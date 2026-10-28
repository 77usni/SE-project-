
package smartfood.domain;

import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

class RestaurantTest {

    @Test
    void testCreateRestaurant() {
        Restaurant restaurant = new Restaurant(1, "Pizza House", true);

        assertEquals(1, restaurant.getId());
        assertEquals("Pizza House", restaurant.getName());
        assertTrue(restaurant.isOpen());
        assertTrue(restaurant.getMenuItems().isEmpty());
    }

    @Test
    void testUpdateRestaurantDetails() {
        Restaurant restaurant = new Restaurant(1, "Pizza House", true);

        restaurant.setId(2);
        restaurant.setName("Burger House");

        assertEquals(2, restaurant.getId());
        assertEquals("Burger House", restaurant.getName());
    }

    @Test
    void testOpenAndCloseRestaurant() {
        Restaurant restaurant = new Restaurant(1, "Pizza House", true);

        restaurant.setOpen(false);
        assertFalse(restaurant.isOpen());

        restaurant.setOpen(true);
        assertTrue(restaurant.isOpen());
    }
}
