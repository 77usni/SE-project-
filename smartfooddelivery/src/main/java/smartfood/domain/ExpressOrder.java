package smartfood.domain;

import java.util.List;

public class ExpressOrder extends Order {

    private static final double DEFAULT_EXPRESS_FEE = 5.0;
    private final double expressFee;

    public ExpressOrder(int orderId, List<OrderItem> items) {
        this(orderId, items, DEFAULT_EXPRESS_FEE);
    }

    public ExpressOrder(
            int orderId,
            List<OrderItem> items,
            double expressFee) {
        super(orderId, items);
        this.expressFee = validateExpressFee(expressFee);
        refreshTotal();
    }

    public ExpressOrder(
            int orderId,
            List<OrderItem> items,
            user customer) {
        this(orderId, items, customer, DEFAULT_EXPRESS_FEE);
    }

    public ExpressOrder(
            int orderId,
            List<OrderItem> items,
            user customer,
            double expressFee) {
        super(orderId, items, customer);
        this.expressFee = validateExpressFee(expressFee);
        refreshTotal();
    }

    private double validateExpressFee(double fee) {
        if (Double.isNaN(fee)
                || Double.isInfinite(fee)
                || fee < 0) {
            throw new IllegalArgumentException(
                    "Express fee must be a non-negative number");
        }

        return fee;
    }

    @Override
    protected double getTypeSpecificFee() {
        return expressFee;
    }

    @Override
    public OrderType getOrderType() {
        return OrderType.EXPRESS;
    }

    public double getExpressFee() {
        return expressFee;
    }
}