package creational.singleton;

public class Fanar {

    private static Fanar f = null;

    private Fanar() {
    }

    public static Fanar getInstance() {
        if (f == null) {
            f = new Fanar();
        }
        return f;
    }

    //multi threading ?
    public static Fanar getInstance2() {
        if (f == null) {
            synchronized (Fanar.class) {
                if (f == null) {
                    f = new Fanar();
                }
            }
        }
        return f;
    }
}
