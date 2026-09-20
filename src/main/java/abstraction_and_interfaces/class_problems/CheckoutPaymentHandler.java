package abstraction_and_interfaces.class_problems;

public class CheckoutPaymentHandler {

    abstract static class PaymentMethod {

        private static final int TRANSACTION_ID_BASE = 1000;
        private static int paymentsCreated = 0;

        private final String transactionId;

        public PaymentMethod() {
            paymentsCreated++;
            this.transactionId = "TXN-" + (TRANSACTION_ID_BASE + paymentsCreated);
        }

        public abstract String processPayment(double amount);

        String processPayment(double amount, String note) {
            return processPayment(amount) + " (" + note + ")";
        }

        public String getTransactionId() {
            return transactionId;
        }
    }

    static class CreditCardPayment extends PaymentMethod {

        private final String cardNumberLastFour;

        public CreditCardPayment(String cardNumberLastFour) {
            this.cardNumberLastFour = cardNumberLastFour;
        }

        @Override
        public String processPayment(double amount) {
            return "Charged $" + amount + " to card ending " + cardNumberLastFour
                    + " - Txn " + getTransactionId();
        }
    }

    static class CashPayment extends PaymentMethod {

        public CashPayment() {
            super();
        }

        @Override
        public String processPayment(double amount) {
            return "Received $" + amount + " in cash - Txn " + getTransactionId();
        }
    }

    static void printConfirmation(PaymentMethod payment, double amount) {
        System.out.println(payment.processPayment(amount));
    }

    public static void main(String[] args) {
        CreditCardPayment creditCard = new CreditCardPayment("4471");
        System.out.println(creditCard.processPayment(250.0));

        CashPayment cash = new CashPayment();
        System.out.println(cash.processPayment(40.0));

        System.out.println();
        System.out.println(creditCard.processPayment(250.0, "Birthday gift"));

        System.out.println();
        // Upcasting: a CreditCardPayment object is being stored in a PaymentMethod-typed
        // variable. The reference type widens to the parent, but the object itself does not
        // change, so the call below still runs CreditCardPayment's own processPayment.
        PaymentMethod reference = creditCard;
        printConfirmation(reference, 250.0);

        System.out.println();
        System.out.println("new PaymentMethod() will not compile: PaymentMethod is abstract,");
        System.out.println("so only a concrete way of paying can ever be constructed.");
    }
}
