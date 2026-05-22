package structural.bridge;

public interface TransferChannel {

    String getName();

    void process(String from, String to, double amount);

    boolean isAvailable();

}
