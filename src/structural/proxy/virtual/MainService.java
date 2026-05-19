package structural.proxy.virtual;

public class MainService {
    public static void main(String[] args) {
        // test 1
        Report report1 = new HeavyReport(); // constructor called
        report1.generate();

        Report report2 = new LazyReportProxy();
        report2.generate(); // constructor called
    }
}
