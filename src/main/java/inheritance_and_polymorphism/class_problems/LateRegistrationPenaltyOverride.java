package inheritance_and_polymorphism.class_problems;

import java.util.Arrays;

public class LateRegistrationPenaltyOverride {

    static class EventTicket {

        private static final int MAX_LATE_FEES = 10;

        private final double basePrice;
        private final double[] lateFeeHistory = new double[MAX_LATE_FEES];
        private double amountPaid;
        private double lateFeesTotal;
        private int lateFeeCount;

        public EventTicket(double basePrice) {
            this.basePrice = basePrice;
            this.amountPaid = 0.0;
            this.lateFeesTotal = 0.0;
            this.lateFeeCount = 0;
        }

        void pay(double amount) {
            if (amount <= 0) {
                return;
            }
            amountPaid += amount;
        }

        double getBalanceDue() {
            return basePrice + lateFeesTotal - amountPaid;
        }

        protected void applyLateFee(double amount) {
            if (amount <= 0 || lateFeeCount >= MAX_LATE_FEES) {
                return;
            }
            lateFeesTotal += amount;
            lateFeeHistory[lateFeeCount] = amount;
            lateFeeCount++;
        }

        double[] getLateFeeHistory() {
            double[] copy = new double[lateFeeCount];
            for (int index = 0; index < lateFeeCount; index++) {
                copy[index] = lateFeeHistory[index];
            }
            return copy;
        }
    }

    static class WorkshopTicket extends EventTicket {

        public WorkshopTicket(double basePrice) {
            super(basePrice);
        }

        @Override
        protected void applyLateFee(double amount) {
            super.applyLateFee(amount * 2);
        }
    }

    public static void main(String[] args) {
        WorkshopTicket workshop = new WorkshopTicket(1200);
        workshop.pay(1200);
        workshop.applyLateFee(100);
        System.out.println("after pay(1200) and applyLateFee(100) -> getBalanceDue() = "
                + workshop.getBalanceDue());

        double[] history = workshop.getLateFeeHistory();
        System.out.println("getLateFeeHistory() -> " + Arrays.toString(history));
        history[0] = 999;
        System.out.println("after tampering with the returned copy -> "
                + Arrays.toString(workshop.getLateFeeHistory()));

        System.out.println();
        EventTicket standard = new EventTicket(1200);
        standard.pay(1200);
        standard.applyLateFee(100);
        System.out.println("base class, same calls -> balance " + standard.getBalanceDue()
                + " | history " + Arrays.toString(standard.getLateFeeHistory()));
    }
}
