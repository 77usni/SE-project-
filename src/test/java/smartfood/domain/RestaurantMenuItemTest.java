
package smartfood.domain;

import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

class RestaurantMenuItemTest {

    @Test
    void restaurantCanAddMealWithPrice() {
        Restaurant restaurant = new Restaurant(1, "Pizza House", true);
        Meal burger = new Meal(1, "Burger", "Beef burger");

        RestaurantMenuItem item =
                new RestaurantMenuItem(1, restaurant, burger, 20.0, true);

        restaurant.addMenuItem(item);

        assertEquals(1, restaurant.getMenuItems().size());
        assertEquals(20.0, restaurant.getMenuItems().get(0).getPrice());
        assertSame(burger, restaurant.getMenuItems().get(0).getMeal());
    }

    @Test
    void sameMealCanHaveDifferentPrices() {
        Restaurant restaurant1 = new Restaurant(1, "Pizza House", true);
        Restaurant restaurant2 = new Restaurant(2, "Burger House", true);
        Meal burger = new Meal(1, "Burger", "Beef burger");

        RestaurantMenuItem item1 =
                new RestaurantMenuItem(1, restaurant1, burger, 20.0, true);

        RestaurantMenuItem item2 =
                new RestaurantMenuItem(2, restaurant2, burger, 25.0, true);

        restaurant1.addMenuItem(item1);
        restaurant2.addMenuItem(item2);

        assertEquals(20.0, restaurant1.getMenuItems().get(0).getPrice());
        assertEquals(25.0, restaurant2.getMenuItems().get(0).getPrice());
    }

    @Test
    void restaurantCannotAddItemBelongingToAnotherRestaurant() {
        Restaurant restaurant1 = new Restaurant(1, "Pizza House", true);
        Restaurant restaurant2 = new Restaurant(2, "Burger House", true);
        Meal burger = new Meal(1, "Burger", "Beef burger");

        RestaurantMenuItem item =
                new RestaurantMenuItem(1, restaurant2, burger, 25.0, true);

        restaurant1.addMenuItem(item);

        assertTrue(restaurant1.getMenuItems().isEmpty());
    }
}
