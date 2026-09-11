package access_modifiers_and_encapsulation.class_problems;

public class FieldVisibilityIntakeValidator {

    static class PatientRecord {

        private static final int MINIMUM_ID_LENGTH = 4;

        private final String patientId;
        final String wardCode;
        protected final double vitalsScore;
        public final String facilityName;

        public PatientRecord(String patientId, String wardCode, double vitalsScore, String facilityName) {
            if (patientId == null || patientId.trim().length() < MINIMUM_ID_LENGTH) {
                throw new IllegalArgumentException("patientId must be at least "
                        + MINIMUM_ID_LENGTH + " non-blank characters");
            }
            this.patientId = patientId.trim();
            this.wardCode = wardCode;
            this.vitalsScore = vitalsScore;
            this.facilityName = facilityName;
        }

        String getPatientId() {
            return patientId;
        }
    }

    static String classifyAccess(String fieldModifier, String accessorContext) {
        if (fieldModifier == null || accessorContext == null) {
            return "DENIED";
        }
        String modifier = fieldModifier.trim().toLowerCase();
        String context = accessorContext.trim().toUpperCase();

        boolean sameClass = context.equals("SAME_CLASS");
        boolean samePackage = context.equals("SAME_PACKAGE");
        boolean differentPackage = context.equals("DIFFERENT_PACKAGE");
        if (!sameClass && !samePackage && !differentPackage) {
            return "DENIED";
        }

        boolean allowed;
        switch (modifier) {
            case "public":
                allowed = true;
                break;
            case "protected":
                allowed = sameClass || samePackage;
                break;
            case "default":
                allowed = sameClass || samePackage;
                break;
            case "private":
                allowed = sameClass;
                break;
            default:
                allowed = false;
                break;
        }
        return allowed ? "ALLOWED" : "DENIED";
    }

    static String summarizeBatch(String[][] attempts) {
        int allowedCount = 0;
        int deniedCount = 0;
        if (attempts != null) {
            for (String[] attempt : attempts) {
                if (attempt == null || attempt.length != 2) {
                    deniedCount++;
                    continue;
                }
                if (classifyAccess(attempt[0], attempt[1]).equals("ALLOWED")) {
                    allowedCount++;
                } else {
                    deniedCount++;
                }
            }
        }
        return "Allowed: " + allowedCount + " | Denied: " + deniedCount;
    }

    public static void main(String[] args) {
        System.out.println("classifyAccess(\"private\", \"SAME_CLASS\") -> "
                + classifyAccess("private", "SAME_CLASS"));
        System.out.println("classifyAccess(\"default\", \"DIFFERENT_PACKAGE\") -> "
                + classifyAccess("default", "DIFFERENT_PACKAGE"));

        String[][] attempts = {
            { "protected", "SAME_PACKAGE" },
            { "protected", "DIFFERENT_PACKAGE" },
            { "public", "DIFFERENT_PACKAGE" }
        };
        System.out.println("summarizeBatch(...) -> " + summarizeBatch(attempts));

        System.out.println();
        try {
            new PatientRecord("MT9", "W3", 98.2, "MediTrack Central");
            System.out.println("MT9 accepted");
        } catch (IllegalArgumentException rejected) {
            System.out.println("MT9 -> construction rejected");
        }
        PatientRecord record = new PatientRecord("MT94", "W3", 98.2, "MediTrack Central");
        System.out.println("MT94 -> accepted, patientId = " + record.getPatientId());
    }
}
