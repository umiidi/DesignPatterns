package structural.bridge;

import java.time.LocalTime;

public class SWIFTChannel implements TransferChannel {

    @Override
    public String getName() {
        return "SWIFT şəbəkəsi";
    }

    @Override
    public void process(String from, String to, double amount) {
        System.out.printf("[SWIFT] %s → %s | %.2f USD | Komissiya: 5.00 USD%n", from, to, amount);
    }

    @Override
    public boolean isAvailable() {
        LocalTime now = LocalTime.now();
        return now.isAfter(LocalTime.of(8, 0)) && now.isBefore(LocalTime.of(22, 0));
    }

}
