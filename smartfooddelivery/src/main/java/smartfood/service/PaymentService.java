package smartfood.service;

import smartfood.domain.Order;
import smartfood.domain.Payment;

public interface PaymentService {
    Payment processCashPayment(Order order, double amountReceived);
}