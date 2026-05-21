package structural.composite;

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
