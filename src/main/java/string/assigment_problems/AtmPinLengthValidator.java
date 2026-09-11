package string.assigment_problems;

public class AtmPinLengthValidator {

    private static final int REQUIRED_PIN_LENGTH = 4;

    public static void checkPinLength(String pin) {
        if (pin == null || pin.length() != REQUIRED_PIN_LENGTH) {
            System.out.println("Invalid PIN — must be exactly 4 digits.");
        } else {
            System.out.println("PIN length OK.");
        }
    }

    private static void runCheck(String pin) {
        System.out.println("Input: \"" + pin + "\"");
        checkPinLength(pin);
        System.out.println();
    }

    public static void main(String[] args) {
        runCheck("482");
        runCheck("4820");
        runCheck("48201");
    }
}
