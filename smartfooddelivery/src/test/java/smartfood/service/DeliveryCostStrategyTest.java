

package smartfood.service;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

import java.util.Collections;

import org.junit.jupiter.api.Test;

import smartfood.domain.Order;
import smartfood.domain.OrderItem;

public class DeliveryCostStrategyTest {

    @Test
    void testCalculatesCostBasedOnDistance() {
        DistanceBasedDeliveryCostStrategy strategy =
                new DistanceBasedDeliveryCostStrategy(2.0);

        assertEquals(10.0, strategy.calculateCost(5.0), 0.001);
    }

    @Test
    void testZeroDistanceHasZeroCost() {
        DistanceBasedDeliveryCostStrategy strategy =
                new DistanceBasedDeliveryCostStrategy(2.0);

        assertEquals(0.0, strategy.calculateCost(0.0), 0.001);
    }

    @Test
    void testRejectsNegativeDistance() {
        DistanceBasedDeliveryCostStrategy strategy =
                new DistanceBasedDeliveryCostStrategy(2.0);

        assertThrows(IllegalArgumentException.class,
                () -> strategy.calculateCost(-1.0));
    }

    @Test
    void testRejectsNegativeCostPerKilometer() {
        assertThrows(IllegalArgumentException.class,
                () -> new DistanceBasedDeliveryCostStrategy(-1.0));
    }

    @Test
    void testOrderIncludesDeliveryCostInTotal() {
        OrderItem item = new OrderItem(null, 2, 10.0);
        Order order = new Order(1, Collections.singletonList(item));
        DistanceBasedDeliveryCostStrategy strategy =
                new DistanceBasedDeliveryCostStrategy(2.0);

        double deliveryCost =
                order.calculateDeliveryCost(5.0, strategy);

        assertEquals(10.0, deliveryCost, 0.001);
        assertEquals(5.0, order.getDeliveryDistanceKm(), 0.001);
        assertEquals(10.0, order.getDeliveryCost(), 0.001);
        assertEquals(30.0, order.getTotal(), 0.001);
    }

    @Test
    void testRecalculatingDeliveryCostUpdatesTotal() {
        OrderItem item = new OrderItem(null, 1, 20.0);
        Order order = new Order(2, Collections.singletonList(item));
        DistanceBasedDeliveryCostStrategy strategy =
                new DistanceBasedDeliveryCostStrategy(3.0);

        order.calculateDeliveryCost(2.0, strategy);
        order.calculateDeliveryCost(4.0, strategy);

        assertEquals(4.0, order.getDeliveryDistanceKm(), 0.001);
        assertEquals(12.0, order.getDeliveryCost(), 0.001);
        assertEquals(32.0, order.getTotal(), 0.001);
    }

    @Test
    void testRejectsNullStrategy() {
        Order order = new Order(3, Collections.emptyList());

        assertThrows(IllegalArgumentException.class,
                () -> order.calculateDeliveryCost(2.0, null));
    }

    @Test
    void testCannotChangeDeliveryCostAfterOrderIsDelivered() {
        Order order = new Order(4, Collections.emptyList());
        DistanceBasedDeliveryCostStrategy strategy =
                new DistanceBasedDeliveryCostStrategy(2.0);

        order.confirm();
        order.startPreparing();
        order.markReady();
        order.markOutForDelivery();
        order.markDelivered();

        assertThrows(IllegalStateException.class,
                () -> order.calculateDeliveryCost(3.0, strategy));
    }
}