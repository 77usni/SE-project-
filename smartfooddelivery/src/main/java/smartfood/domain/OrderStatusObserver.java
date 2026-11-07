package smartfood.domain;

public interface OrderStatusObserver {

    void onStatusChanged(
            Order order,
            OrderStatus previousStatus,
            OrderStatus newStatus);

    void onDriverAssigned(Order order, Driver driver);
}