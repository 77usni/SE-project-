

package smartfood.service;

import java.util.List;

import smartfood.domain.Driver;
import smartfood.domain.Order;

public class DriverAssignmentService {

    private final DriverSelectionStrategy strategy;

    public DriverAssignmentService(DriverSelectionStrategy strategy) {
        if (strategy == null) {
            throw new IllegalArgumentException("Strategy cannot be null");
        }

        this.strategy = strategy;
    }

    public Driver assignDriver(Order order, List<Driver> drivers) {
        if (order == null) {
            throw new IllegalArgumentException("Order cannot be null");
        }

        Driver driver = strategy.selectDriver(drivers);

        if (driver == null) {
            throw new IllegalStateException("No available driver");
        }

        order.assignDriver(driver);
        return driver;
    }
}