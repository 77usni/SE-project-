package smartfood.service;

import smartfood.domain.Driver;
import smartfood.domain.Order;
import smartfood.domain.OrderStatus;
import smartfood.domain.user;

public interface NotificationService {

    void notifyStatusChanged(
            Order order,
            OrderStatus previousStatus,
            OrderStatus newStatus);

    void notifyDriverAssigned(
            user customer,
            Order order,
            Driver driver);

    void notifyCustomerDeliveryStatus(
            user customer,
            Order order,
            OrderStatus status);
}