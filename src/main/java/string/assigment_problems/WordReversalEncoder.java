package string.assigment_problems;

public class WordReversalEncoder {

    public static String reverseEachWord(String sentence) {
        if (sentence == null || sentence.isEmpty()) {
            return "";
        }

        String[] words = sentence.split(" ");
        StringBuilder encoded = new StringBuilder();

        for (int wordIndex = 0; wordIndex < words.length; wordIndex++) {
            if (wordIndex > 0) {
                encoded.append(" ");
            }
            String word = words[wordIndex];
            StringBuilder reversedWord = new StringBuilder();
            for (int position = word.length() - 1; position >= 0; position--) {
                reversedWord.append(word.charAt(position));
            }
            encoded.append(reversedWord);
        }

        return encoded.toString();
    }

    private static void runEncode(String sentence) {
        System.out.println("Input: \"" + sentence + "\"");
        System.out.println(reverseEachWord(sentence));
        System.out.println();
    }

    public static void main(String[] args) {
        runEncode("hello club");
        runEncode("mirror text mini game");
        runEncode("");
    }
}
