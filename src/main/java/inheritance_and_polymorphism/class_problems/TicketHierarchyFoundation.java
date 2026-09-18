package inheritance_and_polymorphism.class_problems;

public class TicketHierarchyFoundation {

    static class EventTicket {

        private static final int MINIMUM_ID_LENGTH = 4;

        private final String attendeeId;
        private final double basePrice;
        private double amountPaid;

        public EventTicket(String attendeeId, double basePrice) {
            if (attendeeId == null || attendeeId.trim().length() < MINIMUM_ID_LENGTH) {
                throw new IllegalArgumentException("attendeeId must be at least "
                        + MINIMUM_ID_LENGTH + " non-blank characters");
            }
            this.attendeeId = attendeeId.trim();
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
    }

    static String registerBatch(String[] attendeeIds, double basePrice) {
        int registered = 0;
        int rejected = 0;

        if (attendeeIds != null) {
            for (String attendeeId : attendeeIds) {
                try {
                    new EventTicket(attendeeId, basePrice);
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
            new EventTicket("ST1", 500);
            System.out.println("new EventTicket(\"ST1\", 500) -> accepted");
        } catch (IllegalArgumentException rejected) {
            System.out.println("new EventTicket(\"ST1\", 500) -> construction rejected");
        }

        WorkshopTicket workshop = new WorkshopTicket("STU2", 1200, "AI/ML");
        workshop.pay(500);
        System.out.println(workshop.getAttendeeId() + " (" + workshop.getTrack()
                + ") after pay(500) -> getBalanceDue() = " + workshop.getBalanceDue());

        System.out.println();
        System.out.println(registerBatch(new String[] { "STU1", "ST1", "STU2", "   ", "STU3" }, 500));
    }
}
