package structural.adapter;

public class LegacyPaymentSystem {
    public void makePayment(String cardNumber, double amount) {
        System.out.println("Paying $" + amount + " with card " + cardNumber);
    }
}