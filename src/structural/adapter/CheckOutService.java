package structural.adapter;

public class CheckOutService {

    private final PaymentProcessor paymentProcessor;

    public CheckOutService(PaymentProcessor paymentProcessor) {
        this.paymentProcessor = paymentProcessor;
    }

    public void checkout(PaymentRequest request){
        paymentProcessor.processPayment(request);
    }

}
