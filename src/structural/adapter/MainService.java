package structural.adapter;

public class MainService {

    public static void main(String[] args) {
        LegacyPaymentSystem legacyPaymentSystem = new LegacyPaymentSystem();
        LegacyPaymentAdapter legacyPaymentAdapter = new LegacyPaymentAdapter(legacyPaymentSystem);

        CheckOutService checkOutService = new CheckOutService(legacyPaymentAdapter);

        PaymentRequest request1 = new PaymentRequest("4169738812345678", 100, "USD");
        checkOutService.checkout(request1);

        PaymentRequest request2 = new PaymentRequest("4169738812345678", 100, "AZN");
        checkOutService.checkout(request2);

        PaymentRequest request3 = new PaymentRequest("4169738812345678", 100, "EUR");
        checkOutService.checkout(request3);
    }

}
