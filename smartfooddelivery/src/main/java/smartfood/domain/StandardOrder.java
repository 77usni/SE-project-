package smartfood.domain;

import java.util.List;

public class StandardOrder extends Order {

    public StandardOrder(int orderId, List<OrderItem> items) {
        super(orderId, items);
    }

    public StandardOrder(
            int orderId,
            List<OrderItem> items,
            user customer) {
        super(orderId, items, customer);
    }

    @Override
    public OrderType getOrderType() {
        return OrderType.STANDARD;
    }
}