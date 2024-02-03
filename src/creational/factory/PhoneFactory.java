package creational.factory;

public class PhoneFactory {

    public static Phone getPhone(String brend) {
        if (brend == null) {
            return null;
        } else if (brend.equalsIgnoreCase("Samsung")) {
            return new Samsung();
        } else if (brend.equalsIgnoreCase("Xiaomi")) {
            return new Xiaomi();
        } else if (brend.equalsIgnoreCase("iPhone")) {
            return new İphone();
        }
        return null;
    }
}
