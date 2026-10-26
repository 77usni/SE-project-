
package smartfood.service;

import java.util.ArrayList;
import java.util.List;

import smartfood.domain.Restaurant;
import smartfood.domain.RestaurantMenuItem;

public class MenuService {

    public List<RestaurantMenuItem> getAvailableMenuItems(Restaurant restaurant) {

        List<RestaurantMenuItem> availableItems = new ArrayList<>();

        if (restaurant == null) {
            return availableItems;
        }

        for (RestaurantMenuItem item : restaurant.getMenuItems()) {
            if (item != null && item.isAvailable()) {
                availableItems.add(item);
            }
        }

        return availableItems;
    }
}
