package constructors_and_keywords.assigment_problems;

import constructors_and_keywords.assigment_problems.ExamWeekSurgeFeeCalculator.SurgeFeeCalculator;

public class NightlyMultiKitchenReconciliationEngine {

    static class DeliveryAccount {

        static final SurgeFeeCalculator SURGE_CALCULATOR;
        static String campusName;
        static int accountsCreated;

        static {
            SURGE_CALCULATOR = new SurgeFeeCalculator(1.0);
            campusName = "Main Campus";
            accountsCreated = 0;
        }

        private final String studentId;
        private final double orderValue;
        private double amountSettled;

        public DeliveryAccount(String studentId, double orderValue) {
            if (studentId == null || studentId.trim().isEmpty()) {
                throw new IllegalArgumentException("Student id is required");
            }
            if (orderValue < 0) {
                throw new IllegalArgumentException("Order value cannot be negative");
            }
            this.studentId = studentId.trim();
            this.orderValue = orderValue;
            this.amountSettled = 0.0;
            accountsCreated++;
        }

        public DeliveryAccount(String studentId) {
            this(studentId, 0.0);
        }

        String getStudentId() {
            return studentId;
        }

        void settle(double amount) {
            if (amount > 0) {
                amountSettled += amount;
            }
        }

        double getOutstanding() {
            return orderValue - amountSettled;
        }

        final double calculateSurgeFee(int delayMinutes) {
            return SURGE_CALCULATOR.calculateSurgeFee(orderValue, delayMinutes);
        }
    }

    static class PremiumDeliveryAccount extends DeliveryAccount {

        private static final double PREMIUM_WAIVER = 0.5;

        public PremiumDeliveryAccount(String studentId, double orderValue) {
            super(studentId, orderValue);
        }

        double waivedSurgeFee(int delayMinutes) {
            return Math.round(calculateSurgeFee(delayMinutes) * PREMIUM_WAIVER * 100) / 100.0;
        }
    }

    private static int processedCount;
    private static int nullSkippedCount;
    private static int premiumCount;
    private static int regularCount;
    private static double grandTotalSurgeFees;

    static void processAccount(DeliveryAccount account, double amount, int delayMinutes) {
        if (account == null) {
            nullSkippedCount++;
            return;
        }
        if (delayMinutes < 0) {
            System.out.println("Skipped " + account.getStudentId() + ": negative delay minutes");
            return;
        }

        double surgeFee;
        if (account instanceof PremiumDeliveryAccount) {
            PremiumDeliveryAccount premium = (PremiumDeliveryAccount) account;
            surgeFee = premium.waivedSurgeFee(delayMinutes);
            premiumCount++;
        } else {
            surgeFee = account.calculateSurgeFee(delayMinutes);
            regularCount++;
        }

        account.settle(amount);
        grandTotalSurgeFees += surgeFee;
        processedCount++;
        System.out.println(account.getStudentId() + " -> surge Rs " + surgeFee
                + " | outstanding Rs " + Math.round(account.getOutstanding() * 100) / 100.0);
    }

    static void processBatch(DeliveryAccount[] accounts, double[] amounts, int[] delayMinutesArray) {
        processedCount = 0;
        nullSkippedCount = 0;
        premiumCount = 0;
        regularCount = 0;
        grandTotalSurgeFees = 0.0;

        if (accounts == null || amounts == null || delayMinutesArray == null) {
            System.out.println("Batch rejected: all three parallel arrays are required");
            return;
        }
        if (accounts.length != amounts.length || accounts.length != delayMinutesArray.length) {
            System.out.println("Batch rejected: parallel arrays have mismatched lengths ("
                    + accounts.length + ", " + amounts.length + ", " + delayMinutesArray.length
                    + ") - refusing to run rather than settle the wrong amount against the wrong student");
            return;
        }

        for (int index = 0; index < accounts.length; index++) {
            processAccount(accounts[index], amounts[index], delayMinutesArray[index]);
        }

        System.out.println(processedCount + " processed | " + nullSkippedCount + " null skipped | "
                + premiumCount + " premium | " + regularCount + " regular | grand total surge fees = Rs "
                + Math.round(grandTotalSurgeFees * 100) / 100.0);
    }

    public static void main(String[] args) {
        DeliveryAccount[] accounts = {
            new PremiumDeliveryAccount("STU001", 500),
            null,
            new DeliveryAccount("STU002", 300)
        };
        double[] amounts = { 500, 400, 300 };
        int[] delayMinutesArray = { 10, 5, 0 };
        processBatch(accounts, amounts, delayMinutesArray);

        System.out.println();
        System.out.println("Campus: " + DeliveryAccount.campusName
                + " | accounts created: " + DeliveryAccount.accountsCreated);

        System.out.println();
        processBatch(accounts, new double[] { 100, 200 }, delayMinutesArray);
    }
}
