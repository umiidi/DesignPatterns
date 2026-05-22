package structural.bridge;

public abstract class BankTransfer {

    protected final TransferChannel transferChannel;

    protected BankTransfer(TransferChannel transferChannel) {
        this.transferChannel = transferChannel;
    }

    abstract String getName();

    void execute(String from, String to, double amount) {
        if (!transferChannel.isAvailable()) {
            throw new IllegalStateException("%s hal-hazırda əlçatmazdır".formatted(transferChannel.getName()));
        }
        transferChannel.process(from, to, amount);
        printSummary(from, to, amount);
    }

    void printSummary(String from, String to, double amount) {
        System.out.printf("📋 %s | %s → %s | %.2f AZN | %s%n", getName(), from, to, amount, transferChannel.getName());
    }

}
