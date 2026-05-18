package structural.decorator;

public class BasePriceCalculator implements PriceCalculator{

    @Override
    public double calculate(double basePrice) {
        return basePrice;
    }

    @Override
    public String describe() {
        return "Base Price";
    }

}
