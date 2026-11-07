

package smartfood.service;

import java.util.List;

import smartfood.domain.Driver;

public class FastestDriverStrategy
        implements DriverSelectionStrategy {

    @Override
    public Driver selectDriver(List<Driver> drivers) {
        if (drivers == null) {
            return null;
        }

        Driver fastestDriver = null;

        for (Driver driver : drivers) {
            if (driver != null
                    && driver.isAvailable()
                    && (fastestDriver == null
                    || driver.getSpeed() > fastestDriver.getSpeed())) {
                fastestDriver = driver;
            }
        }

        return fastestDriver;
    }
}