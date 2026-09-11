package constructors_and_keywords.class_problems;

public class TieredBoardingPenaltyCalculator {

    static final class BoardingPenaltyCalculator {

        private static final int FIRST_BRACKET_END = 5;
        private static final int SECOND_BRACKET_END = 15;
        private static final double FIRST_BRACKET_RATE = 0.005;
        private static final double SECOND_BRACKET_RATE = 0.01;
        private static final double THIRD_BRACKET_RATE = 0.02;

        private final double minimumPenaltyPercent;

        public BoardingPenaltyCalculator(double minimumPenaltyPercent) {
            if (minimumPenaltyPercent < 0) {
                throw new IllegalArgumentException("Minimum penalty percent cannot be negative");
            }
            this.minimumPenaltyPercent = minimumPenaltyPercent;
        }

        final double calculatePenalty(double ticketFare, int minutesLate) {
            if (ticketFare < 0) {
                throw new IllegalArgumentException("Ticket fare cannot be negative");
            }
            if (minutesLate < 0) {
                throw new IllegalArgumentException("Minutes late cannot be negative");
            }
            if (minutesLate == 0) {
                return 0.0;
            }

            int firstBracketMinutes = Math.min(minutesLate, FIRST_BRACKET_END);
            int secondBracketMinutes = Math.max(0, Math.min(minutesLate, SECOND_BRACKET_END) - FIRST_BRACKET_END);
            int thirdBracketMinutes = Math.max(0, minutesLate - SECOND_BRACKET_END);

            double tieredPenalty = ticketFare * (firstBracketMinutes * FIRST_BRACKET_RATE
                    + secondBracketMinutes * SECOND_BRACKET_RATE
                    + thirdBracketMinutes * THIRD_BRACKET_RATE);

            double floorPenalty = ticketFare * minimumPenaltyPercent / 100;
            double payable = Math.max(tieredPenalty, floorPenalty);
            return Math.round(payable * 100) / 100.0;
        }
    }

    private static void printPenalty(BoardingPenaltyCalculator calculator, double ticketFare, int minutesLate) {
        System.out.println("ticketFare = " + (long) ticketFare + ", minutesLate = " + minutesLate
                + " -> Rs " + calculator.calculatePenalty(ticketFare, minutesLate));
    }

    public static void main(String[] args) {
        BoardingPenaltyCalculator calculator = new BoardingPenaltyCalculator(1.0);
        printPenalty(calculator, 1000, 0);
        printPenalty(calculator, 1000, 1);
        printPenalty(calculator, 1000, 5);
        printPenalty(calculator, 1000, 6);
        printPenalty(calculator, 1000, 15);
        printPenalty(calculator, 1000, 16);

        System.out.println();
        try {
            calculator.calculatePenalty(-100, 5);
        } catch (IllegalArgumentException rejected) {
            System.out.println("Calculation blocked: " + rejected.getMessage());
        }
        try {
            calculator.calculatePenalty(1000, -2);
        } catch (IllegalArgumentException rejected) {
            System.out.println("Calculation blocked: " + rejected.getMessage());
        }
    }
}
