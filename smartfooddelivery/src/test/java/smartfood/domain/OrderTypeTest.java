package smartfood.domain;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.time.LocalDateTime;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;

import org.junit.jupiter.api.Test;

public class OrderTypeTest {

    @Test
    void testStandardOrderType() {
        Order order = new StandardOrder(
                1, Collections.emptyList());

        assertEquals(OrderType.STANDARD, order.getOrderType());
    }

    @Test
    void testExpressOrderType() {
        Order order = new ExpressOrder(
                2, Collections.emptyList());

        assertEquals(OrderType.EXPRESS, order.getOrderType());
    }

    @Test
    void testScheduledOrderTypeAndDeliveryTime() {
        LocalDateTime deliveryTime =
                LocalDateTime.of(2027, 1, 1, 18, 30);
        ScheduledOrder order = new ScheduledOrder(
                3, Collections.emptyList(), deliveryTime);

        assertEquals(OrderType.SCHEDULED, order.getOrderType());
        assertEquals(
                deliveryTime,
                order.getScheduledDeliveryTime());
    }

    @Test
    void testDifferentOrderTypesCanBeHandledPolymorphically() {
        List<Order> orders = Arrays.asList(
                new StandardOrder(4, Collections.emptyList()),
                new ExpressOrder(5, Collections.emptyList()),
                new ScheduledOrder(
                        6,
                        Collections.emptyList(),
                        LocalDateTime.of(2027, 1, 2, 12, 0)));

        assertTrue(orders.get(0) instanceof Order);
        assertTrue(orders.get(1) instanceof Order);
        assertTrue(orders.get(2) instanceof Order);

        assertEquals(OrderType.STANDARD,
                orders.get(0).getOrderType());
        assertEquals(OrderType.EXPRESS,
                orders.get(1).getOrderType());
        assertEquals(OrderType.SCHEDULED,
                orders.get(2).getOrderType());
    }
}