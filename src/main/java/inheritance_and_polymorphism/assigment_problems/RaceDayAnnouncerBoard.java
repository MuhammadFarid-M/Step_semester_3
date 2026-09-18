package inheritance_and_polymorphism.assigment_problems;

public class RaceDayAnnouncerBoard {

    static class RaceEntry {

        private static final int MAX_LATE_FEES = 10;

        private final String bibNumber;
        private final double entryFee;
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
            lateFeeCount++;
        }

        String announce() {
            return "Race Entry | Bib: " + bibNumber + " | Balance: " + getBalanceDue();
        }
    }

    static class RunnerEntry extends RaceEntry {

        private final String category;

        public RunnerEntry(String bibNumber, double entryFee, String category) {
            super(bibNumber, entryFee);
            this.category = category;
        }

        @Override
        protected void applyLateFee(double amount) {
            super.applyLateFee(amount * 2);
        }

        @Override
        String announce() {
            return "Runner Entry | Bib: " + getBibNumber() + " | Category: " + category
                    + " | Balance: " + getBalanceDue();
        }
    }

    static class RelayTeamEntry extends RaceEntry {

        private final int teamSize;

        public RelayTeamEntry(String bibNumber, double entryFee, int teamSize) {
            super(bibNumber, entryFee);
            this.teamSize = teamSize;
        }

        int getTeamSize() {
            return teamSize;
        }

        @Override
        String announce() {
            return "Relay Team | Bib: " + getBibNumber() + " | Team Size: " + teamSize
                    + " | Balance: " + getBalanceDue();
        }
    }

    static String announceAll(RaceEntry[] entries) {
        StringBuilder report = new StringBuilder();
        if (entries == null) {
            return report.toString();
        }
        for (RaceEntry entry : entries) {
            report.append(entry.announce());
            if (entry instanceof RelayTeamEntry) {
                RelayTeamEntry relay = (RelayTeamEntry) entry;
                report.append(" [Team size via downcast: ").append(relay.getTeamSize()).append("]");
            }
            report.append(" | ");
        }
        return report.toString();
    }

    public static void main(String[] args) {
        RunnerEntry runnerEntry = new RunnerEntry("BIB2001", 80, "Open 10K");
        runnerEntry.pay(30);
        runnerEntry.applyLateFee(20);
        RelayTeamEntry relayEntry = new RelayTeamEntry("BIB4001", 300, 4);

        RaceEntry[] fleet = { runnerEntry, relayEntry };
        System.out.println(announceAll(fleet));

        System.out.println();
        RaceEntry plain = new RaceEntry("BIB5001", 50);
        try {
            RelayTeamEntry bad = (RelayTeamEntry) plain;
            System.out.println("downcast succeeded, team size = " + bad.getTeamSize());
        } catch (ClassCastException expected) {
            System.out.println("(RelayTeamEntry) plain -> ClassCastException at runtime");
        }

        System.out.println("guarded instead: plain instanceof RelayTeamEntry -> "
                + (plain instanceof RelayTeamEntry));
    }
}
