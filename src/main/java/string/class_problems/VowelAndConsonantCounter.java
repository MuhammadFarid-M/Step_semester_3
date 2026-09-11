package string.class_problems;

public class VowelAndConsonantCounter {

    public static void countVowelsAndConsonants(String text) {
        int vowels = 0;
        int consonants = 0;

        if (text != null) {
            for (int index = 0; index < text.length(); index++) {
                char current = Character.toLowerCase(text.charAt(index));
                if (current < 'a' || current > 'z') {
                    continue;
                }
                if (current == 'a' || current == 'e' || current == 'i' || current == 'o' || current == 'u') {
                    vowels++;
                } else {
                    consonants++;
                }
            }
        }

        System.out.println("Vowels: " + vowels + " | Consonants: " + consonants);
    }

    private static void runCount(String text) {
        System.out.println("Input: \"" + text + "\"");
        countVowelsAndConsonants(text);
        System.out.println();
    }

    public static void main(String[] args) {
        runCount("Java Programming");
        runCount("Rhythm");
        runCount("");
    }
}
