package structural.bridge;

public class ABAZChannel implements TransferChannel {

    @Override
    public String getName() {
        return "ABAZ şəbəkəsi";
    }

    @Override
    public void process(String from, String to, double amount) {
        System.out.printf("[ABAZ] %s → %s | %.2f AZN | Komissiya: %.2f AZN%n", from, to, amount, amount * 0.001);
    }

    @Override
    public boolean isAvailable() {
        return true;
    }

}
