

package smartfood.service;

import static org.junit.jupiter.api.Assertions.*;

import java.util.Arrays;
import java.util.Collections;

import org.junit.jupiter.api.Test;

import smartfood.domain.Driver;
import smartfood.domain.DriverStatus;
import smartfood.domain.Order;

public class DriverAssignmentServiceTest {

    @Test
    void testAssignAvailableDriver() {
        Order order = new Order(1, Collections.emptyList());
        Driver driver = new Driver(1, "Ali", 5.0, 30.0);
        DriverAssignmentService service =
                new DriverAssignmentService(
                        new FirstAvailableDriverStrategy());

        Driver assignedDriver =
                service.assignDriver(order, Arrays.asList(driver));

        assertSame(driver, assignedDriver);
        assertSame(driver, order.getAssignedDriver());
        assertEquals(DriverStatus.BUSY, driver.getStatus());
    }

    @Test
    void testSkipsBusyDriverAndAssignsAvailableDriver() {
        Order order = new Order(2, Collections.emptyList());
        Driver busyDriver = new Driver(1, "Ahmad", 2.0, 40.0);
        Driver availableDriver = new Driver(2, "Ali", 5.0, 30.0);
        busyDriver.markBusy();

        DriverAssignmentService service =
                new DriverAssignmentService(
                        new FirstAvailableDriverStrategy());

        Driver assignedDriver = service.assignDriver(
                order, Arrays.asList(busyDriver, availableDriver));

        assertSame(availableDriver, assignedDriver);
        assertEquals(DriverStatus.BUSY, busyDriver.getStatus());
        assertEquals(DriverStatus.BUSY, availableDriver.getStatus());
    }

    @Test
    void testRejectsAssignmentWhenNoDriverIsAvailable() {
        Order order = new Order(3, Collections.emptyList());
        Driver busyDriver = new Driver(1, "Ahmad", 2.0, 40.0);
        busyDriver.markBusy();

        DriverAssignmentService service =
                new DriverAssignmentService(
                        new FirstAvailableDriverStrategy());

        assertThrows(IllegalStateException.class, () ->
                service.assignDriver(order, Arrays.asList(busyDriver)));
    }

    @Test
    void testRejectsAssignmentWhenDriverListIsNull() {
        Order order = new Order(4, Collections.emptyList());
        DriverAssignmentService service =
                new DriverAssignmentService(
                        new FirstAvailableDriverStrategy());

        assertThrows(IllegalStateException.class, () ->
                service.assignDriver(order, null));
    }

    @Test
    void testRejectsNullOrder() {
        Driver driver = new Driver(1, "Ali", 5.0, 30.0);
        DriverAssignmentService service =
                new DriverAssignmentService(
                        new FirstAvailableDriverStrategy());

        assertThrows(IllegalArgumentException.class, () ->
                service.assignDriver(null, Arrays.asList(driver)));

        assertEquals(DriverStatus.AVAILABLE, driver.getStatus());
    }

    @Test
    void testNearestStrategySelectsClosestAvailableDriver() {
        Driver fartherDriver = new Driver(1, "Ahmad", 8.0, 40.0);
        Driver nearestDriver = new Driver(2, "Ali", 3.0, 25.0);
        Order order = new Order(5, Collections.emptyList());
        DriverAssignmentService service =
                new DriverAssignmentService(new NearestDriverStrategy());

        Driver assignedDriver = service.assignDriver(
                order, Arrays.asList(fartherDriver, nearestDriver));

        assertSame(nearestDriver, assignedDriver);
        assertEquals(DriverStatus.BUSY, nearestDriver.getStatus());
    }

    @Test
    void testNearestStrategySkipsUnavailableClosestDriver() {
        Driver closestDriver = new Driver(1, "Ahmad", 2.0, 40.0);
        Driver nextClosestDriver = new Driver(2, "Ali", 5.0, 25.0);
        closestDriver.markBusy();

        Order order = new Order(6, Collections.emptyList());
        DriverAssignmentService service =
                new DriverAssignmentService(new NearestDriverStrategy());

        Driver assignedDriver = service.assignDriver(
                order, Arrays.asList(closestDriver, nextClosestDriver));

        assertSame(nextClosestDriver, assignedDriver);
        assertEquals(DriverStatus.BUSY, closestDriver.getStatus());
        assertEquals(DriverStatus.BUSY, nextClosestDriver.getStatus());
    }

    @Test
    void testFastestStrategySelectsFastestAvailableDriver() {
        Driver slowerDriver = new Driver(1, "Ahmad", 2.0, 25.0);
        Driver fasterDriver = new Driver(2, "Ali", 8.0, 40.0);
        Order order = new Order(7, Collections.emptyList());
        DriverAssignmentService service =
                new DriverAssignmentService(new FastestDriverStrategy());

        Driver assignedDriver = service.assignDriver(
                order, Arrays.asList(slowerDriver, fasterDriver));

        assertSame(fasterDriver, assignedDriver);
        assertEquals(DriverStatus.BUSY, fasterDriver.getStatus());
    }

    @Test
    void testFastestStrategySkipsUnavailableFastestDriver() {
        Driver fastestDriver = new Driver(1, "Ahmad", 2.0, 45.0);
        Driver nextFastestDriver = new Driver(2, "Ali", 5.0, 30.0);
        fastestDriver.markBusy();

        Order order = new Order(8, Collections.emptyList());
        DriverAssignmentService service =
                new DriverAssignmentService(new FastestDriverStrategy());

        Driver assignedDriver = service.assignDriver(
                order, Arrays.asList(fastestDriver, nextFastestDriver));

        assertSame(nextFastestDriver, assignedDriver);
        assertEquals(DriverStatus.BUSY, fastestDriver.getStatus());
        assertEquals(DriverStatus.BUSY, nextFastestDriver.getStatus());
    }

    @Test
    void testStrategiesRejectAssignmentWhenNoDriverIsAvailable() {
        Driver busyDriver = new Driver(1, "Ahmad", 2.0, 40.0);
        busyDriver.markBusy();

        Order nearestOrder = new Order(9, Collections.emptyList());
        Order fastestOrder = new Order(10, Collections.emptyList());

        DriverAssignmentService nearestService =
                new DriverAssignmentService(new NearestDriverStrategy());
        DriverAssignmentService fastestService =
                new DriverAssignmentService(new FastestDriverStrategy());

        assertThrows(IllegalStateException.class, () ->
                nearestService.assignDriver(
                        nearestOrder, Arrays.asList(busyDriver)));

        assertThrows(IllegalStateException.class, () ->
                fastestService.assignDriver(
                        fastestOrder, Arrays.asList(busyDriver)));
    }
}