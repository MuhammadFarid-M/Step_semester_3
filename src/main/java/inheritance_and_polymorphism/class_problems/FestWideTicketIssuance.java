package inheritance_and_polymorphism.class_problems;

public class FestWideTicketIssuance {

    static class EventTicket {

        private static final int TICKET_ID_BASE = 1000;
        private static final int PROMO_CODE_LENGTH = 5;
        private static int ticketsIssued = 0;

        public final String ticketId;

        private final double basePrice;
        private double amountPaid;

        public EventTicket(double basePrice) {
            ticketsIssued++;
            this.ticketId = "TCK-" + (TICKET_ID_BASE + ticketsIssued);
            this.basePrice = basePrice;
            this.amountPaid = 0.0;
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
            return basePrice - amountPaid;
        }

        static boolean isValidPromoCode(String code) {
            if (code == null || code.length() != PROMO_CODE_LENGTH) {
                return false;
            }
            if (code.charAt(0) != 'F') {
                return false;
            }
            for (int index = 1; index <= 3; index++) {
                if (!Character.isDigit(code.charAt(index))) {
                    return false;
                }
            }
            return Character.isUpperCase(code.charAt(4));
        }

        static int getTicketsIssued() {
            return ticketsIssued;
        }
    }

    static class GroupTicket extends EventTicket {

        private final int groupSize;

        public GroupTicket(double basePrice, int groupSize) {
            super(basePrice);
            if (groupSize <= 0) {
                throw new IllegalArgumentException("groupSize must be positive");
            }
            this.groupSize = groupSize;
        }

        int getGroupSize() {
            return groupSize;
        }
    }

    static String processNightlySettlement(EventTicket[] tickets) {
        int processed = 0;
        int nullSkipped = 0;
        int groupCount = 0;
        int individualCount = 0;

        if (tickets != null) {
            for (EventTicket ticket : tickets) {
                if (ticket == null) {
                    nullSkipped++;
                    continue;
                }
                if (ticket instanceof GroupTicket) {
                    groupCount++;
                } else {
                    individualCount++;
                }
                processed++;
            }
        }

        return processed + " processed | " + nullSkipped + " null skipped | "
                + groupCount + " group | " + individualCount + " individual";
    }

    public static void main(String[] args) {
        EventTicket firstTicket = new EventTicket(500);
        System.out.println("t1.ticketId -> " + firstTicket.ticketId);
        System.out.println("getTicketsIssued() -> " + EventTicket.getTicketsIssued());

        System.out.println();
        System.out.println("isValidPromoCode(\"F123A\") -> " + EventTicket.isValidPromoCode("F123A"));
        System.out.println("isValidPromoCode(\"F12A\")  -> " + EventTicket.isValidPromoCode("F12A"));
        System.out.println("isValidPromoCode(\"X123A\") -> " + EventTicket.isValidPromoCode("X123A"));

        System.out.println();
        firstTicket.pay(200);
        firstTicket.pay(200, "UPI");
        System.out.println("getBalanceDue() -> " + firstTicket.getBalanceDue());

        System.out.println();
        GroupTicket groupTicket = new GroupTicket(2000, 5);
        EventTicket individualTicket = new EventTicket(500);
        System.out.println(processNightlySettlement(new EventTicket[] {
            groupTicket, null, individualTicket
        }));
        System.out.println("group size " + groupTicket.getGroupSize()
                + " | tickets issued so far: " + EventTicket.getTicketsIssued());
    }
}
