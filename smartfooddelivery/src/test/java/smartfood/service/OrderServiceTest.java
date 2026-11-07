

package smartfood.service;

import static org.junit.jupiter.api.Assertions.*;

import java.util.Arrays;
import java.util.Collections;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import smartfood.domain.Order;
import smartfood.domain.OrderItem;
import smartfood.domain.OrderStatus;
import smartfood.domain.Restaurant;
import smartfood.domain.RestaurantMenuItem;

public class OrderServiceTest {

    private OrderService service;
    private RestaurantMenuItem availableMeal;
    private RestaurantMenuItem unavailableMeal;
    private Restaurant restaurant;

    @BeforeEach
    void setUp() {
        service = new OrderService();

        restaurant = new Restaurant(1, "Pizza House", true);
        restaurant.setDeliveryZone("Nablus");
        restaurant.setMinimumOrderValue(30.0);

        availableMeal = new RestaurantMenuItem(
                1, restaurant, null, 20.0, true);

        unavailableMeal = new RestaurantMenuItem(
                2, restaurant, null, 15.0, false);
    }

    @Test
    void testPlaceValidOrder() {
        OrderItem item = new OrderItem(availableMeal, 2, 20.0);

        Order order = service.placeOrder(1, Arrays.asList(item));

        assertNotNull(order);
        assertEquals(1, order.getOrderId());
        assertEquals(OrderStatus.PENDING, order.getStatus());
        assertEquals(40.0, order.getTotal(), 0.001);
    }

    @Test
    void testMultipleItemsTotal() {
        OrderItem item1 = new OrderItem(availableMeal, 2, 20.0);

        RestaurantMenuItem secondMeal =
                new RestaurantMenuItem(3, restaurant, null, 10.0, true);

        OrderItem item2 = new OrderItem(secondMeal, 3, 10.0);

        Order order = service.placeOrder(
                2, Arrays.asList(item1, item2));

        assertEquals(70.0, order.getTotal(), 0.001);
    }

    @Test
    void testOrderUsesActualMenuPrice() {
        OrderItem item = new OrderItem(availableMeal, 2, 5.0);

        Order order = service.placeOrder(
                10, Arrays.asList(item));

        assertEquals(40.0, order.getTotal(), 0.001);
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
                service.placeOrder(9, Arrays.asList((OrderItem) null)));
    }

    @Test
    void testValidDeliveryOrder() {
        OrderItem item = new OrderItem(availableMeal, 2, 20.0);

        Order order = service.placeOrder(
                11, Arrays.asList(item),
                restaurant, "Rafidia Street", "Nablus");

        assertNotNull(order);
        assertEquals(OrderStatus.PENDING, order.getStatus());
        assertEquals(40.0, order.getTotal(), 0.001);
    }

    @Test
    void testRejectOutsideDeliveryZone() {
        OrderItem item = new OrderItem(availableMeal, 2, 20.0);

        assertThrows(IllegalArgumentException.class, () ->
                service.placeOrder(
                        12, Arrays.asList(item),
                        restaurant, "City Center", "Ramallah"));
    }

    @Test
    void testRejectEmptyAddress() {
        OrderItem item = new OrderItem(availableMeal, 2, 20.0);

        assertThrows(IllegalArgumentException.class, () ->
                service.placeOrder(
                        13, Arrays.asList(item),
                        restaurant, "   ", "Nablus"));
    }

    @Test
    void testRejectNullAddress() {
        OrderItem item = new OrderItem(availableMeal, 2, 20.0);

        assertThrows(IllegalArgumentException.class, () ->
                service.placeOrder(
                        14, Arrays.asList(item),
                        restaurant, null, "Nablus"));
    }

    @Test
    void testRejectBelowMinimumOrder() {
        RestaurantMenuItem cheapMeal =
                new RestaurantMenuItem(3, restaurant, null, 10.0, true);

        OrderItem item = new OrderItem(cheapMeal, 2, 10.0);

        assertThrows(IllegalArgumentException.class, () ->
                service.placeOrder(
                        15, Arrays.asList(item),
                        restaurant, "Rafidia Street", "Nablus"));
    }

    @Test
    void testAcceptOrderAtMinimumValue() {
        RestaurantMenuItem menuItem =
                new RestaurantMenuItem(4, restaurant, null, 30.0, true);

        OrderItem item = new OrderItem(menuItem, 1, 30.0);

        Order order = service.placeOrder(
                16, Arrays.asList(item),
                restaurant, "Rafidia Street", "Nablus");

        assertEquals(30.0, order.getTotal(), 0.001);
    }

    @Test
    void testAcceptCaseInsensitiveZone() {
        OrderItem item = new OrderItem(availableMeal, 2, 20.0);

        Order order = service.placeOrder(
                17, Arrays.asList(item),
                restaurant, "Rafidia Street", "nablus");

        assertNotNull(order);
    }

    @Test
    void testRejectNullRestaurant() {
        OrderItem item = new OrderItem(availableMeal, 2, 20.0);

        assertThrows(IllegalArgumentException.class, () ->
                service.placeOrder(
                        18, Arrays.asList(item),
                        null, "Rafidia Street", "Nablus"));
    }

    @Test
    void testRejectOrderItemFromAnotherRestaurant() {
        Restaurant otherRestaurant =
                new Restaurant(2, "Burger House", true);
        RestaurantMenuItem otherRestaurantMeal =
                new RestaurantMenuItem(
                        5, otherRestaurant, null, 20.0, true);
        OrderItem item = new OrderItem(otherRestaurantMeal, 2, 20.0);

        assertThrows(IllegalArgumentException.class, () ->
                service.placeOrder(
                        19, Arrays.asList(item),
                        restaurant, "Rafidia Street", "Nablus"));
    }

    @Test
    void testRejectAddressWithoutLettersOrDigits() {
        OrderItem item = new OrderItem(availableMeal, 2, 20.0);

        assertThrows(IllegalArgumentException.class, () ->
                service.placeOrder(
                        20, Arrays.asList(item),
                        restaurant, "!!!", "Nablus"));
    }
}