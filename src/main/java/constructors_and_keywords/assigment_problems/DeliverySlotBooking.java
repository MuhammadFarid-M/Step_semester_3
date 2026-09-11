package constructors_and_keywords.assigment_problems;

public class DeliverySlotBooking {

    static class DeliverySlot {

        private static final String DEFAULT_SLOT = "ASAP";
        private static final String[] PEAK_SLOTS = {
            "12:00-13:00", "13:00-14:00", "19:00-20:00", "20:00-21:00"
        };

        private final String orderId;
        private final String timeSlot;

        public DeliverySlot(String orderId, String timeSlot) {
            if (orderId == null || orderId.trim().isEmpty()) {
                throw new IllegalArgumentException("Order id is required");
            }
            this.orderId = orderId.trim();
            this.timeSlot = (timeSlot == null || timeSlot.trim().isEmpty())
                    ? DEFAULT_SLOT
                    : timeSlot.trim();
        }

        public DeliverySlot(String orderId) {
            this(orderId, DEFAULT_SLOT);
        }

        String getOrderId() {
            return orderId;
        }

        String getTimeSlot() {
            return timeSlot;
        }

        boolean isPeakHour() {
            for (String peakSlot : PEAK_SLOTS) {
                if (peakSlot.equals(timeSlot)) {
                    return true;
                }
            }
            return false;
        }
    }

    private static void printSlot(DeliverySlot slot) {
        System.out.println(slot.getOrderId() + " (" + slot.getTimeSlot() + ") -> " + slot.isPeakHour());
    }

    public static void main(String[] args) {
        printSlot(new DeliverySlot("ORD101", "13:00-14:00"));
        printSlot(new DeliverySlot("ORD102"));
        printSlot(new DeliverySlot("ORD103", "20:00-21:00"));
        printSlot(new DeliverySlot("ORD104", "16:00-17:00"));
    }
}
