package access_modifiers_and_encapsulation.assigment_problems;

public class ReferenceDeskSubclassReach {

    static String classifyAccess(String fieldModifier, String accessorContext) {
        if (fieldModifier == null || accessorContext == null) {
            return "DENIED";
        }
        String modifier = fieldModifier.trim().toLowerCase();
        String context = accessorContext.trim().toUpperCase();

        boolean sameClass = context.equals("SAME_CLASS");
        boolean samePackage = context.equals("SAME_PACKAGE");
        boolean subclassOwnType = context.equals("SUBCLASS_DIFFERENT_PACKAGE_OWN_TYPE");
        boolean subclassParentType = context.equals("SUBCLASS_DIFFERENT_PACKAGE_PARENT_TYPE");
        boolean differentPackage = context.equals("DIFFERENT_PACKAGE");
        if (!sameClass && !samePackage && !differentPackage && !subclassOwnType && !subclassParentType) {
            return "DENIED";
        }

        boolean allowed;
        switch (modifier) {
            case "public":
                allowed = true;
                break;
            case "protected":
                allowed = sameClass || samePackage || subclassOwnType;
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

    static String describeContext(String accessorContext) {
        if (accessorContext == null || accessorContext.trim().isEmpty()) {
            return "";
        }
        String[] words = accessorContext.trim().split("_");
        StringBuilder description = new StringBuilder();
        for (String word : words) {
            if (word.isEmpty()) {
                continue;
            }
            if (description.length() > 0) {
                description.append(" ");
            }
            description.append(Character.toUpperCase(word.charAt(0)))
                    .append(word.substring(1).toLowerCase());
        }
        return description.toString();
    }

    private static void printMatrixRow(String modifier) {
        String[] contexts = {
            "SAME_CLASS", "SAME_PACKAGE", "DIFFERENT_PACKAGE",
            "SUBCLASS_DIFFERENT_PACKAGE_OWN_TYPE", "SUBCLASS_DIFFERENT_PACKAGE_PARENT_TYPE"
        };
        StringBuilder row = new StringBuilder(String.format("%-10s", modifier));
        for (String context : contexts) {
            row.append(" | ").append(String.format("%-7s", classifyAccess(modifier, context)));
        }
        System.out.println(row.toString());
    }

    public static void main(String[] args) {
        System.out.println("classifyAccess(\"protected\", \"SUBCLASS_DIFFERENT_PACKAGE_OWN_TYPE\") -> "
                + classifyAccess("protected", "SUBCLASS_DIFFERENT_PACKAGE_OWN_TYPE"));
        System.out.println("classifyAccess(\"protected\", \"SUBCLASS_DIFFERENT_PACKAGE_PARENT_TYPE\") -> "
                + classifyAccess("protected", "SUBCLASS_DIFFERENT_PACKAGE_PARENT_TYPE"));
        System.out.println("describeContext(\"SUBCLASS_DIFFERENT_PACKAGE_OWN_TYPE\") -> \""
                + describeContext("SUBCLASS_DIFFERENT_PACKAGE_OWN_TYPE") + "\"");

        System.out.println();
        System.out.println("Modifier   | SameClass | SamePkg | DiffPkg | SubOwn  | SubParent");
        printMatrixRow("private");
        printMatrixRow("default");
        printMatrixRow("protected");
        printMatrixRow("public");
    }
}
