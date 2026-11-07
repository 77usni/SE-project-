package smartfood.service;

public class PromotionalPricingStrategy implements PricingStrategy {

    private final double discountRate;

    public PromotionalPricingStrategy(double discountRate) {
        if (Double.isNaN(discountRate)
                || Double.isInfinite(discountRate)
                || discountRate < 0
                || discountRate > 1) {
            throw new IllegalArgumentException(
                    "Discount rate must be between 0 and 1");
        }

        this.discountRate = discountRate;
    }

    @Override
    public double calculatePrice(double baseAmount) {
        validateAmount(baseAmount);
        return baseAmount * (1.0 - discountRate);
    }

    private void validateAmount(double amount) {
        if (Double.isNaN(amount)
                || Double.isInfinite(amount)
                || amount < 0) {
            throw new IllegalArgumentException(
                    "Amount must be a non-negative number");
        }
    }
}