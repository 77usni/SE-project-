package smartfood.service;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

import java.util.Collections;

import org.junit.jupiter.api.Test;

import smartfood.domain.Order;
import smartfood.domain.OrderItem;
import smartfood.domain.Payment;
import smartfood.domain.PaymentMethod;
import smartfood.domain.PaymentStatus;

public class CashPaymentServiceTest {

    @Test
    void testProcessesExactCashPayment() {
        CashPaymentService service = new CashPaymentService();
        Order order = createOrder(50.0);

        Payment payment = service.processCashPayment(order, 50.0);

        assertEquals(PaymentStatus.COMPLETED, payment.getStatus());
        assertEquals(PaymentMethod.CASH, payment.getMethod());
        assertEquals(50.0, payment.getAmountDue(), 0.001);
        assertEquals(50.0, payment.getAmountReceived(), 0.001);
        assertEquals(0.0, payment.getChangeDue(), 0.001);
        assertEquals(order.getOrderId(), payment.getOrderId());
    }

    @Test
    void testReturnsChangeForOverpayment() {
        CashPaymentService service = new CashPaymentService();
        Order order = createOrder(40.0);

        Payment payment = service.processCashPayment(order, 50.0);

        assertEquals(PaymentStatus.COMPLETED, payment.getStatus());
        assertEquals(10.0, payment.getChangeDue(), 0.001);
    }

    @Test
    void testRecordsInsufficientCashAsFailed() {
        CashPaymentService service = new CashPaymentService();
        Order order = createOrder(50.0);

        Payment payment = service.processCashPayment(order, 30.0);

        assertEquals(PaymentStatus.FAILED, payment.getStatus());
        assertEquals(PaymentMethod.CASH, payment.getMethod());
        assertEquals(50.0, payment.getAmountDue(), 0.001);
        assertEquals(30.0, payment.getAmountReceived(), 0.001);
        assertEquals(0.0, payment.getChangeDue(), 0.001);
    }

    @Test
    void testRejectsNegativeAmountReceived() {
        CashPaymentService service = new CashPaymentService();
        Order order = createOrder(50.0);

        assertThrows(IllegalArgumentException.class,
                () -> service.processCashPayment(order, -1.0));
    }

    @Test
    void testRejectsNaNAmountReceived() {
        CashPaymentService service = new CashPaymentService();
        Order order = createOrder(50.0);

        assertThrows(IllegalArgumentException.class,
                () -> service.processCashPayment(order, Double.NaN));
    }

    @Test
    void testRejectsNullOrder() {
        CashPaymentService service = new CashPaymentService();

        assertThrows(IllegalArgumentException.class,
                () -> service.processCashPayment(null, 50.0));
    }

    @Test
    void testRejectsPaymentForCancelledOrder() {
        CashPaymentService service = new CashPaymentService();
        Order order = createOrder(50.0);
        order.cancel();

        assertThrows(IllegalStateException.class,
                () -> service.processCashPayment(order, 50.0));
    }

    private Order createOrder(double price) {
        OrderItem item = new OrderItem(null, 1, price);
        return new Order(1, Collections.singletonList(item));
    }
}