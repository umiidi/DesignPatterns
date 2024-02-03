package creational.abstractfactory.phone;

import creational.abstractfactory.AbstractFactory;
import creational.abstractfactory.computer.Computer;

public class PhoneFactory extends AbstractFactory {

    @Override
    public Phone getPhone(String brend) {
        if (brend == null) {
            return null;
        } else if (brend.equalsIgnoreCase("Samsung")) {
            return new Samsung();
        } else if (brend.equalsIgnoreCase("iPhone")) {
            return new İphone();
        }
        return null;
    }

    @Override
    public Computer getComputer(String brend) {
        return null;
    }
}
