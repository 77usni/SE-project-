
package smartfood.domain;

import static org.junit.jupiter.api.Assertions.*;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import org.junit.jupiter.api.Test;

public class OrderTest {

    @Test
    void testOrderStartsPending() {
        OrderItem item = new OrderItem(null, 2, 10.0);
        Order order = new Order(1, Arrays.asList(item));

        assertEquals(OrderStatus.PENDING, order.getStatus());
    }

    @Test
    void testCalculateTotal() {
        OrderItem item1 = new OrderItem(null, 2, 10.0);
        OrderItem item2 = new OrderItem(null, 3, 5.0);
        Order order = new Order(1, Arrays.asList(item1, item2));

        assertEquals(35.0, order.calculateTotal(), 0.001);
        assertEquals(35.0, order.getTotal(), 0.001);
    }

    @Test
    void testOrderId() {
        OrderItem item = new OrderItem(null, 1, 20.0);
        Order order = new Order(15, Arrays.asList(item));

        assertEquals(15, order.getOrderId());
    }

    @Test
    void testGetItems() {
        OrderItem item = new OrderItem(null, 2, 12.0);
        Order order = new Order(1, Arrays.asList(item));

        assertEquals(1, order.getItems().size());
        assertSame(item, order.getItems().get(0));
    }

    @Test
    void testItemsListIsCopied() {
        OrderItem item = new OrderItem(null, 1, 10.0);
        List<OrderItem> items = new ArrayList<>();
        items.add(item);

        Order order = new Order(1, items);
        items.clear();

        assertEquals(1, order.getItems().size());
    }

    @Test
    void testCancelPendingOrder() {
        OrderItem item = new OrderItem(null, 2, 10.0);
        Order order = new Order(1, Arrays.asList(item));

        assertDoesNotThrow(() -> order.cancel());
        assertEquals(OrderStatus.CANCELLED, order.getStatus());
    }

    @Test
    void testCannotCancelPreparingOrder() {
        OrderItem item = new OrderItem(null, 1, 20.0);
        Order order = new Order(2, Arrays.asList(item));

        order.confirm();
        order.startPreparing();

        assertThrows(IllegalStateException.class,
                () -> order.cancel());

        assertEquals(OrderStatus.PREPARING, order.getStatus());
    }

    @Test
    void testCannotCancelTwice() {
        OrderItem item = new OrderItem(null, 1, 15.0);
        Order order = new Order(3, Arrays.asList(item));

        order.cancel();

        assertThrows(IllegalStateException.class,
                () -> order.cancel());

        assertEquals(OrderStatus.CANCELLED, order.getStatus());
    }

    @Test
    void testCannotPrepareCancelledOrder() {
        OrderItem item = new OrderItem(null, 1, 25.0);
        Order order = new Order(4, Arrays.asList(item));

        order.cancel();

        assertThrows(IllegalStateException.class,
                () -> order.startPreparing());

        assertEquals(OrderStatus.CANCELLED, order.getStatus());
    }

    @Test
    void testCompleteOrderLifecycle() {
        Order order = new Order(5, new ArrayList<>());

        order.confirm();
        assertEquals(OrderStatus.CONFIRMED, order.getStatus());

        order.startPreparing();
        assertEquals(OrderStatus.PREPARING, order.getStatus());

        order.markReady();
        assertEquals(OrderStatus.READY, order.getStatus());

        order.markOutForDelivery();
        assertEquals(OrderStatus.OUT_FOR_DELIVERY, order.getStatus());

        order.markDelivered();
        assertEquals(OrderStatus.DELIVERED, order.getStatus());
    }

    @Test
    void testRejectsInvalidStatusTransition() {
        Order order = new Order(6, new ArrayList<>());

        assertThrows(IllegalStateException.class,
                () -> order.startPreparing());

        assertEquals(OrderStatus.PENDING, order.getStatus());
    }

    @Test
    void testDeliveredOrderCannotBeModified() {
        Order order = new Order(7, new ArrayList<>());

        order.confirm();
        order.startPreparing();
        order.markReady();
        order.markOutForDelivery();
        order.markDelivered();

        assertThrows(IllegalStateException.class,
                () -> order.confirm());

        assertEquals(OrderStatus.DELIVERED, order.getStatus());
    }
}