
package smartfood.domain;

import static org.junit.jupiter.api.Assertions.*;

import java.util.Arrays;
import java.util.Collections;
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
        List<OrderItem> items = new java.util.ArrayList<>();
        items.add(item);

        Order order = new Order(1, items);

        items.clear();

        assertEquals(1, order.getItems().size());
    }
}
