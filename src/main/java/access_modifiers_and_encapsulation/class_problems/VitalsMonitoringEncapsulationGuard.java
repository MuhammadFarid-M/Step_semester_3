package access_modifiers_and_encapsulation.class_problems;

import java.util.Arrays;

public class VitalsMonitoringEncapsulationGuard {

    static class PatientVitals {

        private static final int MAX_READINGS = 500;
        private static final double MIN_VALID_READING = 0.0;
        private static final double MAX_VALID_READING = 45.0;

        private final double[] readings;
        private int readingCount;

        PatientVitals(double[] initialReadings) {
            this.readings = new double[MAX_READINGS];
            this.readingCount = 0;
            if (initialReadings != null) {
                for (double reading : initialReadings) {
                    recordReading(reading);
                }
            }
        }

        void recordReading(double reading) {
            if (reading <= MIN_VALID_READING || reading > MAX_VALID_READING) {
                return;
            }
            if (readingCount >= MAX_READINGS) {
                return;
            }
            readings[readingCount] = reading;
            readingCount++;
        }

        double getAverage() {
            if (readingCount == 0) {
                return 0.0;
            }
            double total = 0.0;
            for (int index = 0; index < readingCount; index++) {
                total += readings[index];
            }
            return Math.round((total / readingCount) * 100) / 100.0;
        }

        double[] getAllReadings() {
            double[] copy = new double[readingCount];
            for (int index = 0; index < readingCount; index++) {
                copy[index] = readings[index];
            }
            return copy;
        }
    }

    public static void main(String[] args) {
        PatientVitals vitals = new PatientVitals(new double[] { 36.5, -2, 37.1 });
        System.out.println("getAllReadings() -> " + Arrays.toString(vitals.getAllReadings()));

        double[] copy = vitals.getAllReadings();
        copy[0] = 999;
        System.out.println("after tampering with the copy, getAllReadings()[0] -> "
                + vitals.getAllReadings()[0]);

        vitals.recordReading(46.0);
        vitals.recordReading(0.0);
        vitals.recordReading(38.2);
        System.out.println("after recording 46.0, 0.0 and 38.2 -> " + Arrays.toString(vitals.getAllReadings()));
        System.out.println("getAverage() -> " + vitals.getAverage());

        PatientVitals empty = new PatientVitals(null);
        System.out.println("empty history -> " + Arrays.toString(empty.getAllReadings())
                + " | average " + empty.getAverage());
    }
}
