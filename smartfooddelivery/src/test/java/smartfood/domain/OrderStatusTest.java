
package smartfood.domain;

import static org.junit.jupiter.api.Assertions.*;
import org.junit.jupiter.api.Test;

public class OrderStatusTest {

    @Test
    void testPendingStatusExists() {
        assertEquals("PENDING", OrderStatus.PENDING.name());
    }

    @Test
    void testCancelledStatusExists() {
        assertEquals("CANCELLED", OrderStatus.CANCELLED.name());
    }

    @Test
    void testDeliveredStatusExists() {
        assertEquals("DELIVERED", OrderStatus.DELIVERED.name());
    }
}
