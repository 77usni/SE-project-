
package smartfood.domain;

public class RestaurantMenuItem {

    private int id;
    private Restaurant restaurant;
    private Meal meal;
    private double price;
    private boolean available;

    public RestaurantMenuItem(int id, Restaurant restaurant, Meal meal,
                              double price, boolean available) {
        this.id = id;
        this.restaurant = restaurant;
        this.meal = meal;
        this.price = price;
        this.available = available;
    }

    public int getId() {
        return id;
    }

    public Restaurant getRestaurant() {
        return restaurant;
    }

    public Meal getMeal() {
        return meal;
    }

    public double getPrice() {
        return price;
    }

    public boolean isAvailable() {
        return available;
    }

    public void setPrice(double price) {
        this.price = price;
    }

    public void setAvailable(boolean available) {
        this.available = available;
    }
}
