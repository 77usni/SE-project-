package smartfood.domain;

import java.time.LocalDateTime;
import java.util.List;

public class ScheduledOrder extends Order {

    private final LocalDateTime scheduledDeliveryTime;

    public ScheduledOrder(
            int orderId,
            List<OrderItem> items,
            LocalDateTime scheduledDeliveryTime) {
        super(orderId, items);
        this.scheduledDeliveryTime =
                validateFutureTime(scheduledDeliveryTime);
    }

    public ScheduledOrder(
            int orderId,
            List<OrderItem> items,
            user customer,
            LocalDateTime scheduledDeliveryTime) {
        super(orderId, items, customer);
        this.scheduledDeliveryTime =
                validateFutureTime(scheduledDeliveryTime);
    }

    private LocalDateTime validateFutureTime(LocalDateTime time) {
        if (time == null || !time.isAfter(LocalDateTime.now())) {
            throw new IllegalArgumentException(
                    "Scheduled delivery time must be in the future");
        }

        return time;
    }

    @Override
    public OrderType getOrderType() {
        return OrderType.SCHEDULED;
    }

    public LocalDateTime getScheduledDeliveryTime() {
        return scheduledDeliveryTime;
    }
}