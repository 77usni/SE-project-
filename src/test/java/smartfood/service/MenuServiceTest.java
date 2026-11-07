
package smartfood.service;

import org.junit.jupiter.api.Test;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

import smartfood.domain.Meal;
import smartfood.domain.Restaurant;
import smartfood.domain.RestaurantMenuItem;

class MenuServiceTest {

    @Test
    void testGetAvailableMenuItems() {
        Restaurant restaurant = new Restaurant(1, "Pizza House", true);

        Meal burger = new Meal(1, "Burger", "Beef burger");
        Meal pizza = new Meal(2, "Pizza", "Cheese pizza");

        RestaurantMenuItem item1 =
                new RestaurantMenuItem(1, restaurant, burger, 20.0, true);

        RestaurantMenuItem item2 =
                new RestaurantMenuItem(2, restaurant, pizza, 30.0, false);

        restaurant.addMenuItem(item1);
        restaurant.addMenuItem(item2);

        MenuService service = new MenuService();

        List<RestaurantMenuItem> result =
                service.getAvailableMenuItems(restaurant);

        assertEquals(1, result.size());
        assertEquals("Burger", result.get(0).getMeal().getName());
        assertEquals(20.0, result.get(0).getPrice());
    }

    @Test
    void testEmptyMenu() {
        Restaurant restaurant = new Restaurant(1, "Pizza House", true);

        MenuService service = new MenuService();

        assertTrue(service.getAvailableMenuItems(restaurant).isEmpty());
    }

    @Test
    void testNullRestaurant() {
        MenuService service = new MenuService();

        assertTrue(service.getAvailableMenuItems(null).isEmpty());
    }

    @Test
    void testAllMenuItemsUnavailable() {
        Restaurant restaurant = new Restaurant(1, "Pizza House", true);

        Meal pizza = new Meal(1, "Pizza", "Cheese pizza");

        RestaurantMenuItem item =
                new RestaurantMenuItem(1, restaurant, pizza, 30.0, false);

        restaurant.addMenuItem(item);

        MenuService service = new MenuService();

        assertTrue(service.getAvailableMenuItems(restaurant).isEmpty());
    }
}
