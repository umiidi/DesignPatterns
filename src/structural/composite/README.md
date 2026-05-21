# Composite Pattern — Şirkət Strukturu

## Pattern haqqında

**Composite** structural design pattern-lərindən biridir. Tək obyekt və qrup obyektləri eyni interfeys vasitəsilə idarə edir — client fərqi bilmir.

**Real dünya analoqu:** Fayl sistemi. Fayl da var, qovluq da. Qovluğun içində fayl da ola bilər, qovluq da. İkisini də eyni əməliyyatla silə bilərsən — `delete()`.

## Struktur

```
OrganizationComponent (interface)
        ▲
        ├── Employee      ← Leaf — uşaq saxlamır
        └── Department    ← Composite — uşaq saxlayır
                │
                ├── Employee
                ├── Employee
                └── Department
                        │
                        └── Employee
```

---

## OrganizationComponent (interface)

```java
public interface OrganizationComponent {
    String getName();
    double getSalary();
    void print(String indent);
}
```

---

## Employee — Leaf

Ağacın yarpağı. Uşaq saxlamır, yalnız öz məlumatlarını bilir.

```java
public class Employee implements OrganizationComponent {

    private final String name;
    private final String position;
    private final double salary;

    public Employee(String name, String position, double salary) {
        this.name = name;
        this.position = position;
        this.salary = salary;
    }

    @Override
    public String getName() { return name; }

    @Override
    public double getSalary() { return salary; }

    @Override
    public void print(String indent) {
        System.out.printf("%s👤 %s (%s) — %.0f AZN%n", indent, name, position, salary);
    }
}
```

---

## Department — Composite

Uşaqlar saxlayır. Özü də `OrganizationComponent`-dir — başqa `Department`-in uşağı ola bilər.

```java
public class Department implements OrganizationComponent {

    private final String name;
    private final List<OrganizationComponent> members = new ArrayList<>();

    public Department(String name) {
        this.name = name;
    }

    public void add(OrganizationComponent component) {
        members.add(component);
    }

    public void remove(OrganizationComponent component) {
        members.remove(component);
    }

    @Override
    public String getName() { return name; }

    @Override
    public double getSalary() {
        // rekursiv — hər uşağın getSalary()-sini çağırır
        return members.stream()
            .mapToDouble(OrganizationComponent::getSalary)
            .sum();
    }

    @Override
    public void print(String indent) {
        System.out.printf("%s🏢 %s — Cəmi: %.0f AZN%n", indent, name, getSalary());
        members.forEach(m -> m.print(indent + "   "));
    }
}
```

---

## MainService

```java
public class MainService {

    public static void main(String[] args) {
        Employee cto     = new Employee("Kamran", "CTO", 8000);
        Employee dev1    = new Employee("Ali", "Senior Dev", 5000);
        Employee dev2    = new Employee("Vəli", "Junior Dev", 3000);
        Employee design1 = new Employee("Aytən", "UI Designer", 4000);
        Employee hr1     = new Employee("Nigar", "HR Manager", 3500);

        Department engineering = new Department("Engineering");
        engineering.add(dev1);
        engineering.add(dev2);

        Department design = new Department("Design");
        design.add(design1);

        Department hr = new Department("HR");
        hr.add(hr1);

        Department company = new Department("TechAZ MMC");
        company.add(cto);
        company.add(engineering);
        company.add(design);
        company.add(hr);

        company.print("");
        System.out.printf("%nŞirkətin ümumi xərci: %.0f AZN%n", company.getSalary());
    }
}
```

**Çıxış:**
```
🏢 TechAZ MMC — Cəmi: 23500 AZN
   👤 Kamran (CTO) — 8000 AZN
   🏢 Engineering — Cəmi: 8000 AZN
      👤 Ali (Senior Dev) — 5000 AZN
      👤 Vəli (Junior Dev) — 3000 AZN
   🏢 Design — Cəmi: 4000 AZN
      👤 Aytən (UI Designer) — 4000 AZN
   🏢 HR — Cəmi: 3500 AZN
      👤 Nigar (HR Manager) — 3500 AZN

Şirkətin ümumi xərci: 23500 AZN
```

---

## Composite-in əsas ideyası

```java
// Client Department-in içində nə olduğunu bilmir
// Employee-dirmi, Department-dirmi — fərq etmir
OrganizationComponent company = new Department("TechAZ MMC");
company.getSalary(); // rekursiv — bütün ağacı keçir
company.print("");   // rekursiv — bütün ağacı çap edir
```

Yeni tip əlavə olunanda (məs. `Contractor`) — yalnız `OrganizationComponent` implement etmək kifayətdir. `Department` və client dəyişmir.

---

## Java-da real nümunələr

```java
// Swing — ən klassik Composite nümunəsi
JButton button = new JButton("OK");  // Leaf
JPanel panel   = new JPanel();       // Composite
JFrame frame   = new JFrame();       // Composite

panel.add(button);  // Leaf → Composite
frame.add(panel);   // Composite → Composite

frame.repaint();    // rekursiv — hər uşaq özünü rəsm edir
```

---

## Nə vaxt istifadə etmək lazımdır

- Ağac strukturlu data var — fayl sistemi, org chart, menyu, XML/JSON
- Tək element və qrup elementi eyni cür idarə etmək lazımdırsa
- Rekursiv strukturlar modelləşdiriləndə