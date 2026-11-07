
package smartfood.service;

public class DistanceBasedDeliveryCostStrategy
        implements DeliveryCostStrategy {

    private final double costPerKilometer;

    public DistanceBasedDeliveryCostStrategy(double costPerKilometer) {
        if (Double.isNaN(costPerKilometer)
                || Double.isInfinite(costPerKilometer)
                || costPerKilometer < 0) {
            throw new IllegalArgumentException(
                    "Cost per kilometer must be a non-negative number");
        }

        this.costPerKilometer = costPerKilometer;
    }

    @Override
    public double calculateCost(double distanceKm) {
        if (Double.isNaN(distanceKm)
                || Double.isInfinite(distanceKm)
                || distanceKm < 0) {
            throw new IllegalArgumentException(
                    "Distance must be a non-negative number");
        }

        return distanceKm * costPerKilometer;
    }

    public double getCostPerKilometer() {
        return costPerKilometer;
    }
}