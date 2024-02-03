package creational.abstractfactory.computer;

import creational.abstractfactory.AbstractFactory;
import creational.abstractfactory.phone.Phone;

public class ComputerFactory extends AbstractFactory {

    @Override
    public Phone getPhone(String brend) {
        return null;
    }

    @Override
    public Computer getComputer(String brend) {
        if (brend == null) {
            return null;
        } else if (brend.equalsIgnoreCase("Asus")) {
            return new Asus();
        } else if (brend.equalsIgnoreCase("HP")) {
            return new HP();
        }
        return null;
    }

}
