
package smartfood.domain;

import java.util.ArrayList;
import java.util.List;

public class Restaurant {

    private int id;
    private String name;
    private boolean isOpen;
    private List<RestaurantMenuItem> menuItems;

    public Restaurant(int id, String name, boolean isOpen) {
        this.id = id;
        this.name = name;
        this.isOpen = isOpen;
        this.menuItems = new ArrayList<>();
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public boolean isOpen() {
        return isOpen;
    }

    public void setOpen(boolean open) {
        isOpen = open;
    }

    public void addMenuItem(RestaurantMenuItem item) {
        if (item != null && item.getRestaurant() == this) {
            menuItems.add(item);
        }
    }

    public List<RestaurantMenuItem> getMenuItems() {
        return new ArrayList<>(menuItems);
    }
}
