package inheritance_and_polymorphism.assigment_problems;

import java.util.Arrays;

public class LateWithdrawalPenaltyOverride {

    static class RaceEntry {

        private static final int MAX_LATE_FEES = 10;

        private final String bibNumber;
        private final double entryFee;
        private final double[] lateFeeHistory = new double[MAX_LATE_FEES];
        private double amountPaid;
        private double lateFeesTotal;
        private int lateFeeCount;

        public RaceEntry(String bibNumber, double entryFee) {
            this.bibNumber = bibNumber;
            this.entryFee = entryFee;
            this.amountPaid = 0.0;
            this.lateFeesTotal = 0.0;
            this.lateFeeCount = 0;
        }

        String getBibNumber() {
            return bibNumber;
        }

        void pay(double amount) {
            if (amount <= 0) {
                return;
            }
            amountPaid += amount;
        }

        double getBalanceDue() {
            return entryFee + lateFeesTotal - amountPaid;
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

    static class RunnerEntry extends RaceEntry {

        private final String category;

        public RunnerEntry(String bibNumber, double entryFee, String category) {
            super(bibNumber, entryFee);
            this.category = category;
        }

        String getCategory() {
            return category;
        }

        @Override
        protected void applyLateFee(double amount) {
            super.applyLateFee(amount * 2);
        }
    }

    public static void main(String[] args) {
        RunnerEntry runner = new RunnerEntry("BIB2001", 80, "Open 10K");
        runner.pay(30);
        runner.applyLateFee(20);
        System.out.println(runner.getBibNumber() + " (" + runner.getCategory()
                + ") after pay(30) and applyLateFee(20) -> getBalanceDue() = " + runner.getBalanceDue());

        double[] history = runner.getLateFeeHistory();
        System.out.println("getLateFeeHistory() -> " + Arrays.toString(history));
        history[0] = 999;
        System.out.println("after tampering with the returned copy -> "
                + Arrays.toString(runner.getLateFeeHistory()));

        System.out.println();
        RaceEntry plain = new RaceEntry("BIB5001", 80);
        plain.pay(30);
        plain.applyLateFee(20);
        System.out.println("base class, same calls -> balance " + plain.getBalanceDue()
                + " | history " + Arrays.toString(plain.getLateFeeHistory()));
    }
}
