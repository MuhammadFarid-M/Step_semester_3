package string.assigment_problems;

import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

public class StopWordFilteredWordFrequencyReport {

    private static final String[] STOP_WORDS = { "the", "was", "and", "a", "is", "of", "in" };

    public static void printFilteredWordFrequency(String feedback) {
        if (feedback == null || feedback.trim().isEmpty()) {
            System.out.println("No words to report");
            return;
        }

        String cleaned = feedback.toLowerCase()
                .replace(".", "")
                .replace(",", "")
                .replace("!", "")
                .replace("?", "")
                .replace(";", "")
                .replace(":", "");

        String[] words = cleaned.trim().split("\\s+");

        Map<String, Integer> wordCounts = new LinkedHashMap<>();
        for (String word : words) {
            if (word.isEmpty() || isStopWord(word)) {
                continue;
            }
            wordCounts.put(word, wordCounts.getOrDefault(word, 0) + 1);
        }

        if (wordCounts.isEmpty()) {
            System.out.println("No words to report");
            return;
        }

        List<Map.Entry<String, Integer>> rankedWords = new ArrayList<>(wordCounts.entrySet());
        rankedWords.sort((left, right) -> right.getValue() - left.getValue());

        for (Map.Entry<String, Integer> entry : rankedWords) {
            System.out.println(entry.getKey() + ": " + entry.getValue());
        }
    }

    private static boolean isStopWord(String word) {
        for (String stopWord : STOP_WORDS) {
            if (stopWord.equals(word)) {
                return true;
            }
        }
        return false;
    }

    private static void runReport(String feedback) {
        System.out.println("Input: \"" + feedback + "\"");
        printFilteredWordFrequency(feedback);
        System.out.println();
    }

    public static void main(String[] args) {
        runReport("The mentor was great, the session was great and clear.");
        runReport("A session of doubts in the lab was useful and the lab was fun.");
        runReport("");
    }
}
