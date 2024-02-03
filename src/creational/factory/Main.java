package creational.factory;

public class Main {

    public static void main(String[] args) {
        Phone p1 = PhoneFactory.getPhone("Samsung");
        p1.start();

        Phone p2 = PhoneFactory.getPhone("iPhone");
        p2.start();

        Phone p3 = PhoneFactory.getPhone("Xiaomi");
        p3.start();
    }

}
