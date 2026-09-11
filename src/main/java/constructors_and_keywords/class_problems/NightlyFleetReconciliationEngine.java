package constructors_and_keywords.class_problems;

import constructors_and_keywords.class_problems.TieredBoardingPenaltyCalculator.BoardingPenaltyCalculator;

public class NightlyFleetReconciliationEngine {

    static class BusTicketAccount {

        static final BoardingPenaltyCalculator PENALTY_CALCULATOR;
        static String depotName;
        static int accountsCreated;

        static {
            PENALTY_CALCULATOR = new BoardingPenaltyCalculator(1.0);
            depotName = "Central Depot";
            accountsCreated = 0;
        }

        private final String bookingId;
        private final double ticketFare;
        private double amountPaid;

        public BusTicketAccount(String bookingId, double ticketFare) {
            if (bookingId == null || bookingId.trim().isEmpty()) {
                throw new IllegalArgumentException("Booking id is required");
            }
            if (ticketFare < 0) {
                throw new IllegalArgumentException("Ticket fare cannot be negative");
            }
            this.bookingId = bookingId.trim();
            this.ticketFare = ticketFare;
            this.amountPaid = 0.0;
            accountsCreated++;
        }

        public BusTicketAccount(String bookingId) {
            this(bookingId, 0.0);
        }

        String getBookingId() {
            return bookingId;
        }

        void settle(double amount) {
            if (amount > 0) {
                amountPaid += amount;
            }
        }

        double getDue() {
            return ticketFare - amountPaid;
        }

        final double calculatePenalty(int minutesLate) {
            return PENALTY_CALCULATOR.calculatePenalty(ticketFare, minutesLate);
        }
    }

    static class SleeperTicketAccount extends BusTicketAccount {

        private static final double SLEEPER_CONCESSION = 0.5;

        public SleeperTicketAccount(String bookingId, double ticketFare) {
            super(bookingId, ticketFare);
        }

        double concessionalPenalty(int minutesLate) {
            return Math.round(calculatePenalty(minutesLate) * SLEEPER_CONCESSION * 100) / 100.0;
        }
    }

    private static int processedCount;
    private static int nullSkippedCount;
    private static int sleeperCount;
    private static int regularCount;
    private static double grandTotalPenalties;

    static void processAccount(BusTicketAccount account, double amount, int minutesLate) {
        if (account == null) {
            nullSkippedCount++;
            return;
        }
        if (minutesLate < 0) {
            System.out.println("Skipped " + account.getBookingId() + ": negative minutes late");
            return;
        }

        double penalty;
        if (account instanceof SleeperTicketAccount) {
            SleeperTicketAccount sleeper = (SleeperTicketAccount) account;
            penalty = sleeper.concessionalPenalty(minutesLate);
            sleeperCount++;
        } else {
            penalty = account.calculatePenalty(minutesLate);
            regularCount++;
        }

        account.settle(amount);
        grandTotalPenalties += penalty;
        processedCount++;
        System.out.println(account.getBookingId() + " -> penalty Rs " + penalty
                + " | due Rs " + Math.round(account.getDue() * 100) / 100.0);
    }

    static void processBatch(BusTicketAccount[] accounts, double[] amounts, int[] minutesLateArray) {
        processedCount = 0;
        nullSkippedCount = 0;
        sleeperCount = 0;
        regularCount = 0;
        grandTotalPenalties = 0.0;

        if (accounts == null || amounts == null || minutesLateArray == null) {
            System.out.println("Batch rejected: all three parallel arrays are required");
            return;
        }
        if (accounts.length != amounts.length || accounts.length != minutesLateArray.length) {
            System.out.println("Batch rejected: parallel arrays have mismatched lengths ("
                    + accounts.length + ", " + amounts.length + ", " + minutesLateArray.length
                    + ") - refusing to run rather than settle the wrong amount against the wrong passenger");
            return;
        }

        for (int index = 0; index < accounts.length; index++) {
            processAccount(accounts[index], amounts[index], minutesLateArray[index]);
        }

        System.out.println(processedCount + " processed | " + nullSkippedCount + " null skipped | "
                + sleeperCount + " sleeper | " + regularCount + " regular | grand total penalties = Rs "
                + Math.round(grandTotalPenalties * 100) / 100.0);
    }

    public static void main(String[] args) {
        BusTicketAccount[] accounts = {
            new SleeperTicketAccount("BK001", 2000),
            null,
            new BusTicketAccount("BK002", 1200)
        };
        double[] amounts = { 1200, 900, 700 };
        int[] minutesLateArray = { 10, 5, 0 };
        processBatch(accounts, amounts, minutesLateArray);

        System.out.println();
        System.out.println("Depot: " + BusTicketAccount.depotName
                + " | accounts created: " + BusTicketAccount.accountsCreated);

        System.out.println();
        processBatch(accounts, new double[] { 100 }, minutesLateArray);
    }
}
