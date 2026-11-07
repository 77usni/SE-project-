
package smartfood.domain;

import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

class MealTest {

    @Test
    void testCreateMeal() {
        Meal meal = new Meal(1, "Burger", "Beef burger");

        assertEquals(1, meal.getId());
        assertEquals("Burger", meal.getName());
        assertEquals("Beef burger", meal.getDescription());
    }

    @Test
    void testSetName() {
        Meal meal = new Meal(1, "Burger", "Beef burger");

        meal.setName("Chicken Burger");

        assertEquals("Chicken Burger", meal.getName());
    }

    @Test
    void testSetDescription() {
        Meal meal = new Meal(1, "Burger", "Beef burger");

        meal.setDescription("Chicken burger with cheese");

        assertEquals("Chicken burger with cheese", meal.getDescription());
    }
}
