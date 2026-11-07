package smartfood.service;

public class ExpressDeliveryPricingStrategy
        implements PricingStrategy {

    private final double surchargeRate;

    public ExpressDeliveryPricingStrategy(double surchargeRate) {
        if (Double.isNaN(surchargeRate)
                || Double.isInfinite(surchargeRate)
                || surchargeRate < 0) {
            throw new IllegalArgumentException(
                    "Surcharge rate must be a non-negative number");
        }

        this.surchargeRate = surchargeRate;
    }

    @Override
    public double calculatePrice(double baseAmount) {
        validateAmount(baseAmount);
        return baseAmount * (1.0 + surchargeRate);
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