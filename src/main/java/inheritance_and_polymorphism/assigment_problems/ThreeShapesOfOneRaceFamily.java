package inheritance_and_polymorphism.assigment_problems;

public class ThreeShapesOfOneRaceFamily {

    static class RaceEntry {

        private final String bibNumber;
        private final double entryFee;
        private double amountPaid;

        public RaceEntry(String bibNumber, double entryFee) {
            this.bibNumber = bibNumber;
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

        String getCategory() {
            return category;
        }

        @Override
        String announce() {
            return "Runner Entry | Bib: " + getBibNumber() + " | Category: " + category
                    + " | Balance: " + getBalanceDue();
        }
    }

    static class EliteRunnerEntry extends RunnerEntry {

        private final double sponsorBonus;

        public EliteRunnerEntry(String bibNumber, double entryFee, String category, double sponsorBonus) {
            super(bibNumber, entryFee, category);
            this.sponsorBonus = sponsorBonus;
        }

        @Override
        String announce() {
            return "Elite Runner | Bib: " + getBibNumber() + " | Category: " + getCategory()
                    + " | Sponsor Bonus: " + sponsorBonus + " | Balance: " + getBalanceDue();
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

    static String classifyGeneration(RaceEntry entry) {
        if (entry == null) {
            return "No entry supplied";
        }
        if (entry instanceof EliteRunnerEntry) {
            return "Multilevel descendant (3 generations deep)";
        }
        if (entry instanceof RelayTeamEntry) {
            return "Hierarchical sibling (independent branch)";
        }
        if (entry instanceof RunnerEntry) {
            return "Direct subclass (2 generations deep)";
        }
        return "Base of the family tree";
    }

    static double getTotalBalanceDue(RaceEntry[] entries) {
        double total = 0.0;
        if (entries != null) {
            for (RaceEntry entry : entries) {
                if (entry != null) {
                    total += entry.getBalanceDue();
                }
            }
        }
        return total;
    }

    public static void main(String[] args) {
        RunnerEntry runnerEntry = new RunnerEntry("BIB2001", 80, "Open 10K");
        EliteRunnerEntry eliteEntry = new EliteRunnerEntry("BIB3001", 150, "Elite Full Marathon", 500);
        RelayTeamEntry relayEntry = new RelayTeamEntry("BIB4001", 300, 4);

        RaceEntry[] entries = { runnerEntry, eliteEntry, relayEntry };
        for (RaceEntry entry : entries) {
            System.out.println(entry.announce());
        }

        System.out.println();
        for (RaceEntry entry : entries) {
            System.out.println(entry.getBibNumber() + " -> " + classifyGeneration(entry));
        }

        System.out.println();
        System.out.println("getTotalBalanceDue(...) -> " + getTotalBalanceDue(entries));
    }
}
