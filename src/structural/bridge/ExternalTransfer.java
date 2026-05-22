package structural.bridge;

public class ExternalTransfer extends BankTransfer {

    private static final int MAX_LIMIT = 20000;

    public ExternalTransfer(TransferChannel transferChannel) {
        super(transferChannel);
    }

    @Override
    String getName() {
        return "Xarici köçürmə";
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
