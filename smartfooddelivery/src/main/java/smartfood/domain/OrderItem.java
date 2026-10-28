
package smartfood.domain;

public class OrderItem {

    private RestaurantMenuItem menuItem;
    private int quantity;
    private double unitPrice;

    public OrderItem(RestaurantMenuItem menuItem,
                     int quantity,
                     double unitPrice) {

        this.menuItem = menuItem;
        this.quantity = quantity;

        this.unitPrice = menuItem != null
                ? menuItem.getPrice()
                : unitPrice;
    }

    public RestaurantMenuItem getMenuItem() {
        return menuItem;
    }

    public int getQuantity() {
        return quantity;
    }

    public double getUnitPrice() {
        return unitPrice;
    }

    public double calculateSubtotal() {
        return quantity * unitPrice;
    }
}
