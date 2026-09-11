package arrays_and_strings.class_problems;

import java.util.Random;

public class BmiCalculatorForTeam {

    private static final int TEAM_SIZE = 10;
    private static final Random RANDOM = new Random();

    public static double calculateBmi(double heightInMetres, double weightInKg) {
        if (heightInMetres <= 0) {
            return 0.0;
        }
        return weightInKg / (heightInMetres * heightInMetres);
    }

    public static String getBmiStatus(double bmi) {
        if (bmi < 18.5) {
            return "Underweight";
        }
        if (bmi < 25.0) {
            return "Normal";
        }
        if (bmi < 30.0) {
            return "Overweight";
        }
        return "Obese";
    }

    public static void printWellnessReport(double[] heights, double[] weights) {
        if (heights == null || weights == null || heights.length == 0 || heights.length != weights.length) {
            System.out.println("Wellness report unavailable: a height and a weight are needed for every person.");
            return;
        }

        System.out.printf("%-7s | %-10s | %-11s | %-6s | %s%n",
                "Person", "Height (m)", "Weight (kg)", "BMI", "Status");
        System.out.println("-------------------------------------------------------------");
        for (int index = 0; index < heights.length; index++) {
            double bmi = calculateBmi(heights[index], weights[index]);
            System.out.printf("%-7d | %-10.2f | %-11.2f | %-6.2f | %s%n",
                    index + 1, heights[index], weights[index], bmi, getBmiStatus(bmi));
        }
    }

    private static void printSampleCheck(int personNumber, double heightInMetres, double weightInKg) {
        double bmi = calculateBmi(heightInMetres, weightInKg);
        System.out.printf("Person %d - Height: %.2f m, Weight: %.0f kg%n", personNumber, heightInMetres, weightInKg);
        System.out.printf("BMI: %.2f | Status: %s%n", bmi, getBmiStatus(bmi));
    }

    public static void main(String[] args) {
        double[] heights = { 1.75, 1.60, 1.82, 1.68, 1.55, 1.90, 1.72, 1.65, 1.78, 1.58 };
        double[] weights = { 70.0, 90.0, 95.0, 52.0, 45.0, 88.0, 68.0, 74.0, 61.0, 40.0 };

        System.out.println("=== Wellness Report (recorded department data) ===");
        printWellnessReport(heights, weights);

        System.out.println();
        System.out.println("=== Sample Check (matches the sample in the problem statement) ===");
        printSampleCheck(1, heights[0], weights[0]);
        printSampleCheck(2, heights[1], weights[1]);

        System.out.println();
        System.out.println("=== Wellness Report (randomly generated data for a live demo) ===");
        double[] randomHeights = new double[TEAM_SIZE];
        double[] randomWeights = new double[TEAM_SIZE];
        for (int index = 0; index < TEAM_SIZE; index++) {
            randomHeights[index] = 1.50 + (RANDOM.nextDouble() * 0.45);
            randomWeights[index] = 45.0 + (RANDOM.nextDouble() * 65.0);
        }
        printWellnessReport(randomHeights, randomWeights);
    }
}
