package constructors_and_keywords.assigment_problems;

public class ExamWeekSurgeFeeCalculator {

    static final class SurgeFeeCalculator {

        private static final int FIRST_BRACKET_END = 5;
        private static final int SECOND_BRACKET_END = 15;
        private static final double FIRST_BRACKET_RATE = 0.005;
        private static final double SECOND_BRACKET_RATE = 0.01;
        private static final double THIRD_BRACKET_RATE = 0.02;

        private final double minimumSurgePercent;

        public SurgeFeeCalculator(double minimumSurgePercent) {
            if (minimumSurgePercent < 0) {
                throw new IllegalArgumentException("Minimum surge percent cannot be negative");
            }
            this.minimumSurgePercent = minimumSurgePercent;
        }

        final double calculateSurgeFee(double orderValue, int delayMinutes) {
            if (orderValue < 0) {
                throw new IllegalArgumentException("Order value cannot be negative");
            }
            if (delayMinutes < 0) {
                throw new IllegalArgumentException("Delay minutes cannot be negative");
            }
            if (delayMinutes == 0) {
                return 0.0;
            }

            int firstBracketMinutes = Math.min(delayMinutes, FIRST_BRACKET_END);
            int secondBracketMinutes = Math.max(0, Math.min(delayMinutes, SECOND_BRACKET_END) - FIRST_BRACKET_END);
            int thirdBracketMinutes = Math.max(0, delayMinutes - SECOND_BRACKET_END);

            double tieredFee = orderValue * (firstBracketMinutes * FIRST_BRACKET_RATE
                    + secondBracketMinutes * SECOND_BRACKET_RATE
                    + thirdBracketMinutes * THIRD_BRACKET_RATE);

            double floorFee = orderValue * minimumSurgePercent / 100;
            double payable = Math.max(tieredFee, floorFee);
            return Math.round(payable * 100) / 100.0;
        }
    }

    private static void printFee(SurgeFeeCalculator calculator, double orderValue, int delayMinutes) {
        System.out.println("orderValue = " + (long) orderValue + ", delayMinutes = " + delayMinutes
                + " -> Rs " + calculator.calculateSurgeFee(orderValue, delayMinutes));
    }

    public static void main(String[] args) {
        SurgeFeeCalculator calculator = new SurgeFeeCalculator(1.0);
        printFee(calculator, 500, 0);
        printFee(calculator, 500, 1);
        printFee(calculator, 500, 5);
        printFee(calculator, 500, 6);
        printFee(calculator, 500, 15);
        printFee(calculator, 500, 16);

        System.out.println();
        try {
            calculator.calculateSurgeFee(-50, 5);
        } catch (IllegalArgumentException rejected) {
            System.out.println("Calculation blocked: " + rejected.getMessage());
        }
        try {
            calculator.calculateSurgeFee(500, -3);
        } catch (IllegalArgumentException rejected) {
            System.out.println("Calculation blocked: " + rejected.getMessage());
        }
    }
}
