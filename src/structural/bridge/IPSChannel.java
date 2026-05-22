package structural.bridge;

public class IPSChannel implements TransferChannel {

    @Override
    public String getName() {
        return "IPS şəbəkəsi";
    }

    @Override
    public void process(String from, String to, double amount) {
        System.out.printf("[IPS] %s → %s | %.2f AZN | IPS köçürmə | Komissiya: 0.00%n", from, to, amount);
    }

    @Override
    public boolean isAvailable() {
        return true;
    }

}
