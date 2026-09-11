package access_modifiers_and_encapsulation.assigment_problems;

public class MembershipFieldReachChecker {

    private static final String[] MODIFIERS = { "private", "default", "protected", "public" };

    static class LibraryMember {

        private static final int MINIMUM_ID_LENGTH = 4;

        private final String membershipId;
        final String branchCode;
        protected final double finesOwed;
        public final String displayName;

        public LibraryMember(String membershipId, String branchCode, double finesOwed, String displayName) {
            if (membershipId == null || membershipId.trim().length() < MINIMUM_ID_LENGTH) {
                throw new IllegalArgumentException("membershipId must be at least "
                        + MINIMUM_ID_LENGTH + " non-blank characters");
            }
            this.membershipId = membershipId.trim();
            this.branchCode = branchCode;
            this.finesOwed = finesOwed;
            this.displayName = displayName;
        }

        String getMembershipId() {
            return membershipId;
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

    static String summarizeByModifier(String[][] attempts) {
        int[] allowedCounts = new int[MODIFIERS.length];
        int[] deniedCounts = new int[MODIFIERS.length];

        if (attempts != null) {
            for (String[] attempt : attempts) {
                if (attempt == null || attempt.length != 2 || attempt[0] == null) {
                    continue;
                }
                int slot = modifierSlot(attempt[0]);
                if (slot < 0) {
                    continue;
                }
                if (classifyAccess(attempt[0], attempt[1]).equals("ALLOWED")) {
                    allowedCounts[slot]++;
                } else {
                    deniedCounts[slot]++;
                }
            }
        }

        StringBuilder summary = new StringBuilder();
        for (int slot = 0; slot < MODIFIERS.length; slot++) {
            if (slot > 0) {
                summary.append(" | ");
            }
            summary.append(MODIFIERS[slot]).append(": ")
                    .append(allowedCounts[slot]).append(" allowed / ")
                    .append(deniedCounts[slot]).append(" denied");
        }
        return summary.toString();
    }

    private static int modifierSlot(String fieldModifier) {
        String modifier = fieldModifier.trim().toLowerCase();
        for (int slot = 0; slot < MODIFIERS.length; slot++) {
            if (MODIFIERS[slot].equals(modifier)) {
                return slot;
            }
        }
        return -1;
    }

    public static void main(String[] args) {
        System.out.println("classifyAccess(\"private\", \"SAME_CLASS\") -> "
                + classifyAccess("private", "SAME_CLASS"));
        System.out.println("classifyAccess(\"protected\", \"DIFFERENT_PACKAGE\") -> "
                + classifyAccess("protected", "DIFFERENT_PACKAGE"));

        String[][] attempts = {
            { "private", "SAME_CLASS" }, { "private", "SAME_PACKAGE" },
            { "default", "SAME_PACKAGE" }, { "default", "DIFFERENT_PACKAGE" },
            { "protected", "SAME_PACKAGE" }, { "protected", "SAME_CLASS" },
            { "public", "DIFFERENT_PACKAGE" }
        };
        System.out.println(summarizeByModifier(attempts));

        System.out.println();
        try {
            new LibraryMember("LB9", "BR1", 0, "Priya Nair");
            System.out.println("LB9 accepted");
        } catch (IllegalArgumentException rejected) {
            System.out.println("LB9 -> construction rejected");
        }
        LibraryMember member = new LibraryMember("LB94", "BR1", 0, "Priya Nair");
        System.out.println("LB94 -> accepted, membershipId = " + member.getMembershipId());
    }
}
