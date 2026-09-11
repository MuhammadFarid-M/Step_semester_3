package arrays_and_strings.assigment_problems;

public class TrafficSignalStreakAnalyzer {

    public static void findLongestStreak(String signalLog) {
        if (signalLog == null || signalLog.isEmpty()) {
            System.out.println("No Signal Readings Provided");
            return;
        }

        char longestColour = signalLog.charAt(0);
        int longestLength = 1;
        char currentColour = signalLog.charAt(0);
        int currentLength = 1;

        for (int index = 1; index < signalLog.length(); index++) {
            char reading = signalLog.charAt(index);
            if (reading == currentColour) {
                currentLength++;
            } else {
                currentColour = reading;
                currentLength = 1;
            }
            if (currentLength > longestLength) {
                longestLength = currentLength;
                longestColour = currentColour;
            }
        }

        String occurrenceLabel = longestLength == 1 ? " time" : " times";
        System.out.println("Longest Streak: '" + longestColour + "' repeated " + longestLength + occurrenceLabel);
    }

    private static void runAnalysis(String signalLog) {
        System.out.println("Input: \"" + signalLog + "\"");
        findLongestStreak(signalLog);
        System.out.println();
    }

    public static void main(String[] args) {
        runAnalysis("RRGGGYRR");
        runAnalysis("RRRRYYGG");
        runAnalysis("RYGRYG");
        runAnalysis("");
    }
}
