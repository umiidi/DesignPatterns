package structural.decorator;

public class MainService {

    public static void main(String[] args) {
        PriceCalculator basePriceCalculator = new BasePriceCalculator();
        PriceCalculator discountCalculator = new DiscountDecorator(basePriceCalculator, 10);
        PriceCalculator taxCalculator = new TaxDecorator(discountCalculator, 18);
        PriceCalculator shippingCalculator = new ShippingDecorator(taxCalculator, 5);

        new CheckoutPriceService(shippingCalculator).printSummary(100);
    }

}
