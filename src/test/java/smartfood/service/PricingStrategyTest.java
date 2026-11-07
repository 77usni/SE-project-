package smartfood.service;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

import java.util.Collections;

import org.junit.jupiter.api.Test;

import smartfood.domain.Order;
import smartfood.domain.OrderItem;
import smartfood.domain.OrderStatus;

public class PricingStrategyTest {

    @Test
    void testNormalPricingKeepsBaseAmount() {
        PricingStrategy strategy = new NormalPricingStrategy();

        assertEquals(100.0, strategy.calculatePrice(100.0), 0.001);
    }

    @Test
    void testPeakHourPricingAddsMultiplier() {
        PricingStrategy strategy =
                new PeakHourPricingStrategy(1.5);

        assertEquals(150.0, strategy.calculatePrice(100.0), 0.001);
    }

    @Test
    void testExpressPricingAddsSurcharge() {
        PricingStrategy strategy =
                new ExpressDeliveryPricingStrategy(0.2);

        assertEquals(120.0, strategy.calculatePrice(100.0), 0.001);
    }

    @Test
    void testPromotionalPricingAppliesDiscount() {
        PricingStrategy strategy =
                new PromotionalPricingStrategy(0.2);

        assertEquals(80.0, strategy.calculatePrice(100.0), 0.001);
    }

    @Test
    void testFullDiscountMakesPriceZero() {
        PricingStrategy strategy =
                new PromotionalPricingStrategy(1.0);

        assertEquals(0.0, strategy.calculatePrice(100.0), 0.001);
    }

    @Test
    void testRejectsInvalidStrategyValues() {
        assertThrows(IllegalArgumentException.class,
                () -> new PeakHourPricingStrategy(0.9));

        assertThrows(IllegalArgumentException.class,
                () -> new ExpressDeliveryPricingStrategy(-0.1));

        assertThrows(IllegalArgumentException.class,
                () -> new PromotionalPricingStrategy(1.1));
    }

    @Test
    void testOrderUsesPricingStrategyForFinalTotal() {
        OrderItem item = new OrderItem(null, 2, 20.0);
        Order order = new Order(
                1, Collections.singletonList(item));

        double finalTotal = order.applyPricingStrategy(
                new PeakHourPricingStrategy(1.5));

        assertEquals(60.0, finalTotal, 0.001);
        assertEquals(60.0, order.getTotal(), 0.001);
    }

    @Test
    void testChangingPricingStrategyRecalculatesFromBaseAmount() {
        OrderItem item = new OrderItem(null, 1, 100.0);
        Order order = new Order(
                2, Collections.singletonList(item));

        order.applyPricingStrategy(
                new PeakHourPricingStrategy(1.5));
        order.applyPricingStrategy(
                new PromotionalPricingStrategy(0.2));

        assertEquals(80.0, order.getTotal(), 0.001);
    }

    @Test
    void testDeliveryCostIsIncludedBeforePricingIsApplied() {
        OrderItem item = new OrderItem(null, 1, 100.0);
        Order order = new Order(
                3, Collections.singletonList(item));

        order.calculateDeliveryCost(
                5.0,
                new DistanceBasedDeliveryCostStrategy(2.0));
        order.applyPricingStrategy(
                new PromotionalPricingStrategy(0.1));

        assertEquals(99.0, order.getTotal(), 0.001);
    }

    @Test
    void testCannotApplyPricingAfterOrderIsDelivered() {
        Order order = new Order(4, Collections.emptyList());

        order.confirm();
        order.startPreparing();
        order.markReady();
        order.markOutForDelivery();
        order.markDelivered();

        assertEquals(OrderStatus.DELIVERED, order.getStatus());
        assertThrows(IllegalStateException.class,
                () -> order.applyPricingStrategy(
                        new NormalPricingStrategy()));
    }
}