package arrays_and_strings.assigment_problems;

public class ExamHallSeatDuplicationChecker {

    public static void checkDuplicateSeats(int[] seatNumbers) {
        if (seatNumbers == null || seatNumbers.length == 0) {
            System.out.println("No Seat Numbers Provided");
            return;
        }

        boolean duplicateFound = false;
        for (int current = 0; current < seatNumbers.length; current++) {

            if (appearsEarlier(seatNumbers, current)) {
                continue;
            }
            for (int later = current + 1; later < seatNumbers.length; later++) {
                if (seatNumbers[current] == seatNumbers[later]) {
                    System.out.println("Duplicate Seat Number Found: " + seatNumbers[current]);
                    duplicateFound = true;
                    break;
                }
            }
        }

        if (!duplicateFound) {
            System.out.println("No Duplicate Seats Found");
        }
    }

    private static boolean appearsEarlier(int[] seatNumbers, int index) {
        for (int earlier = 0; earlier < index; earlier++) {
            if (seatNumbers[earlier] == seatNumbers[index]) {
                return true;
            }
        }
        return false;
    }

    private static void runCheck(int[] seatNumbers) {
        System.out.print("Input: {");
        for (int index = 0; index < seatNumbers.length; index++) {
            System.out.print(index == 0 ? "" : ", ");
            System.out.print(seatNumbers[index]);
        }
        System.out.println("}");
        checkDuplicateSeats(seatNumbers);
        System.out.println();
    }

    public static void main(String[] args) {
        runCheck(new int[] { 101, 102, 103, 102, 105 });
        runCheck(new int[] { 101, 102, 103, 104, 105 });
        runCheck(new int[] { 101, 102, 102, 103, 101, 101 });
    }
}
