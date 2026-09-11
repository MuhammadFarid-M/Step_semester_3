package constructors_and_keywords.class_problems;

import java.util.Arrays;

public class RemainderFairFareSplitter {

    static class FareSplitter {

        private static final int DEFAULT_PASSENGER_COUNT = 2;
        private static final double DEFAULT_FARE = 0.0;

        private final String tripId;
        private final double totalFare;
        private final int passengerCount;

        public FareSplitter(String tripId, double totalFare, int passengerCount) {
            if (tripId == null || tripId.trim().isEmpty()) {
                throw new IllegalArgumentException("Trip id is required");
            }
            if (totalFare < 0) {
                throw new IllegalArgumentException("Total fare cannot be negative");
            }
            if (passengerCount <= 0) {
                throw new IllegalArgumentException("Passenger count must be positive");
            }
            this.tripId = tripId.trim();
            this.totalFare = totalFare;
            this.passengerCount = passengerCount;
        }

        public FareSplitter(String tripId, double totalFare) {
            this(tripId, totalFare, DEFAULT_PASSENGER_COUNT);
        }

        public FareSplitter(String tripId) {
            this(tripId, DEFAULT_FARE);
        }

        String getTripId() {
            return tripId;
        }

        double[] fareBreakdown() {
            long totalPaise = Math.round(totalFare * 100);
            long basePaise = totalPaise / passengerCount;
            long leftoverPaise = totalPaise - (basePaise * passengerCount);

            double[] shares = new double[passengerCount];
            for (int index = 0; index < passengerCount; index++) {
                long sharePaise = basePaise;
                if (index >= passengerCount - leftoverPaise) {
                    sharePaise++;
                }
                shares[index] = sharePaise / 100.0;
            }
            return shares;
        }

        boolean isConfirmationOverdue(int confirmed, int expected) {
            return confirmed < expected;
        }
    }

    private static void printBreakdown(FareSplitter splitter) {
        double[] shares = splitter.fareBreakdown();
        double sum = 0.0;
        for (double share : shares) {
            sum += share;
        }
        System.out.println(splitter.getTripId() + " -> " + Arrays.toString(shares)
                + " (sums to " + Math.round(sum * 100) / 100.0 + ")");
    }

    public static void main(String[] args) {
        printBreakdown(new FareSplitter("TRIP001", 100000, 3));
        printBreakdown(new FareSplitter("TRIP002", 1000.05, 7));
        printBreakdown(new FareSplitter("TRIP003"));

        System.out.println();
        FareSplitter trip = new FareSplitter("TRIP001", 100000, 3);
        System.out.println("Confirmation overdue (2 of 3): " + trip.isConfirmationOverdue(2, 3));
        System.out.println("Confirmation overdue (3 of 3): " + trip.isConfirmationOverdue(3, 3));

        System.out.println();
        try {
            new FareSplitter("TRIP004", -100, 2);
        } catch (IllegalArgumentException rejected) {
            System.out.println("Construction blocked: " + rejected.getMessage());
        }
        try {
            new FareSplitter("TRIP005", 500, 0);
        } catch (IllegalArgumentException rejected) {
            System.out.println("Construction blocked: " + rejected.getMessage());
        }
    }
}
