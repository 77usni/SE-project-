package smartfood.service;

public interface PricingStrategy {
    double calculatePrice(double baseAmount);
}