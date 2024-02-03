package creational.abstractfactory;
import creational.abstractfactory.computer.Computer;
import creational.abstractfactory.phone.Phone;

public abstract class AbstractFactory {

    public abstract Phone getPhone(String brend);

    public abstract Computer getComputer(String brend);

}
