package structural.bridge;

public class InternalTransfer extends BankTransfer {

    public InternalTransfer(TransferChannel transferChannel) {
        super(transferChannel);
    }

    @Override
    String getName() {
        return "Daxili köçürmə";
    }

    @Override
    void execute(String from, String to, double amount) {
        super.execute(from, to, amount);
    }

}
