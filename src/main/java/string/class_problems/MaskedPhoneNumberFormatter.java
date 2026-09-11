package string.class_problems;

public class MaskedPhoneNumberFormatter {

    private static final int PHONE_LENGTH = 10;
    private static final int VISIBLE_DIGITS = 4;
    private static final String INVALID_MESSAGE = "Invalid phone number";

    public static String maskPhoneNumber(String phone) {
        if (phone == null || phone.length() != PHONE_LENGTH) {
            return INVALID_MESSAGE;
        }

        for (int index = 0; index < phone.length(); index++) {
            if (!Character.isDigit(phone.charAt(index))) {
                return INVALID_MESSAGE;
            }
        }

        int maskLength = PHONE_LENGTH - VISIBLE_DIGITS;
        StringBuilder masked = new StringBuilder();
        for (int index = 0; index < maskLength; index++) {
            masked.append('X');
        }
        masked.append(phone.substring(maskLength));
        masked.insert(maskLength, "-");
        return masked.toString();
    }

    private static void runMask(String phone) {
        System.out.println("Input: \"" + phone + "\"");
        System.out.println(maskPhoneNumber(phone));
        System.out.println();
    }

    public static void main(String[] args) {
        runMask("9876543210");
        runMask("98765");
        runMask("98765abcde");
    }
}
