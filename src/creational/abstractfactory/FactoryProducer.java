package creational.abstractfactory;
import creational.abstractfactory.computer.ComputerFactory;
import creational.abstractfactory.phone.PhoneFactory;

public class FactoryProducer {

    public static AbstractFactory getFactory(String choice) {
        if (choice.equalsIgnoreCase("Phone")) {
            return new PhoneFactory();
        } else if (choice.equalsIgnoreCase("Computer")) {
            return new ComputerFactory();
        }
        return null;
    }
}
