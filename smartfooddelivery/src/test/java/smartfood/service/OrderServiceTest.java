
package smartfood.service;

import static org.junit.jupiter.api.Assertions.*;

import java.util.Arrays;
import java.util.Collections;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import smartfood.domain.Order;
import smartfood.domain.OrderItem;
import smartfood.domain.OrderStatus;
import smartfood.domain.RestaurantMenuItem;

public class OrderServiceTest {

    private OrderService service;
    private RestaurantMenuItem availableMeal;
    private RestaurantMenuItem unavailableMeal;

    @BeforeEach
    void setUp() {
        service = new OrderService();

        availableMeal = new RestaurantMenuItem(
                1, null, null, 20.0, true);

        unavailableMeal = new RestaurantMenuItem(
                2, null, null, 15.0, false);
    }

    @Test
    void testPlaceValidOrder() {
        OrderItem item = new OrderItem(availableMeal, 2, 20.0);

        Order order = service.placeOrder(
                1, Arrays.asList(item));

        assertNotNull(order);
        assertEquals(1, order.getOrderId());
        assertEquals(OrderStatus.PENDING, order.getStatus());
        assertEquals(40.0, order.getTotal(), 0.001);
    }

    @Test
    void testMultipleItemsTotal() {
        OrderItem item1 = new OrderItem(availableMeal, 2, 20.0);

        RestaurantMenuItem secondMeal =
                new RestaurantMenuItem(
                        3, null, null, 10.0, true);

        OrderItem item2 = new OrderItem(secondMeal, 3, 10.0);

        Order order = service.placeOrder(
                2, Arrays.asList(item1, item2));

        assertEquals(70.0, order.getTotal(), 0.001);
    }

    @Test
    void testRejectEmptyOrder() {
        assertThrows(IllegalArgumentException.class, () ->
                service.placeOrder(3, Collections.emptyList()));
    }

    @Test
    void testRejectNullOrderList() {
        assertThrows(IllegalArgumentException.class, () ->
                service.placeOrder(4, null));
    }

    @Test
    void testRejectZeroQuantity() {
        OrderItem item = new OrderItem(availableMeal, 0, 20.0);

        assertThrows(IllegalArgumentException.class, () ->
                service.placeOrder(5, Arrays.asList(item)));
    }

    @Test
    void testRejectNegativeQuantity() {
        OrderItem item = new OrderItem(availableMeal, -2, 20.0);

        assertThrows(IllegalArgumentException.class, () ->
                service.placeOrder(6, Arrays.asList(item)));
    }

    @Test
    void testRejectUnavailableMeal() {
        OrderItem item = new OrderItem(unavailableMeal, 1, 15.0);

        assertThrows(IllegalArgumentException.class, () ->
                service.placeOrder(7, Arrays.asList(item)));
    }

    @Test
    void testRejectNullMenuItem() {
        OrderItem item = new OrderItem(null, 1, 20.0);

        assertThrows(IllegalArgumentException.class, () ->
                service.placeOrder(8, Arrays.asList(item)));
    }

    @Test
    void testRejectNullOrderItem() {
        assertThrows(IllegalArgumentException.class, () ->
                service.placeOrder(
                        9, Arrays.asList((OrderItem) null)));
    }

    @Test
    void testOrderUsesActualMenuPrice() {
        OrderItem item = new OrderItem(availableMeal, 2, 5.0);

        Order order = service.placeOrder(
                10, Arrays.asList(item));

        assertEquals(40.0, order.getTotal(), 0.001);
    }
}
