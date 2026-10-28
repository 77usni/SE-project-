
package smartfood.domain;

import static org.junit.jupiter.api.Assertions.*;
import org.junit.jupiter.api.Test;

public class OrderItemTest {

    @Test
    void testCalculateSubtotal() {
        OrderItem item = new OrderItem(null, 3, 10.0);

        assertEquals(30.0, item.calculateSubtotal(), 0.001);
    }

    @Test
    void testGetQuantity() {
        OrderItem item = new OrderItem(null, 2, 15.0);

        assertEquals(2, item.getQuantity());
    }

    @Test
    void testGetUnitPrice() {
        OrderItem item = new OrderItem(null, 2, 15.0);

        assertEquals(15.0, item.getUnitPrice(), 0.001);
    }

    @Test
    void testUsesRestaurantMenuPrice() {

        RestaurantMenuItem menuItem =
                new RestaurantMenuItem(
                        1, null, null, 20.0, true);

        OrderItem item = new OrderItem(menuItem, 2, 5.0);

        assertEquals(20.0, item.getUnitPrice(), 0.001);
        assertEquals(40.0, item.calculateSubtotal(), 0.001);
    }
}
