
package smartfood.domain;

import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

class RestaurantTest {

    @Test
    void testCreateRestaurant() {
        Restaurant restaurant =
                new Restaurant(1, "Pizza House", true);

        assertEquals(1, restaurant.getId());
        assertEquals("Pizza House", restaurant.getName());
        assertTrue(restaurant.isOpen());
        assertTrue(restaurant.getMenuItems().isEmpty());
    }

    @Test
    void testUpdateRestaurantDetails() {
        Restaurant restaurant =
                new Restaurant(1, "Pizza House", true);

        restaurant.setId(2);
        restaurant.setName("Burger House");

        assertEquals(2, restaurant.getId());
        assertEquals("Burger House", restaurant.getName());
    }

    @Test
    void testOpenAndCloseRestaurant() {
        Restaurant restaurant =
                new Restaurant(1, "Pizza House", true);

        restaurant.setOpen(false);
        assertFalse(restaurant.isOpen());

        restaurant.setOpen(true);
        assertTrue(restaurant.isOpen());
    }

    @Test
    void testDefaultDeliveryPolicies() {
        Restaurant restaurant =
                new Restaurant(1, "Pizza House", true);

        assertEquals("", restaurant.getDeliveryZone());
        assertEquals(0.0,
                restaurant.getMinimumOrderValue(), 0.001);
    }

    @Test
    void testSetDeliveryZone() {
        Restaurant restaurant =
                new Restaurant(1, "Pizza House", true);

        restaurant.setDeliveryZone("Nablus");

        assertEquals("Nablus",
                restaurant.getDeliveryZone());
    }

    @Test
    void testSetMinimumOrderValue() {
        Restaurant restaurant =
                new Restaurant(1, "Pizza House", true);

        restaurant.setMinimumOrderValue(30.0);

        assertEquals(30.0,
                restaurant.getMinimumOrderValue(), 0.001);
    }
}
