package oop_design_and_uml.class_problems;

public class FoodOrderPaymentSystem {

    interface PaymentMethod {
        String getMethodName();

        boolean pay(double amount);
    }

    interface OrderEventListener {
        void onOrderEvent(String message);
    }

    static class CreditCardPayment implements PaymentMethod {

        private final String cardLastFour;

        public CreditCardPayment(String cardLastFour) {
            this.cardLastFour = cardLastFour;
        }

        public String getCardLastFour() {
            return cardLastFour;
        }

        @Override
        public String getMethodName() {
            return "Credit Card";
        }

        @Override
        public boolean pay(double amount) {
            return amount > 0;
        }
    }

    static class DigitalWalletPayment implements PaymentMethod {

        private final double walletBalance;

        public DigitalWalletPayment(double walletBalance) {
            this.walletBalance = walletBalance;
        }

        @Override
        public String getMethodName() {
            return "Digital Wallet";
        }

        @Override
        public boolean pay(double amount) {
            return amount > 0 && amount <= walletBalance;
        }
    }

    static class CashOnDeliveryPayment implements PaymentMethod {

        @Override
        public String getMethodName() {
            return "Cash on Delivery";
        }

        @Override
        public boolean pay(double amount) {
            return amount > 0;
        }
    }

    static class CustomerNotifier implements OrderEventListener {

        @Override
        public void onOrderEvent(String message) {
            System.out.println("Notification: " + message);
        }
    }

    static class FoodItem {

        private final String itemName;
        private final double unitPrice;

        public FoodItem(String itemName, double unitPrice) {
            this.itemName = itemName;
            this.unitPrice = unitPrice;
        }

        public String getItemName() {
            return itemName;
        }

        public double getUnitPrice() {
            return unitPrice;
        }
    }

    static class Restaurant {

        private final String restaurantName;

        public Restaurant(String restaurantName) {
            this.restaurantName = restaurantName;
        }

        public String getRestaurantName() {
            return restaurantName;
        }
    }

    static class LineItem {

        private final FoodItem foodItem;
        private final int quantity;

        LineItem(FoodItem foodItem, int quantity) {
            this.foodItem = foodItem;
            this.quantity = quantity;
        }

        public FoodItem getFoodItem() {
            return foodItem;
        }

        public int getQuantity() {
            return quantity;
        }

        public double getLineTotal() {
            return foodItem.getUnitPrice() * quantity;
        }

        public String describe() {
            return foodItem.getItemName() + " (Qty " + quantity + ")";
        }
    }

    static class Customer {

        private final String customerName;

        public Customer(String customerName) {
            this.customerName = customerName;
        }

        public String getCustomerName() {
            return customerName;
        }
    }

    enum OrderStatus {
        CREATED("Created"),
        PLACED("Placed"),
        PAID("Paid"),
        PENDING_PAYMENT("Pending Payment");

        private final String label;

        OrderStatus(String label) {
            this.label = label;
        }

        public String label() {
            return label;
        }
    }

    static class Order {

        private static final int MAX_LINE_ITEMS = 50;
        private static int nextOrderNumber = 123;

        private final int orderNumber;
        private final Customer customer;
        private final Restaurant restaurant;
        private final LineItem[] lineItems = new LineItem[MAX_LINE_ITEMS];
        private final OrderEventListener listener;
        private int lineItemCount;
        private OrderStatus status;

        public Order(Customer customer, Restaurant restaurant, OrderEventListener listener) {
            this.orderNumber = nextOrderNumber;
            nextOrderNumber++;
            this.customer = customer;
            this.restaurant = restaurant;
            this.listener = listener;
            this.lineItemCount = 0;
            this.status = OrderStatus.CREATED;
            System.out.println("Order created.");
        }

        public int getOrderNumber() {
            return orderNumber;
        }

        public Customer getCustomer() {
            return customer;
        }

        public Restaurant getRestaurant() {
            return restaurant;
        }

        public OrderStatus getStatus() {
            return status;
        }

        public void addItem(FoodItem foodItem, int quantity) {
            if (status != OrderStatus.CREATED) {
                System.out.println("Cannot add items: the order is already " + status.label() + ".");
                return;
            }
            if (quantity <= 0 || lineItemCount >= MAX_LINE_ITEMS) {
                System.out.println("Cannot add " + foodItem.getItemName() + ": invalid quantity.");
                return;
            }
            lineItems[lineItemCount] = new LineItem(foodItem, quantity);
            lineItemCount++;
        }

        public String describeItems() {
            if (lineItemCount == 0) {
                return "no items";
            }
            StringBuilder description = new StringBuilder();
            for (int index = 0; index < lineItemCount; index++) {
                description.append(index == 0 ? "" : ", ").append(lineItems[index].describe());
            }
            return description.toString();
        }

        public double getTotalAmount() {
            double total = 0.0;
            for (int index = 0; index < lineItemCount; index++) {
                total += lineItems[index].getLineTotal();
            }
            return total;
        }

        public boolean place() {
            if (lineItemCount == 0) {
                System.out.println("Cannot place order: Order must contain at least one item.");
                return false;
            }
            status = OrderStatus.PLACED;
            System.out.println("Order placed successfully.");
            return true;
        }

        public void payWith(PaymentMethod paymentMethod) {
            if (status != OrderStatus.PLACED && status != OrderStatus.PENDING_PAYMENT) {
                System.out.println("Cannot pay: the order has not been placed yet.");
                return;
            }
            if (paymentMethod.pay(getTotalAmount())) {
                status = OrderStatus.PAID;
                System.out.println("Payment via " + paymentMethod.getMethodName()
                        + " successful. Order status: " + status.label());
                notifyListener("Order #" + orderNumber + " placed and paid.");
                return;
            }
            status = OrderStatus.PENDING_PAYMENT;
            System.out.println("Payment via " + paymentMethod.getMethodName()
                    + " failed. Order status: " + status.label());
            notifyListener("Order #" + orderNumber + " placed, awaiting payment.");
        }

        private void notifyListener(String message) {
            if (listener != null) {
                listener.onOrderEvent(message);
            }
        }
    }

    public static void main(String[] args) {
        Customer customer = new Customer("Customer");
        Restaurant restaurant = new Restaurant("Campus Kitchen");
        OrderEventListener notifier = new CustomerNotifier();

        FoodItem pizza = new FoodItem("Pizza", 250.0);
        FoodItem soda = new FoodItem("Soda", 60.0);
        FoodItem burger = new FoodItem("Burger", 180.0);

        Order firstOrder = new Order(customer, restaurant, notifier);
        firstOrder.place();

        System.out.println();
        firstOrder.addItem(pizza, 2);
        firstOrder.addItem(soda, 1);
        System.out.println("Added " + firstOrder.describeItems() + ".");
        if (firstOrder.place()) {
            firstOrder.payWith(new CreditCardPayment("4471"));
        }

        System.out.println();
        Order secondOrder = new Order(customer, restaurant, notifier);
        secondOrder.addItem(burger, 1);
        if (secondOrder.place()) {
            secondOrder.payWith(new DigitalWalletPayment(50.0));
        }

        System.out.println();
        secondOrder.payWith(new CashOnDeliveryPayment());
        System.out.println("A new payment method plugged in without changing Order.");
        System.out.println(firstOrder.getRestaurant().getRestaurantName() + " served "
                + firstOrder.getCustomer().getCustomerName()
                + " for " + String.format("$%.2f", firstOrder.getTotalAmount()));
    }
}
