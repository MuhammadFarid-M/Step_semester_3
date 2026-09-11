package access_modifiers_and_encapsulation.assigment_problems;

public class LibraryMemberJavaBean {

    public static class LibraryMember {

        private String membershipId;
        private String name;
        private boolean premiumMember;
        private int securityAnswerFingerprint;

        public LibraryMember() {
            this(null);
        }

        public LibraryMember(String name) {
            this(null, name);
        }

        public LibraryMember(String membershipId, String name) {
            this.membershipId = null;
            this.name = name;
            this.premiumMember = false;
            this.securityAnswerFingerprint = 0;
            applyMembershipId(membershipId);
        }

        public String getMembershipId() {
            return membershipId;
        }

        public void setMembershipId(String id) {
            applyMembershipId(id);
        }

        private void applyMembershipId(String id) {
            if (this.membershipId != null) {
                return;
            }
            if (id == null || id.trim().isEmpty()) {
                return;
            }
            this.membershipId = id.trim();
        }

        public String getName() {
            return name;
        }

        public void setName(String name) {
            this.name = name;
        }

        public boolean isPremiumMember() {
            return premiumMember;
        }

        public void setPremiumMember(boolean premium) {
            this.premiumMember = premium;
        }

        public void setSecurityAnswer(String answer) {
            if (answer == null || answer.trim().isEmpty()) {
                System.out.println("Security answer rejected: no value supplied");
                return;
            }
            this.securityAnswerFingerprint = fingerprint(answer.trim().toLowerCase());
            System.out.println("Security answer stored (one-way, not retrievable)");
        }

        boolean matchesSecurityAnswer(String candidate) {
            return candidate != null && securityAnswerFingerprint != 0
                    && fingerprint(candidate.trim().toLowerCase()) == securityAnswerFingerprint;
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
        System.out.println("new LibraryMember(\"Priya Nair\").getMembershipId() -> "
                + new LibraryMember("Priya Nair").getMembershipId());
        System.out.println("new LibraryMember(\"LIB-8841\", \"Priya Nair\").getMembershipId() -> "
                + new LibraryMember("LIB-8841", "Priya Nair").getMembershipId());

        LibraryMember member = new LibraryMember();
        member.setMembershipId("LIB-8841");
        member.setMembershipId("FAKE-0000");
        System.out.println("after a second setMembershipId, getMembershipId() -> " + member.getMembershipId());

        System.out.println();
        member.setName("Priya Nair");
        member.setPremiumMember(true);
        System.out.println("getName() -> " + member.getName()
                + " | isPremiumMember() -> " + member.isPremiumMember());

        System.out.println();
        member.setSecurityAnswer("  Chennai ");
        System.out.println("matchesSecurityAnswer(\"chennai\") -> " + member.matchesSecurityAnswer("chennai"));
        System.out.println("matchesSecurityAnswer(\"Mumbai\") -> " + member.matchesSecurityAnswer("Mumbai"));
    }
}
