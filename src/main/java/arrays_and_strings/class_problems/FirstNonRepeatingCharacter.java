package arrays_and_strings.class_problems;

/**
 * Unique Letter Hunt mini-game: finds the first character in the input that
 * appears exactly once in the whole text.
 */
public class FirstNonRepeatingCharacter {

    /** Returned when every character in the text repeats. */
    private static final char NONE_FOUND = '\0';

    public static char findFirstNonRepeatingChar(String text) {
        if (text == null || text.isEmpty()) {
            return NONE_FOUND;
        }

        int[] frequency = new int[Character.MAX_VALUE + 1];
        for (int index = 0; index < text.length(); index++) {
            frequency[text.charAt(index)]++;
        }

        for (int index = 0; index < text.length(); index++) {
            if (frequency[text.charAt(index)] == 1) {
                return text.charAt(index);
            }
        }
        return NONE_FOUND;
    }

    private static void reportFirstNonRepeatingChar(String text) {
        System.out.println("Input: \"" + text + "\"");
        char firstUnique = findFirstNonRepeatingChar(text);
        if (firstUnique == NONE_FOUND) {
            System.out.println("No Non-Repeating Character Found");
        } else {
            System.out.println("First Non-Repeating Character: '" + firstUnique + "'");
        }
        System.out.println();
    }

    public static void main(String[] args) {
        reportFirstNonRepeatingChar("swiss");
        reportFirstNonRepeatingChar("aabbcc");
        reportFirstNonRepeatingChar("teeter");
        reportFirstNonRepeatingChar("");
    }
}
