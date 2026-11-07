
package smartfood.service;

import java.util.List;

import smartfood.domain.Order;
import smartfood.domain.OrderItem;
import smartfood.domain.Restaurant;
import smartfood.domain.RestaurantMenuItem;

public class OrderService {

    public Order placeOrder(int orderId, List<OrderItem> items) {
        if (items == null || items.isEmpty()) {
            throw new IllegalArgumentException("Order cannot be empty");
        }

        for (OrderItem item : items) {
            if (item == null) {
                throw new IllegalArgumentException("Invalid order item");
            }

            if (item.getQuantity() <= 0) {
                throw new IllegalArgumentException("Invalid quantity");
            }

            RestaurantMenuItem menuItem = item.getMenuItem();

            if (menuItem == null || !menuItem.isAvailable()) {
                throw new IllegalArgumentException("Menu item is unavailable");
            }
        }

        return new Order(orderId, items);
    }

    public Order placeOrder(
            int orderId,
            List<OrderItem> items,
            Restaurant restaurant,
            String deliveryAddress,
            String customerZone) {

        if (restaurant == null) {
            throw new IllegalArgumentException("Invalid restaurant");
        }

        if (!isValidDeliveryAddress(deliveryAddress)) {
            throw new IllegalArgumentException("Invalid delivery address");
        }

        String restaurantZone = restaurant.getDeliveryZone();

        if (restaurantZone == null
                || restaurantZone.trim().isEmpty()
                || customerZone == null
                || !restaurantZone.trim().equalsIgnoreCase(customerZone.trim())) {
            throw new IllegalArgumentException("Outside delivery zone");
        }

        Order order = placeOrder(orderId, items);

        for (OrderItem item : order.getItems()) {
            if (item.getMenuItem().getRestaurant() != restaurant) {
                throw new IllegalArgumentException(
                        "Order item belongs to another restaurant");
            }
        }

        if (order.getTotal() < restaurant.getMinimumOrderValue()) {
            throw new IllegalArgumentException("Order is below minimum value");
        }

        return order;
    }

    private boolean isValidDeliveryAddress(String deliveryAddress) {
        if (deliveryAddress == null || deliveryAddress.trim().isEmpty()) {
            return false;
        }

        boolean containsLetterOrDigit = false;

        for (char character : deliveryAddress.trim().toCharArray()) {
            if (Character.isISOControl(character)) {
                return false;
            }

            if (Character.isLetterOrDigit(character)) {
                containsLetterOrDigit = true;
            }
        }

        return containsLetterOrDigit;
    }
}