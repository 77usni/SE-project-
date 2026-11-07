

package smartfood.service;

import static org.junit.jupiter.api.Assertions.assertSame;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.verifyNoInteractions;

import java.util.Collections;

import org.junit.jupiter.api.Test;

import smartfood.domain.Driver;
import smartfood.domain.Order;
import smartfood.domain.OrderStatus;
import smartfood.domain.user;

public class OrderNotificationTest {

    @Test
    void testNotifiesWhenOrderStartsPreparing() {
        NotificationService notificationService =
                mock(NotificationService.class);
        OrderNotificationObserver observer =
                new OrderNotificationObserver(notificationService);
        Order order = new Order(1, Collections.emptyList());

        order.addStatusObserver(observer);
        order.confirm();
        order.startPreparing();

        assertSame(OrderStatus.PREPARING, order.getStatus());
        verify(notificationService).notifyStatusChanged(
                order,
                OrderStatus.CONFIRMED,
                OrderStatus.PREPARING);
    }

    @Test
    void testNotifiesWhenOrderIsCancelled() {
        NotificationService notificationService =
                mock(NotificationService.class);
        OrderNotificationObserver observer =
                new OrderNotificationObserver(notificationService);
        Order order = new Order(2, Collections.emptyList());

        order.addStatusObserver(observer);
        order.cancel();

        assertSame(OrderStatus.CANCELLED, order.getStatus());
        verify(notificationService).notifyStatusChanged(
                order,
                OrderStatus.PENDING,
                OrderStatus.CANCELLED);
    }

    @Test
    void testDoesNotNotifyAfterObserverIsRemoved() {
        NotificationService notificationService =
                mock(NotificationService.class);
        OrderNotificationObserver observer =
                new OrderNotificationObserver(notificationService);
        Order order = new Order(3, Collections.emptyList());

        order.addStatusObserver(observer);
        order.removeStatusObserver(observer);
        order.confirm();
        order.startPreparing();

        verifyNoInteractions(notificationService);
    }

    @Test
    void testNotifiesCustomerWhenDriverIsAssigned() {
        NotificationService notificationService =
                mock(NotificationService.class);
        OrderNotificationObserver observer =
                new OrderNotificationObserver(notificationService);
        user customer = new user(
                1, "Ali", "ali@example.com", "1234", "CUSTOMER");
        Order order = new Order(
                4, Collections.emptyList(), customer);
        Driver driver = new Driver(1, "Ahmad");

        order.addStatusObserver(observer);
        order.assignDriver(driver);

        verify(notificationService).notifyDriverAssigned(
                customer, order, driver);
    }

    @Test
    void testNotifiesCustomerWhenOrderGoesOutForDelivery() {
        NotificationService notificationService =
                mock(NotificationService.class);
        OrderNotificationObserver observer =
                new OrderNotificationObserver(notificationService);
        user customer = new user(
                2, "Sara", "sara@example.com", "5678", "CUSTOMER");
        Order order = new Order(
                5, Collections.emptyList(), customer);

        order.addStatusObserver(observer);
        order.confirm();
        order.startPreparing();
        order.markReady();
        order.markOutForDelivery();

        verify(notificationService).notifyCustomerDeliveryStatus(
                customer, order, OrderStatus.OUT_FOR_DELIVERY);
    }

    @Test
    void testNotifiesCustomerWhenOrderIsDelivered() {
        NotificationService notificationService =
                mock(NotificationService.class);
        OrderNotificationObserver observer =
                new OrderNotificationObserver(notificationService);
        user customer = new user(
                3, "Omar", "omar@example.com", "9012", "CUSTOMER");
        Order order = new Order(
                6, Collections.emptyList(), customer);

        order.addStatusObserver(observer);
        order.confirm();
        order.startPreparing();
        order.markReady();
        order.markOutForDelivery();
        order.markDelivered();

        verify(notificationService).notifyCustomerDeliveryStatus(
                customer, order, OrderStatus.DELIVERED);
    }
}