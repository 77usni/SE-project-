
package smartfood.service;

import java.util.List;

import smartfood.domain.Driver;

public class NearestDriverStrategy
        implements DriverSelectionStrategy {

    @Override
    public Driver selectDriver(List<Driver> drivers) {
        if (drivers == null) {
            return null;
        }

        Driver nearestDriver = null;

        for (Driver driver : drivers) {
            if (driver != null
                    && driver.isAvailable()
                    && (nearestDriver == null
                    || driver.getDistanceToRestaurant()
                    < nearestDriver.getDistanceToRestaurant())) {
                nearestDriver = driver;
            }
        }

        return nearestDriver;
    }
}