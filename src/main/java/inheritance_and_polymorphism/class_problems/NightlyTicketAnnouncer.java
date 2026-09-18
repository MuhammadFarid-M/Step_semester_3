package inheritance_and_polymorphism.class_problems;

public class NightlyTicketAnnouncer {

    static class EventTicket {

        private final double basePrice;
        private double amountPaid;

        public EventTicket(double basePrice) {
            this.basePrice = basePrice;
            this.amountPaid = 0.0;
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
            return "Standard | Balance: " + getBalanceDue();
        }
    }

    static class WorkshopTicket extends EventTicket {

        private final String track;

        public WorkshopTicket(double basePrice, String track) {
            super(basePrice);
            this.track = track;
        }

        String getTrack() {
            return track;
        }

        @Override
        String printTicket() {
            return "Workshop | Track: " + track + " | Balance: " + getBalanceDue();
        }
    }

    static String batchPrint(EventTicket[] tickets) {
        StringBuilder report = new StringBuilder();
        if (tickets == null) {
            return report.toString();
        }
        for (EventTicket ticket : tickets) {
            report.append(ticket.printTicket());
            if (ticket instanceof WorkshopTicket) {
                WorkshopTicket workshop = (WorkshopTicket) ticket;
                report.append(" [Track via downcast: ").append(workshop.getTrack()).append("]");
            }
            report.append(" | ");
        }
        return report.toString();
    }

    public static void main(String[] args) {
        EventTicket[] tickets = {
            new EventTicket(500),
            new WorkshopTicket(1200, "AI/ML")
        };
        System.out.println(batchPrint(tickets));

        System.out.println();
        EventTicket plain = new EventTicket(500);
        try {
            WorkshopTicket bad = (WorkshopTicket) plain;
            System.out.println("downcast succeeded, track = " + bad.getTrack());
        } catch (ClassCastException expected) {
            System.out.println("(WorkshopTicket) plain -> ClassCastException at runtime");
        }

        System.out.println("guarded instead: plain instanceof WorkshopTicket -> "
                + (plain instanceof WorkshopTicket));
    }
}
