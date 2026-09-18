package inheritance_and_polymorphism.class_problems;

public class ThreeShapesOfOneFamilyTree {

    static class EventTicket {

        private final String attendeeId;
        private final double basePrice;
        private double amountPaid;

        public EventTicket(String attendeeId, double basePrice) {
            this.attendeeId = attendeeId;
            this.basePrice = basePrice;
            this.amountPaid = 0.0;
        }

        String getAttendeeId() {
            return attendeeId;
        }

        void pay(double amount) {
            if (amount <= 0) {
                return;
            }
            amountPaid += amount;
        }

        double getBalanceDue() {
            return basePrice - amountPaid;
        }

        String printTicket() {
            return "Standard Event Ticket | Balance Due: " + getBalanceDue();
        }
    }

    static class WorkshopTicket extends EventTicket {

        private final String track;

        public WorkshopTicket(String attendeeId, double basePrice, String track) {
            super(attendeeId, basePrice);
            this.track = track;
        }

        String getTrack() {
            return track;
        }

        @Override
        String printTicket() {
            return "Workshop Ticket | Track: " + track + " | Balance Due: " + getBalanceDue();
        }
    }

    static class PremiumWorkshopTicket extends WorkshopTicket {

        private final double kitFee;

        public PremiumWorkshopTicket(String attendeeId, double basePrice, String track, double kitFee) {
            super(attendeeId, basePrice, track);
            this.kitFee = kitFee;
        }

        @Override
        String printTicket() {
            return "Premium Workshop Ticket | Track: " + getTrack()
                    + " | Kit Fee: " + kitFee + " | Balance Due: " + getBalanceDue();
        }
    }

    static class HackathonTicket extends EventTicket {

        private final String teamName;

        public HackathonTicket(String attendeeId, double basePrice, String teamName) {
            super(attendeeId, basePrice);
            this.teamName = teamName;
        }

        @Override
        String printTicket() {
            return "Hackathon Ticket | Team: " + teamName + " | Balance Due: " + getBalanceDue();
        }
    }

    static String classifyGeneration(EventTicket ticket) {
        if (ticket == null) {
            return "No ticket supplied";
        }
        if (ticket instanceof PremiumWorkshopTicket) {
            return "Multilevel descendant (3 generations deep)";
        }
        if (ticket instanceof HackathonTicket) {
            return "Hierarchical sibling (independent branch)";
        }
        if (ticket instanceof WorkshopTicket) {
            return "Direct subclass (2 generations deep)";
        }
        return "Base of the family tree";
    }

    static double getTotalBalanceDue(EventTicket[] tickets) {
        double total = 0.0;
        if (tickets != null) {
            for (EventTicket ticket : tickets) {
                if (ticket != null) {
                    total += ticket.getBalanceDue();
                }
            }
        }
        return total;
    }

    public static void main(String[] args) {
        EventTicket standardTicket = new EventTicket("STU1", 500);
        WorkshopTicket workshopTicket = new WorkshopTicket("STU2", 1200, "AI/ML");
        PremiumWorkshopTicket premiumTicket = new PremiumWorkshopTicket("STU3", 2000, "Cloud Native", 300);
        HackathonTicket hackathonTicket = new HackathonTicket("STU4", 800, "Byte Force");

        EventTicket[] tickets = { standardTicket, workshopTicket, premiumTicket, hackathonTicket };
        for (EventTicket ticket : tickets) {
            System.out.println(ticket.printTicket());
        }

        System.out.println();
        for (EventTicket ticket : tickets) {
            System.out.println(ticket.getAttendeeId() + " -> " + classifyGeneration(ticket));
        }

        System.out.println();
        System.out.println("getTotalBalanceDue(...) -> " + getTotalBalanceDue(tickets));
    }
}
