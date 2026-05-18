package structural.decorator;

public class DiscountDecorator extends BasePriceDecorator {

    private final double discountRate;

    protected DiscountDecorator(PriceCalculator priceCalculator, double discountRate) {
        super(priceCalculator);
        this.discountRate = discountRate;
    }

    @Override
    public double calculate(double basePrice) {
        return super.calculate(basePrice) * (1 - discountRate / 100);
    }

    @Override
    public String describe() {
        return String.format("%s -> %.0f%% endirim", super.describe(), discountRate);
    }

}
