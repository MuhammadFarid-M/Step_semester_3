package inheritance_and_polymorphism.assigment_problems;

public class RaceEntryFoundation {

    static class RaceEntry {

        private static final int MINIMUM_BIB_LENGTH = 4;

        private final String bibNumber;
        private final double entryFee;
        private double amountPaid;

        public RaceEntry(String bibNumber, double entryFee) {
            if (bibNumber == null || bibNumber.trim().length() < MINIMUM_BIB_LENGTH) {
                throw new IllegalArgumentException("bibNumber must be at least "
                        + MINIMUM_BIB_LENGTH + " non-blank characters");
            }
            this.bibNumber = bibNumber.trim();
            this.entryFee = entryFee;
            this.amountPaid = 0.0;
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
            return entryFee - amountPaid;
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

    static String registerBatch(String[] bibNumbers, double entryFee) {
        int registered = 0;
        int rejected = 0;

        if (bibNumbers != null) {
            for (String bibNumber : bibNumbers) {
                try {
                    new RaceEntry(bibNumber, entryFee);
                    registered++;
                } catch (IllegalArgumentException rejectedAttempt) {
                    rejected++;
                }
            }
        }

        return "Registered: " + registered + " | Rejected: " + rejected;
    }

    public static void main(String[] args) {
        try {
            new RaceEntry("B1", 50);
            System.out.println("new RaceEntry(\"B1\", 50) -> accepted");
        } catch (IllegalArgumentException rejected) {
            System.out.println("new RaceEntry(\"B1\", 50) -> construction rejected");
        }

        RunnerEntry runner = new RunnerEntry("BIB2001", 80, "Open 10K");
        runner.pay(30);
        System.out.println(runner.getBibNumber() + " (" + runner.getCategory()
                + ") after pay(30) -> getBalanceDue() = " + runner.getBalanceDue());

        System.out.println();
        System.out.println(registerBatch(new String[] { "BIB1", "B1", "BIB2" }, 80));
    }
}
