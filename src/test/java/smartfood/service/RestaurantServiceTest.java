
package smartfood.service;

import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

import java.util.Arrays;
import java.util.List;

import smartfood.domain.Restaurant;

public class RestaurantServiceTest {

    @Test
    public void testGetOpenRestaurants() {
        Restaurant r1 = new Restaurant(1, "Al Najah Restaurant", true);
        Restaurant r2 = new Restaurant(2, "Food House", false);
        Restaurant r3 = new Restaurant(3, "Pizza House", true);

        RestaurantService service = new RestaurantService();

        List<Restaurant> result = service.getOpenRestaurants(
                Arrays.asList(r1, r2, r3));

        assertEquals(2, result.size());
        assertTrue(result.contains(r1));
        assertFalse(result.contains(r2));
        assertTrue(result.contains(r3));
    }

    @Test
    public void testEmptyRestaurantList() {
        RestaurantService service = new RestaurantService();

        List<Restaurant> result = service.getOpenRestaurants(
                Arrays.asList());

        assertTrue(result.isEmpty());
    }

    @Test
    public void testNullRestaurantList() {
        RestaurantService service = new RestaurantService();

        List<Restaurant> result = service.getOpenRestaurants(null);

        assertTrue(result.isEmpty());
    }
    
    

    @Test
    public void testListContainingNullRestaurant() {
        Restaurant r1 = new Restaurant(1, "Al Najah Restaurant", true);
        Restaurant r2 = new Restaurant(2, "Food House", false);

        RestaurantService service = new RestaurantService();

        List<Restaurant> result = service.getOpenRestaurants(
                Arrays.asList(r1, null, r2));

        assertEquals(1, result.size());
        assertTrue(result.contains(r1));
        assertFalse(result.contains(r2));
    }

}
