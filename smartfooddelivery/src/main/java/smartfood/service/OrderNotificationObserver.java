package smartfood.service;

import smartfood.domain.Driver;
import smartfood.domain.Order;
import smartfood.domain.OrderStatus;
import smartfood.domain.OrderStatusObserver;
import smartfood.domain.user;

public class OrderNotificationObserver
        implements OrderStatusObserver {

    private final NotificationService notificationService;

    public OrderNotificationObserver(
            NotificationService notificationService) {

        if (notificationService == null) {
            throw new IllegalArgumentException(
                    "Notification service cannot be null");
        }

        this.notificationService = notificationService;
    }

    @Override
    public void onStatusChanged(
            Order order,
            OrderStatus previousStatus,
            OrderStatus newStatus) {

        notificationService.notifyStatusChanged(
                order, previousStatus, newStatus);

        user customer = order.getCustomer();

        if (customer == null) {
            return;
        }

        if (newStatus == OrderStatus.OUT_FOR_DELIVERY
                || newStatus == OrderStatus.DELIVERED) {
            notificationService.notifyCustomerDeliveryStatus(
                    customer, order, newStatus);
        }
    }

    @Override
    public void onDriverAssigned(Order order, Driver driver) {
        user customer = order.getCustomer();

        if (customer != null) {
            notificationService.notifyDriverAssigned(
                    customer, order, driver);
        }
    }
}