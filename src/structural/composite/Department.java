package structural.composite;

import java.util.ArrayList;
import java.util.List;

public class Department implements OrganizationComponent {

    private final String name;
    private final List<OrganizationComponent> members = new ArrayList<>();

    public Department(String name) {
        this.name = name;
    }

    public void add(OrganizationComponent component) {
        members.add(component);
    }

    @Override
    public String getName() { return name; }

    @Override
    public double getSalary() {
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
