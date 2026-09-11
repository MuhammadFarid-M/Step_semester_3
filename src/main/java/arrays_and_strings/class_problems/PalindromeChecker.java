package arrays_and_strings.class_problems;

/**
 * QA toolkit that verifies a text with three independent palindrome checks -
 * iterative comparison, recursion and array reversal - which must always agree.
 */
public class PalindromeChecker {

    /** Compares characters from both ends moving towards the middle. */
    public static boolean isPalindromeIterative(String text) {
        String cleaned = cleanText(text);
        int left = 0;
        int right = cleaned.length() - 1;
        while (left < right) {
            if (cleaned.charAt(left) != cleaned.charAt(right)) {
                return false;
            }
            left++;
            right--;
        }
        return true;
    }

    /** Compares the first and last characters, then recurses on the shrinking middle. */
    public static boolean isPalindromeRecursive(String text) {
        return checkRecursively(cleanText(text));
    }

    private static boolean checkRecursively(String cleaned) {
        if (cleaned.length() <= 1) {
            return true;
        }
        if (cleaned.charAt(0) != cleaned.charAt(cleaned.length() - 1)) {
            return false;
        }
        return checkRecursively(cleaned.substring(1, cleaned.length() - 1));
    }

    /** Reverses a character array copy of the text and compares it with the original. */
    public static boolean isPalindromeArrayReversal(String text) {
        String cleaned = cleanText(text);
        char[] characters = cleaned.toCharArray();
        char[] reversed = new char[characters.length];
        for (int index = 0; index < characters.length; index++) {
            reversed[index] = characters[characters.length - 1 - index];
        }
        return cleaned.equals(new String(reversed));
    }

    /** Keeps only letters and digits, in lower case, so phrases with spaces work too. */
    private static String cleanText(String text) {
        if (text == null) {
            return "";
        }
        StringBuilder cleaned = new StringBuilder();
        for (int index = 0; index < text.length(); index++) {
            char current = text.charAt(index);
            if (Character.isLetterOrDigit(current)) {
                cleaned.append(Character.toLowerCase(current));
            }
        }
        return cleaned.toString();
    }

    private static String verdict(boolean isPalindrome) {
        return isPalindrome ? "Palindrome" : "Not Palindrome";
    }

    private static void reportAllThreeApproaches(String text) {
        System.out.println("Input: \"" + text + "\"");
        System.out.println("Iterative: " + verdict(isPalindromeIterative(text))
                + " | Recursive: " + verdict(isPalindromeRecursive(text))
                + " | Array Reversal: " + verdict(isPalindromeArrayReversal(text)));
        System.out.println();
    }

    public static void main(String[] args) {
        reportAllThreeApproaches("madam");
        reportAllThreeApproaches("hello");
        reportAllThreeApproaches("Never odd or even");
        reportAllThreeApproaches("");
    }
}
