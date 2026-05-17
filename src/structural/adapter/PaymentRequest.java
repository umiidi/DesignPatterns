package structural.adapter;

public record PaymentRequest(
        String cardToken,
        double totalAmount,
        String currency
) {
}
