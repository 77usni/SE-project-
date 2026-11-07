

package smartfood.service;

import java.util.List;

import smartfood.domain.Driver;

public interface DriverSelectionStrategy {
    Driver selectDriver(List<Driver> drivers);
}