package smartfood.service;

public class NormalPricingStrategy implements PricingStrategy {

    @Override
    public double calculatePrice(double baseAmount) {
        validateAmount(baseAmount);
        return baseAmount;
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