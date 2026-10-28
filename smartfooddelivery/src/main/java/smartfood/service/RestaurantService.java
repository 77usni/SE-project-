
package smartfood.service;

import java.util.ArrayList;
import java.util.List;
import smartfood.domain.Restaurant;

public class RestaurantService {

    public List<Restaurant> getOpenRestaurants(List<Restaurant> restaurants) {
        List<Restaurant> openRestaurants = new ArrayList<>();

        if (restaurants == null) {
            return openRestaurants;
        }

        for (Restaurant restaurant : restaurants) {
            if (restaurant != null && restaurant.isOpen()) {
                openRestaurants.add(restaurant);
            }
        }

        return openRestaurants;
    }
}
