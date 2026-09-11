package constructors_and_keywords.class_problems;

public class BusTicketBookingValidator {

    static class BusTicket {

        private final String passengerName;
        private final String destination;
        private boolean checkedIn;

        public BusTicket(String passengerName, String destination) {
            if (!isMeaningfulText(passengerName)) {
                throw new IllegalArgumentException("Invalid passenger name");
            }
            if (!isMeaningfulText(destination)) {
                throw new IllegalArgumentException("Invalid destination");
            }
            this.passengerName = passengerName.trim();
            this.destination = destination.trim();
            this.checkedIn = false;
        }

        private static boolean isMeaningfulText(String value) {
            if (value == null) {
                return false;
            }
            String trimmed = value.trim();
            if (trimmed.isEmpty()) {
                return false;
            }
            for (int index = 0; index < trimmed.length(); index++) {
                char current = trimmed.charAt(index);
                if (!Character.isLetter(current) && current != ' ') {
                    return false;
                }
            }
            return true;
        }

        String bookingKey() {
            return passengerName.toLowerCase() + "|" + destination.toLowerCase();
        }

        void markCheckedIn() {
            if (checkedIn) {
                System.out.println(passengerName + " -> " + destination + ": already checked in, ignoring repeat");
                return;
            }
            checkedIn = true;
            System.out.println(passengerName + " -> " + destination + ": checked in");
        }
    }

    static void processBatch(String[][] rawBookings) {
        if (rawBookings == null || rawBookings.length == 0) {
            System.out.println("Valid: 0 | Rejected: 0 | Duplicates skipped: 0");
            return;
        }

        String[] acceptedKeys = new String[rawBookings.length];
        int acceptedCount = 0;
        int rejectedCount = 0;
        int duplicateCount = 0;

        for (String[] rawBooking : rawBookings) {
            if (rawBooking == null || rawBooking.length != 2) {
                rejectedCount++;
                continue;
            }
            BusTicket ticket;
            try {
                ticket = new BusTicket(rawBooking[0], rawBooking[1]);
            } catch (IllegalArgumentException rejected) {
                rejectedCount++;
                continue;
            }
            String key = ticket.bookingKey();
            boolean alreadyBooked = false;
            for (int index = 0; index < acceptedCount; index++) {
                if (acceptedKeys[index].equals(key)) {
                    alreadyBooked = true;
                    break;
                }
            }
            if (alreadyBooked) {
                duplicateCount++;
                continue;
            }
            acceptedKeys[acceptedCount] = key;
            acceptedCount++;
        }

        System.out.println("Valid: " + acceptedCount
                + " | Rejected: " + rejectedCount
                + " | Duplicates skipped: " + duplicateCount);
    }

    public static void main(String[] args) {
        String[][] rawBookings = {
            { "Divya", "Chennai" },
            { "", "Bangalore" },
            { "Ravi123", "Pune" },
            { "Divya", "Chennai" },
            { "   ", "  " }
        };
        processBatch(rawBookings);

        System.out.println();
        BusTicket ticket = new BusTicket("Divya", "Chennai");
        ticket.markCheckedIn();
        ticket.markCheckedIn();

        System.out.println();
        try {
            new BusTicket(null, "Chennai");
        } catch (IllegalArgumentException rejected) {
            System.out.println("Construction blocked: " + rejected.getMessage());
        }
    }
}
