package structural.proxy.virtual;

public class LazyReportProxy implements Report {

    private Report report;

    @Override
    public String generate() {
        if (report == null) report = new HeavyReport(); // maybe double check locking

        return report.generate();
    }

}
