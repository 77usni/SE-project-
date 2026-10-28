package smartfood.domain;

import java.util.ArrayList;
import java.util.List;

public class Order {

    private int orderId;
    private List<OrderItem> items;
    private OrderStatus status;
    private double total;

    public Order(int orderId, List<OrderItem> items) {
        this.orderId = orderId;
        this.items = new ArrayList<>(items);
        this.status = OrderStatus.PENDING;
        this.total = calculateTotal();
    }

    public double calculateTotal() {
        double sum = 0;

        for (OrderItem item : items) {
            sum += item.calculateSubtotal();
        }

        return sum;
    }

    public int getOrderId() {
        return orderId;
    }

    public List<OrderItem> getItems() {
        return new ArrayList<>(items);
    }

    public OrderStatus getStatus() {
        return status;
    }

    public double getTotal() {
        return total;
    }
}