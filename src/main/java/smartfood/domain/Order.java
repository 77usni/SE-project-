package smartfood.domain;

import java.util.ArrayList;
import java.util.List;

import smartfood.service.DeliveryCostStrategy;
import smartfood.service.PricingStrategy;

public class Order {

    private int orderId;
    private List<OrderItem> items;
    private OrderStatus status;
    private double total;
    private double deliveryDistanceKm;
    private double deliveryCost;
    private Driver assignedDriver;
    private user customer;
    private List<OrderStatusObserver> statusObservers;
    private PricingStrategy pricingStrategy;

    public Order(int orderId, List<OrderItem> items) {
        this.orderId = orderId;
        this.items = new ArrayList<>(items);
        this.status = OrderStatus.PENDING;
        this.deliveryDistanceKm = 0;
        this.deliveryCost = 0;
        this.statusObservers = new ArrayList<>();
        this.total = calculateTotal();
    }

    public Order(int orderId, List<OrderItem> items, user customer) {
        this(orderId, items);
        setCustomer(customer);
    }

    public double calculateTotal() {
        double sum = 0;

        for (OrderItem item : items) {
            sum += item.calculateSubtotal();
        }

        return sum + deliveryCost;
    }

    public double calculateDeliveryCost(
            double distanceKm,
            DeliveryCostStrategy strategy) {
        ensureNotDelivered();

        if (Double.isNaN(distanceKm)
                || Double.isInfinite(distanceKm)
                || distanceKm < 0) {
            throw new IllegalArgumentException(
                    "Distance must be a non-negative number");
        }

        if (strategy == null) {
            throw new IllegalArgumentException(
                    "Delivery cost strategy cannot be null");
        }

        double calculatedCost = strategy.calculateCost(distanceKm);

        if (Double.isNaN(calculatedCost)
                || Double.isInfinite(calculatedCost)
                || calculatedCost < 0) {
            throw new IllegalArgumentException(
                    "Delivery cost must be a non-negative number");
        }

        double newBaseAmount = calculateFoodTotal() + calculatedCost;
        double newTotal = calculatePricedAmount(
                newBaseAmount, pricingStrategy);

        deliveryDistanceKm = distanceKm;
        deliveryCost = calculatedCost;
        total = newTotal;

        return deliveryCost;
    }

    public double applyPricingStrategy(PricingStrategy strategy) {
        ensureNotDelivered();

        if (strategy == null) {
            throw new IllegalArgumentException(
                    "Pricing strategy cannot be null");
        }

        double newTotal = calculatePricedAmount(
                calculateTotal(), strategy);

        pricingStrategy = strategy;
        total = newTotal;

        return total;
    }

    private double calculatePricedAmount(
            double baseAmount,
            PricingStrategy strategy) {
        double result = strategy == null
                ? baseAmount
                : strategy.calculatePrice(baseAmount);

        if (Double.isNaN(result)
                || Double.isInfinite(result)
                || result < 0) {
            throw new IllegalArgumentException(
                    "Final price must be a non-negative number");
        }

        return result;
    }

    private double calculateFoodTotal() {
        double sum = 0;

        for (OrderItem item : items) {
            sum += item.calculateSubtotal();
        }

        return sum;
    }

    public void cancel() {
        if (status != OrderStatus.PENDING) {
            throw new IllegalStateException(
                    "Only pending orders can be cancelled");
        }

        changeStatus(OrderStatus.CANCELLED);
    }

    public void confirm() {
        ensureNotDelivered();

        if (status != OrderStatus.PENDING) {
            throw new IllegalStateException(
                    "Only pending orders can be confirmed");
        }

        changeStatus(OrderStatus.CONFIRMED);
    }

    public void startPreparing() {
        ensureNotDelivered();

        if (status != OrderStatus.CONFIRMED) {
            throw new IllegalStateException(
                    "Only confirmed orders can start preparing");
        }

        changeStatus(OrderStatus.PREPARING);
    }

    public void markReady() {
        ensureNotDelivered();

        if (status != OrderStatus.PREPARING) {
            throw new IllegalStateException(
                    "Only preparing orders can be marked ready");
        }

        changeStatus(OrderStatus.READY);
    }

    public void markOutForDelivery() {
        ensureNotDelivered();

        if (status != OrderStatus.READY) {
            throw new IllegalStateException(
                    "Only ready orders can go out for delivery");
        }

        changeStatus(OrderStatus.OUT_FOR_DELIVERY);
    }

    public void markDelivered() {
        ensureNotDelivered();

        if (status != OrderStatus.OUT_FOR_DELIVERY) {
            throw new IllegalStateException(
                    "Only orders out for delivery can be delivered");
        }

        changeStatus(OrderStatus.DELIVERED);
    }

    public void assignDriver(Driver driver) {
        ensureNotDelivered();

        if (driver == null) {
            throw new IllegalArgumentException("Driver cannot be null");
        }

        if (assignedDriver != null) {
            throw new IllegalStateException(
                    "A driver is already assigned to this order");
        }

        if (!driver.isAvailable()) {
            throw new IllegalStateException("Driver is not available");
        }

        driver.markBusy();
        assignedDriver = driver;

        for (OrderStatusObserver observer
                : new ArrayList<>(statusObservers)) {
            observer.onDriverAssigned(this, driver);
        }
    }

    public void addStatusObserver(OrderStatusObserver observer) {
        if (observer == null) {
            throw new IllegalArgumentException(
                    "Observer cannot be null");
        }

        if (!statusObservers.contains(observer)) {
            statusObservers.add(observer);
        }
    }

    public void removeStatusObserver(OrderStatusObserver observer) {
        statusObservers.remove(observer);
    }

    private void ensureNotDelivered() {
        if (status == OrderStatus.DELIVERED) {
            throw new IllegalStateException(
                    "Delivered orders cannot be modified");
        }
    }

    private void changeStatus(OrderStatus newStatus) {
        OrderStatus previousStatus = status;
        status = newStatus;

        for (OrderStatusObserver observer
                : new ArrayList<>(statusObservers)) {
            observer.onStatusChanged(
                    this, previousStatus, newStatus);
        }
    }

    public int getOrderId() {
        return orderId;
    }

    public List<OrderItem> getItems() {
        return new ArrayList<>(items);
    }

    public OrderStatus getStatus() {
        return status;
    }

    public double getTotal() {
        return total;
    }

    public double getDeliveryDistanceKm() {
        return deliveryDistanceKm;
    }

    public double getDeliveryCost() {
        return deliveryCost;
    }

    public Driver getAssignedDriver() {
        return assignedDriver;
    }

    public user getCustomer() {
        return customer;
    }

    public void setCustomer(user customer) {
        ensureNotDelivered();

        if (customer == null) {
            throw new IllegalArgumentException(
                    "Customer cannot be null");
        }

        this.customer = customer;
    }
}