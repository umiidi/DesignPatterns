package structural.decorator;

public class CheckoutPriceService {

    private final PriceCalculator priceCalculator;

    public CheckoutPriceService(PriceCalculator priceCalculator) {
        this.priceCalculator = priceCalculator;
    }

    public void printSummary(double basePrice) {
        System.out.printf("Əməliyyatlar : %s \n", priceCalculator.describe());

        System.out.printf("Əsas qiymət  : %.2f AZN%n", basePrice);
        System.out.printf("Yekun qiymət : %.2f AZN%n", priceCalculator.calculate(basePrice));
    }

}
