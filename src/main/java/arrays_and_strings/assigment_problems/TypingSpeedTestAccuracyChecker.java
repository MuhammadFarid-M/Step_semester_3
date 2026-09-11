package arrays_and_strings.assigment_problems;

/**
 * Typing-practice website checker: compares a typed attempt against the original
 * passage position by position and reports accuracy and the first mistake.
 */
public class TypingSpeedTestAccuracyChecker {

    public static void checkTypingAccuracy(String original, String typed) {
        if (original == null || typed == null) {
            System.out.println("Typing check unavailable: both the original passage and the typed text are required.");
            return;
        }
        if (original.isEmpty()) {
            System.out.println("Typing check unavailable: the original passage is empty.");
            return;
        }
        if (original.length() != typed.length()) {
            System.out.println("Typing check unavailable: the typed text must be the same length as the original passage.");
            return;
        }

        int totalCharacters = original.length();
        int matchedCharacters = 0;
        int firstMismatchIndex = -1;

        for (int index = 0; index < totalCharacters; index++) {
            if (original.charAt(index) == typed.charAt(index)) {
                matchedCharacters++;
            } else if (firstMismatchIndex == -1) {
                firstMismatchIndex = index;
            }
        }

        double accuracy = (matchedCharacters * 100.0) / totalCharacters;
        String summary = String.format("Matched: %d/%d | Accuracy: %.2f%%",
                matchedCharacters, totalCharacters, accuracy);

        if (firstMismatchIndex == -1) {
            System.out.println(summary + " | No Mismatches");
        } else {
            System.out.printf("%s | First Mismatch at position %d ('%c' vs '%c')%n",
                    summary, firstMismatchIndex + 1,
                    original.charAt(firstMismatchIndex), typed.charAt(firstMismatchIndex));
        }
    }

    private static void runCheck(String original, String typed) {
        System.out.println("Input: original=\"" + original + "\", typed=\"" + typed + "\"");
        checkTypingAccuracy(original, typed);
        System.out.println();
    }

    public static void main(String[] args) {
        runCheck("hello world", "hello worlt");
        runCheck("coding", "coding");
        runCheck("java programming", "jawa progremming");
    }
}
