package structural.decorator;

public class TaxDecorator extends BasePriceDecorator {

    private final double taxRate;

    protected TaxDecorator(PriceCalculator priceCalculator, double taxRate) {
        super(priceCalculator);
        this.taxRate = taxRate;
    }

    @Override
    public double calculate(double basePrice) {
        return super.calculate(basePrice) * (1 + taxRate / 100);
    }

    @Override
    public String describe() {
        return String.format("%s -> %.0f%% vergi", super.describe(), taxRate);
    }

}
