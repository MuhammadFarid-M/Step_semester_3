package access_modifiers_and_encapsulation.class_problems;

import java.util.Arrays;

public class ImmutableDischargeSummaryLedger {

    static class DischargeSummary {

        private final String patientId;
        private final String[] medicationCodes;

        public DischargeSummary(String patientId, String[] medicationCodes) {
            if (patientId == null || patientId.trim().isEmpty()) {
                throw new IllegalArgumentException("patientId is required");
            }
            if (medicationCodes == null) {
                throw new IllegalArgumentException("medicationCodes is required");
            }
            for (String code : medicationCodes) {
                if (!isValidMedicationCode(code)) {
                    throw new IllegalArgumentException("Invalid medication code: " + code);
                }
            }
            this.patientId = patientId.trim();
            this.medicationCodes = new String[medicationCodes.length];
            for (int index = 0; index < medicationCodes.length; index++) {
                this.medicationCodes[index] = medicationCodes[index];
            }
        }

        static boolean isValidMedicationCode(String code) {
            return code != null && code.length() == 5 && code.startsWith("MED-")
                    && Character.isUpperCase(code.charAt(4));
        }

        final String getPatientId() {
            return patientId;
        }

        final String[] getMedicationCodes() {
            String[] copy = new String[medicationCodes.length];
            for (int index = 0; index < medicationCodes.length; index++) {
                copy[index] = medicationCodes[index];
            }
            return copy;
        }

        final DischargeSummary withCorrectedMedication(int index, String newCode) {
            if (index < 0 || index >= medicationCodes.length) {
                throw new IllegalArgumentException("No medication at index " + index);
            }
            if (!isValidMedicationCode(newCode)) {
                throw new IllegalArgumentException("Invalid medication code: " + newCode);
            }
            String[] corrected = getMedicationCodes();
            corrected[index] = newCode;
            return new DischargeSummary(patientId, corrected);
        }
    }

    static class CriticalCareDischargeSummary extends DischargeSummary {

        private final int icuDays;

        public CriticalCareDischargeSummary(String patientId, String[] medicationCodes, int icuDays) {
            super(patientId, medicationCodes);
            if (icuDays < 0) {
                throw new IllegalArgumentException("icuDays cannot be negative");
            }
            this.icuDays = icuDays;
        }

        final int getIcuDays() {
            return icuDays;
        }
    }

    static final String LEDGER_ID;
    private static int ledgerRunCount;

    static {
        LEDGER_ID = "MEDITRACK-NIGHTLY";
        ledgerRunCount = 0;
    }

    static String processNightlyBatch(DischargeSummary[] summaries) {
        ledgerRunCount++;
        int processed = 0;
        int nullSkipped = 0;
        int criticalCare = 0;
        int routine = 0;

        if (summaries != null) {
            for (DischargeSummary summary : summaries) {
                if (summary == null) {
                    nullSkipped++;
                    continue;
                }
                if (summary instanceof CriticalCareDischargeSummary) {
                    CriticalCareDischargeSummary critical = (CriticalCareDischargeSummary) summary;
                    System.out.println("  settled " + critical.getPatientId()
                            + " as critical care (" + critical.getIcuDays() + " ICU days)");
                    criticalCare++;
                } else {
                    System.out.println("  settled " + summary.getPatientId() + " as routine");
                    routine++;
                }
                processed++;
            }
        }

        return processed + " processed | " + nullSkipped + " null skipped | "
                + criticalCare + " critical-care | " + routine + " routine";
    }

    public static void main(String[] args) {
        try {
            new DischargeSummary("MT2026-0142", new String[] { "MED-A", "bad" });
            System.out.println("accepted");
        } catch (IllegalArgumentException rejected) {
            System.out.println("MED-A + bad -> construction rejected");
        }

        DischargeSummary summary = new DischargeSummary("MT2026-0142", new String[] { "MED-A", "MED-B" });
        String[] codes = summary.getMedicationCodes();
        codes[0] = "TAMPERED";
        System.out.println("after tampering with the returned array, getMedicationCodes()[0] -> "
                + summary.getMedicationCodes()[0]);

        DischargeSummary corrected = summary.withCorrectedMedication(1, "MED-C");
        System.out.println("original  -> " + Arrays.toString(summary.getMedicationCodes()));
        System.out.println("corrected -> " + Arrays.toString(corrected.getMedicationCodes()));

        System.out.println();
        DischargeSummary[] batch = {
            new CriticalCareDischargeSummary("MT001", new String[] { "MED-X" }, 4),
            null,
            new DischargeSummary("MT002", new String[] { "MED-Y" })
        };
        System.out.println("Ledger " + LEDGER_ID + ":");
        System.out.println(processNightlyBatch(batch));
        System.out.println("ledger runs so far: " + ledgerRunCount);
    }
}
