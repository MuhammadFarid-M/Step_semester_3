package string.class_problems;

public class BankTransactionReferenceValidator {

    private static final int REFERENCE_LENGTH = 14;
    private static final int BANK_CODE_LENGTH = 3;

    public static String normalizeReference(String raw) {
        if (raw == null) {
            return "";
        }

        String trimmed = raw.trim();
        if (trimmed.length() < BANK_CODE_LENGTH) {
            return trimmed.toUpperCase();
        }
        return trimmed.substring(0, BANK_CODE_LENGTH).toUpperCase() + trimmed.substring(BANK_CODE_LENGTH);
    }

    public static String validateAndFormat(String reference) {
        if (reference == null || reference.length() != REFERENCE_LENGTH) {
            return "Invalid: reference must be exactly 14 characters";
        }

        for (int index = 0; index < BANK_CODE_LENGTH; index++) {
            if (!Character.isLetter(reference.charAt(index))) {
                return "Invalid: bank code must be 3 letters";
            }
        }

        for (int index = BANK_CODE_LENGTH; index < REFERENCE_LENGTH; index++) {
            if (!Character.isDigit(reference.charAt(index))) {
                return "Invalid: date and sequence must be digits";
            }
        }

        StringBuilder display = new StringBuilder();
        display.append("[").append(reference.substring(0, 3)).append("] DATE: ")
                .append(reference.substring(3, 5)).append("/")
                .append(reference.substring(5, 7)).append("/")
                .append(reference.substring(7, 9))
                .append(" | SEQ: ").append(reference.substring(9));
        return display.toString();
    }

    private static void runReference(String raw) {
        System.out.println("Input: \"" + raw + "\"");
        System.out.println(validateAndFormat(normalizeReference(raw)));
        System.out.println();
    }

    public static void main(String[] args) {
        runReference("  hdf03022600042  ");
        runReference("12F03022600042");
        runReference("hdf0302260004");
        runReference("hdf030226000A2");
    }
}
