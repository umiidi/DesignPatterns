package structural.bridge;

public class InternationalTransfer extends BankTransfer {

    private final static int MAX_LIMIT = 50000;

    public InternationalTransfer(TransferChannel transferChannel) {
        super(transferChannel);
    }

    @Override
    String getName() {
        return "Beynəlxalq köçürmə";
    }

    @Override
    void execute(String from, String to, double amount) {
        if (amount >= MAX_LIMIT) {
            throw new IllegalArgumentException("%s limiti aşıldı. Max: %s AZN, cari: %.2f AZN"
                    .formatted(getName(), MAX_LIMIT, amount));
        }
        super.execute(from, to, amount);
    }

}
