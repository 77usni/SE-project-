package smartfood.domain;

public class Payment {

    private final int orderId;
    private final PaymentMethod method;
    private final double amountDue;
    private final double amountReceived;
    private final PaymentStatus status;
    private final double changeDue;

    private Payment(
            int orderId,
            PaymentMethod method,
            double amountDue,
            double amountReceived,
            PaymentStatus status,
            double changeDue) {
        this.orderId = orderId;
        this.method = method;
        this.amountDue = amountDue;
        this.amountReceived = amountReceived;
        this.status = status;
        this.changeDue = changeDue;
    }

    public static Payment completed(
            int orderId,
            double amountDue,
            double amountReceived) {
        validateAmounts(orderId, amountDue, amountReceived);

        if (amountReceived < amountDue) {
            throw new IllegalArgumentException(
                    "Received amount is less than the amount due");
        }

        return new Payment(
                orderId,
                PaymentMethod.CASH,
                amountDue,
                amountReceived,
                PaymentStatus.COMPLETED,
                amountReceived - amountDue);
    }

    public static Payment failed(
            int orderId,
            double amountDue,
            double amountReceived) {
        validateAmounts(orderId, amountDue, amountReceived);

        if (amountReceived >= amountDue) {
            throw new IllegalArgumentException(
                    "A failed payment must have insufficient cash");
        }

        return new Payment(
                orderId,
                PaymentMethod.CASH,
                amountDue,
                amountReceived,
                PaymentStatus.FAILED,
                0);
    }

    private static void validateAmounts(
            int orderId,
            double amountDue,
            double amountReceived) {
        if (orderId <= 0) {
            throw new IllegalArgumentException(
                    "Order ID must be positive");
        }

        if (Double.isNaN(amountDue)
                || Double.isInfinite(amountDue)
                || amountDue < 0) {
            throw new IllegalArgumentException(
                    "Amount due must be a non-negative number");
        }

        if (Double.isNaN(amountReceived)
                || Double.isInfinite(amountReceived)
                || amountReceived < 0) {
            throw new IllegalArgumentException(
                    "Received amount must be a non-negative number");
        }
    }

    public int getOrderId() {
        return orderId;
    }

    public PaymentMethod getMethod() {
        return method;
    }

    public double getAmountDue() {
        return amountDue;
    }

    public double getAmountReceived() {
        return amountReceived;
    }

    public PaymentStatus getStatus() {
        return status;
    }

    public double getChangeDue() {
        return changeDue;
    }
}