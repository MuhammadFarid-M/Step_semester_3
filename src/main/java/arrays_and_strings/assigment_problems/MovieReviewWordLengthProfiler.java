package arrays_and_strings.assigment_problems;

public class MovieReviewWordLengthProfiler {

    public static void classifyWordLengths(String review) {
        int shortWords = 0;
        int mediumWords = 0;
        int longWords = 0;

        if (review != null && !review.trim().isEmpty()) {
            String[] words = review.trim().split("\\s+");
            for (String word : words) {
                int letterCount = countLetters(word);
                if (letterCount == 0) {
                    continue;
                }
                if (letterCount <= 4) {
                    shortWords++;
                } else if (letterCount <= 8) {
                    mediumWords++;
                } else {
                    longWords++;
                }
            }
        }

        System.out.println("Short: " + shortWords + " | Medium: " + mediumWords + " | Long: " + longWords);
    }

    private static int countLetters(String word) {
        int letterCount = 0;
        for (int index = 0; index < word.length(); index++) {
            if (Character.isLetter(word.charAt(index))) {
                letterCount++;
            }
        }
        return letterCount;
    }

    private static void runProfile(String review) {
        System.out.println("Input: \"" + review + "\"");
        classifyWordLengths(review);
        System.out.println();
    }

    public static void main(String[] args) {
        runProfile("This movie was absolutely fantastic and thrilling");
        runProfile("A dull, slow and forgettable experience overall.");
        runProfile("");
    }
}
