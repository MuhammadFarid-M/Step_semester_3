package string.assigment_problems;

public class LibraryIsbnNormalizer {

    private static final int CODE_LENGTH = 13;
    private static final int PUBLISHER_CODE_LENGTH = 3;

    public static String normalizeCode(String raw) {
        if (raw == null) {
            return "";
        }

        String trimmed = raw.trim();
        if (trimmed.length() < PUBLISHER_CODE_LENGTH) {
            return trimmed.toUpperCase();
        }
        return trimmed.substring(0, PUBLISHER_CODE_LENGTH).toUpperCase()
                + trimmed.substring(PUBLISHER_CODE_LENGTH);
    }

    public static String validateAndFormat(String code) {
        if (code == null || code.length() != CODE_LENGTH) {
            return "Invalid: code must be exactly 13 characters";
        }

        for (int index = 0; index < PUBLISHER_CODE_LENGTH; index++) {
            if (!Character.isLetter(code.charAt(index))) {
                return "Invalid: publisher code must be 3 letters";
            }
        }

        for (int index = PUBLISHER_CODE_LENGTH; index < CODE_LENGTH; index++) {
            if (!Character.isDigit(code.charAt(index))) {
                return "Invalid: year and catalog number must be digits";
            }
        }

        StringBuilder display = new StringBuilder();
        display.append("[").append(code.substring(0, 3)).append("] YEAR: ")
                .append(code.substring(3, 7))
                .append(" | CATALOG: ").append(code.substring(7));
        return display.toString();
    }

    private static void runCheck(String raw) {
        System.out.println("Input: \"" + raw + "\"");
        System.out.println(validateAndFormat(normalizeCode(raw)));
        System.out.println();
    }

    public static void main(String[] args) {
        runCheck("  pen2026004251  ");
        runCheck("12N2026004251");
        runCheck("pen202600425");
        runCheck("pen202600425X");
    }
}
