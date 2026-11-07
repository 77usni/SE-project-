package smartfood.service;

public class PeakHourPricingStrategy implements PricingStrategy {

    private final double multiplier;

    public PeakHourPricingStrategy(double multiplier) {
        if (Double.isNaN(multiplier)
                || Double.isInfinite(multiplier)
                || multiplier < 1.0) {
            throw new IllegalArgumentException(
                    "Peak-hour multiplier must be at least 1");
        }

        this.multiplier = multiplier;
    }

    @Override
    public double calculatePrice(double baseAmount) {
        validateAmount(baseAmount);
        return baseAmount * multiplier;
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