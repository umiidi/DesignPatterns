package creational.abstractfactory;

import creational.abstractfactory.computer.Computer;
import creational.abstractfactory.phone.Phone;

public class Main {

    public static void main(String[] args) {
        AbstractFactory a1 = FactoryProducer.getFactory("Computer");
        Computer computer = a1.getComputer("HP");
        computer.start();

        Computer computer2 = a1.getComputer("Asus");
        computer2.start();

        AbstractFactory a2 = FactoryProducer.getFactory("Phone");
        Phone p1 = a2.getPhone("iPhone");
        p1.start();

        Phone p2 = a2.getPhone("Samsung");
        p2.start();
    }

}
