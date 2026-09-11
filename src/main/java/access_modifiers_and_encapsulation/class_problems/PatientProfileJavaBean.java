package access_modifiers_and_encapsulation.class_problems;

public class PatientProfileJavaBean {

    public static class PatientProfile {

        private static final int MIN_PIN_LENGTH = 4;
        private static final int MAX_PIN_LENGTH = 6;

        private String patientId;
        private String name;
        private boolean discharged;
        private int lockerPinFingerprint;

        public PatientProfile() {
            this(null);
        }

        public PatientProfile(String name) {
            this(null, name);
        }

        public PatientProfile(String patientId, String name) {
            this.patientId = null;
            this.name = name;
            this.discharged = false;
            this.lockerPinFingerprint = 0;
            applyPatientId(patientId);
        }

        public String getPatientId() {
            return patientId;
        }

        public void setPatientId(String id) {
            applyPatientId(id);
        }

        private void applyPatientId(String id) {
            if (this.patientId != null) {
                return;
            }
            if (id == null || id.trim().isEmpty()) {
                return;
            }
            this.patientId = id.trim();
        }

        public String getName() {
            return name;
        }

        public void setName(String name) {
            this.name = name;
        }

        public boolean isDischarged() {
            return discharged;
        }

        public void setDischarged(boolean discharged) {
            this.discharged = discharged;
        }

        public void setLockerPin(String pin) {
            if (pin == null) {
                System.out.println("Locker PIN rejected: no value supplied");
                return;
            }
            String trimmed = pin.trim();
            if (trimmed.length() < MIN_PIN_LENGTH || trimmed.length() > MAX_PIN_LENGTH) {
                System.out.println("Locker PIN rejected: must be 4 to 6 digits");
                return;
            }
            for (int index = 0; index < trimmed.length(); index++) {
                if (!Character.isDigit(trimmed.charAt(index))) {
                    System.out.println("Locker PIN rejected: must be digits only");
                    return;
                }
            }
            this.lockerPinFingerprint = fingerprint(trimmed);
            System.out.println("Locker PIN stored (one-way, not retrievable)");
        }

        boolean matchesLockerPin(String candidate) {
            return candidate != null && lockerPinFingerprint != 0
                    && fingerprint(candidate.trim()) == lockerPinFingerprint;
        }

        private static int fingerprint(String value) {
            int hash = 7;
            for (int index = 0; index < value.length(); index++) {
                hash = hash * 31 + value.charAt(index);
            }
            return hash;
        }
    }

    public static void main(String[] args) {
        System.out.println("new PatientProfile(\"Arjun Iyer\").getPatientId() -> "
                + new PatientProfile("Arjun Iyer").getPatientId());
        System.out.println("new PatientProfile(\"MT2026-0142\", \"Arjun Iyer\").getPatientId() -> "
                + new PatientProfile("MT2026-0142", "Arjun Iyer").getPatientId());

        PatientProfile profile = new PatientProfile();
        profile.setPatientId("MT2026-0142");
        profile.setPatientId("HACKED-0000");
        System.out.println("after a second setPatientId, getPatientId() -> " + profile.getPatientId());

        System.out.println();
        profile.setName("Arjun Iyer");
        profile.setDischarged(true);
        System.out.println("getName() -> " + profile.getName() + " | isDischarged() -> " + profile.isDischarged());

        System.out.println();
        profile.setLockerPin("12");
        profile.setLockerPin("12ab");
        profile.setLockerPin("4821");
        System.out.println("matchesLockerPin(\"4821\") -> " + profile.matchesLockerPin("4821"));
        System.out.println("matchesLockerPin(\"0000\") -> " + profile.matchesLockerPin("0000"));
    }
}
