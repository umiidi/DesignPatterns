package structural.decorator;

public class ShippingDecorator extends BasePriceDecorator {

    private final double shippingFee;

    public ShippingDecorator(PriceCalculator priceCalculator, double shippingFee) {
        super(priceCalculator);
        this.shippingFee = shippingFee;
    }

    @Override
    public double calculate(double basePrice) {
        return super.calculate(basePrice) + shippingFee;
    }

    @Override
    public String describe() {
        return String.format("%s -> +%.1f çatdırılma", super.describe(), shippingFee);
    }

}
