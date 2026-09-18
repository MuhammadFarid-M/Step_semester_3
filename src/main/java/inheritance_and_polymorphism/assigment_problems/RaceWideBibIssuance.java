package inheritance_and_polymorphism.assigment_problems;

public class RaceWideBibIssuance {

    static class RaceEntry {

        private static final int MINIMUM_BIB_LENGTH = 4;
        private static final int DISCOUNT_CODE_LENGTH = 5;
        private static int bibCounter = 0;

        public final String entryCode;

        private final String bibNumber;
        private final double entryFee;
        private double amountPaid;

        public RaceEntry(String bibNumber, double entryFee) {
            if (bibNumber == null || bibNumber.trim().length() < MINIMUM_BIB_LENGTH) {
                throw new IllegalArgumentException("bibNumber must be at least "
                        + MINIMUM_BIB_LENGTH + " non-blank characters");
            }
            bibCounter++;
            this.entryCode = "ENT-" + (1000 + bibCounter);
            this.bibNumber = bibNumber.trim();
            this.entryFee = entryFee;
            this.amountPaid = 0.0;
        }

        String getBibNumber() {
            return bibNumber;
        }

        void pay(double amount) {
            if (amount <= 0) {
                System.out.println("Payment rejected: amount must be positive");
                return;
            }
            amountPaid += amount;
        }

        void pay(double amount, String mode) {
            System.out.println("Paying via " + mode);
            pay(amount);
        }

        double getBalanceDue() {
            return entryFee - amountPaid;
        }

        static boolean isValidDiscountCode(String code) {
            if (code == null || code.length() != DISCOUNT_CODE_LENGTH) {
                return false;
            }
            if (code.charAt(0) != 'M') {
                return false;
            }
            for (int index = 1; index <= 3; index++) {
                if (!Character.isDigit(code.charAt(index))) {
                    return false;
                }
            }
            return Character.isUpperCase(code.charAt(4));
        }

        static int getBibCounter() {
            return bibCounter;
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
    }

    static class EliteRunnerEntry extends RunnerEntry {

        private final double sponsorBonus;

        public EliteRunnerEntry(String bibNumber, double entryFee, String category, double sponsorBonus) {
            super(bibNumber, entryFee, category);
            this.sponsorBonus = sponsorBonus;
        }

        double getSponsorBonus() {
            return sponsorBonus;
        }
    }

    static class RelayTeamEntry extends RaceEntry {

        private final int teamSize;

        public RelayTeamEntry(String bibNumber, double entryFee, int teamSize) {
            super(bibNumber, entryFee);
            if (teamSize <= 0) {
                throw new IllegalArgumentException("teamSize must be positive");
            }
            this.teamSize = teamSize;
        }

        int getTeamSize() {
            return teamSize;
        }
    }

    static String settleNight(RaceEntry[] entries) {
        int processed = 0;
        int nullSkipped = 0;
        int relayCount = 0;
        int individualCount = 0;

        if (entries != null) {
            for (RaceEntry entry : entries) {
                if (entry == null) {
                    nullSkipped++;
                    continue;
                }
                if (entry instanceof RelayTeamEntry) {
                    relayCount++;
                } else {
                    individualCount++;
                }
                processed++;
            }
        }

        return processed + " processed | " + nullSkipped + " null skipped | "
                + relayCount + " relay | " + individualCount + " individual";
    }

    public static void main(String[] args) {
        System.out.println("isValidDiscountCode(\"M123A\") -> " + RaceEntry.isValidDiscountCode("M123A"));
        System.out.println("isValidDiscountCode(\"M12A\")  -> " + RaceEntry.isValidDiscountCode("M12A"));
        System.out.println("isValidDiscountCode(\"X123A\") -> " + RaceEntry.isValidDiscountCode("X123A"));

        System.out.println();
        RaceEntry plainEntry = new RaceEntry("BIB5001", 50);
        RunnerEntry runnerEntry = new RunnerEntry("BIB2001", 80, "Open 10K");
        EliteRunnerEntry eliteEntry = new EliteRunnerEntry("BIB3001", 150, "Elite Full Marathon", 500);
        RelayTeamEntry relayEntry = new RelayTeamEntry("BIB4001", 300, 4);

        runnerEntry.pay(10, "UPI");
        System.out.println(runnerEntry.getBibNumber() + " (" + runnerEntry.getCategory()
                + ") balance due -> " + runnerEntry.getBalanceDue());

        System.out.println();
        System.out.println(settleNight(new RaceEntry[] { eliteEntry, null, relayEntry }));

        System.out.println();
        System.out.println("entry codes -> " + plainEntry.entryCode + ", " + runnerEntry.entryCode
                + ", " + eliteEntry.entryCode + ", " + relayEntry.entryCode);
        System.out.println("sponsor bonus " + eliteEntry.getSponsorBonus()
                + " | relay team size " + relayEntry.getTeamSize());
        System.out.println("getBibCounter() -> " + RaceEntry.getBibCounter());

        try {
            new RaceEntry("B1", 50);
        } catch (IllegalArgumentException rejected) {
            System.out.println("rejected construction -> getBibCounter() still "
                    + RaceEntry.getBibCounter());
        }
    }
}
