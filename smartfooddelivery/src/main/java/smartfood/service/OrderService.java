
package smartfood.service;

import java.util.List;

import smartfood.domain.Order;
import smartfood.domain.OrderItem;
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
}
