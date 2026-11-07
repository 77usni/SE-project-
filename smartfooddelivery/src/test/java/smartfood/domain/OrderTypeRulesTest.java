package smartfood.domain;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

import java.time.LocalDateTime;
import java.util.Collections;

import org.junit.jupiter.api.Test;

import smartfood.service.DistanceBasedDeliveryCostStrategy;
import smartfood.service.PromotionalPricingStrategy;

public class OrderTypeRulesTest {

    @Test
    void testStandardOrderUsesNormalDeliveryCost() {
        OrderItem item = new OrderItem(null, 1, 20.0);
        StandardOrder order = new StandardOrder(
                1, Collections.singletonList(item));

        order.calculateDeliveryCost(
                3.0,
                new DistanceBasedDeliveryCostStrategy(2.0));

        assertEquals(OrderType.STANDARD, order.getOrderType());
        assertEquals(26.0, order.getTotal(), 0.001);
    }

    @Test
    void testExpressOrderAddsDefaultFee() {
        OrderItem item = new OrderItem(null, 1, 20.0);
        ExpressOrder order = new ExpressOrder(
                2, Collections.singletonList(item));

        assertEquals(5.0, order.getExpressFee(), 0.001);
        assertEquals(25.0, order.getTotal(), 0.001);

        order.calculateDeliveryCost(
                3.0,
                new DistanceBasedDeliveryCostStrategy(2.0));

        assertEquals(31.0, order.getTotal(), 0.001);
    }

    @Test
    void testCustomExpressFeeIsIncludedInPromotionalPrice() {
        OrderItem item = new OrderItem(null, 1, 20.0);
        ExpressOrder order = new ExpressOrder(
                3,
                Collections.singletonList(item),
                10.0);

        order.applyPricingStrategy(
                new PromotionalPricingStrategy(0.1));

        assertEquals(27.0, order.getTotal(), 0.001);
    }

    @Test
    void testRejectsNegativeExpressFee() {
        assertThrows(IllegalArgumentException.class,
                () -> new ExpressOrder(
                        4,
                        Collections.emptyList(),
                        -1.0));
    }

    @Test
    void testScheduledOrderRequiresFutureDeliveryTime() {
        assertThrows(IllegalArgumentException.class,
                () -> new ScheduledOrder(
                        5,
                        Collections.emptyList(),
                        null));

        assertThrows(IllegalArgumentException.class,
                () -> new ScheduledOrder(
                        6,
                        Collections.emptyList(),
                        LocalDateTime.now().minusMinutes(1)));
    }

    @Test
    void testScheduledOrderAcceptsFutureDeliveryTime() {
        LocalDateTime deliveryTime =
                LocalDateTime.now().plusHours(2);
        ScheduledOrder order = new ScheduledOrder(
                7,
                Collections.emptyList(),
                deliveryTime);

        assertEquals(OrderType.SCHEDULED, order.getOrderType());
        assertEquals(
                deliveryTime,
                order.getScheduledDeliveryTime());
    }
}