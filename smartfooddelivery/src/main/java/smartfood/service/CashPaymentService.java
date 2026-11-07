package smartfood.service;

import smartfood.domain.Order;
import smartfood.domain.OrderStatus;
import smartfood.domain.Payment;

public class CashPaymentService implements PaymentService {

    @Override
    public Payment processCashPayment(
            Order order,
            double amountReceived) {
        if (order == null) {
            throw new IllegalArgumentException(
                    "Order cannot be null");
        }

        if (order.getStatus() == OrderStatus.CANCELLED) {
            throw new IllegalStateException(
                    "Cancelled orders cannot be paid");
        }

        if (Double.isNaN(amountReceived)
                || Double.isInfinite(amountReceived)
                || amountReceived < 0) {
            throw new IllegalArgumentException(
                    "Received amount must be a non-negative number");
        }

        double amountDue = order.getTotal();

        if (amountReceived < amountDue) {
            return Payment.failed(
                    order.getOrderId(),
                    amountDue,
                    amountReceived);
        }

        return Payment.completed(
                order.getOrderId(),
                amountDue,
                amountReceived);
    }
}