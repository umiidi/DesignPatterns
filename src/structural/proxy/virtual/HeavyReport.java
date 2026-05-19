package structural.proxy.virtual;

public class HeavyReport implements Report {

    public HeavyReport() {
        System.out.println("HeavyReport yaradılır...");
        try {
            Thread.sleep(3000);
        } catch (InterruptedException ignored) {
        }
    }

    @Override
    public String generate() {
        return "Hesabat: 1.250.000 sətir data";
    }
}