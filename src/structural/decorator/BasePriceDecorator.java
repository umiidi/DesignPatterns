package structural.decorator;

public abstract class BasePriceDecorator implements PriceCalculator {

    private final PriceCalculator priceCalculator;

    protected BasePriceDecorator(PriceCalculator priceCalculator) {
        this.priceCalculator = priceCalculator;
    }

    @Override
    public double calculate(double basePrice) {
        return priceCalculator.calculate(basePrice);
    }

    @Override
    public String describe() {
        return priceCalculator.describe();
    }

}
