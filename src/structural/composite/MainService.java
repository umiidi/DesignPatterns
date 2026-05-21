package structural.composite;

public class MainService {

    public static void main(String[] args) {
        Employee cto = new Employee("Kamran", "CTO", 8000);
        Employee dev1 = new Employee("Ali", "Senior Dev", 5000);
        Employee dev2 = new Employee("Vəli", "Junior Dev", 3000);
        Employee design1 = new Employee("Aytən", "UI Designer", 4000);
        Employee hr1 = new Employee("Nigar", "HR Manager", 3500);

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
