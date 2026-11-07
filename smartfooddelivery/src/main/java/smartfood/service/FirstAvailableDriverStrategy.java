
package smartfood.service;

import java.util.List;

import smartfood.domain.Driver;

public class FirstAvailableDriverStrategy
        implements DriverSelectionStrategy {

    @Override
    public Driver selectDriver(List<Driver> drivers) {
        if (drivers == null) {
            return null;
        }

        for (Driver driver : drivers) {
            if (driver != null && driver.isAvailable()) {
                return driver;
            }
        }

        return null;
    }
}