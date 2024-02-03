package creational.singleton;

public class Main {
    public static void main(String[] args) {
        Fanar f = Fanar.getInstance();
        Fanar f2 = Fanar.getInstance();
        System.out.println(f == f2);    //true
    }
}
