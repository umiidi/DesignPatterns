package structural.adapter;

public class LegacyPaymentAdapter implements PaymentProcessor {

    private final LegacyPaymentSystem legacyPaymentSystem;

    public LegacyPaymentAdapter(LegacyPaymentSystem legacyPaymentSystem) {
        this.legacyPaymentSystem = legacyPaymentSystem;
    }

    @Override
    public void processPayment(PaymentRequest request) {
        double amount = switch (request.currency()) {
            case "EUR" -> request.totalAmount() * 1.08;
            case "AZN" -> request.totalAmount() * 0.59;
            default -> request.totalAmount();
        };

        legacyPaymentSystem.makePayment(request.cardToken(), amount);
    }

}
