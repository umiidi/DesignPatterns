package structural.decorator;

public interface PriceCalculator {
    double calculate(double basePrice);
    String describe();
}