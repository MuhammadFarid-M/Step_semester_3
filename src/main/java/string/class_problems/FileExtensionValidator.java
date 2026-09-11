package string.class_problems;

public class FileExtensionValidator {

    private static final String[] ACCEPTED_EXTENSIONS = { "pdf", "docx", "zip" };
    private static final String REJECTED_MESSAGE = "Rejected — invalid file type";

    public static String validateFileExtension(String filename) {
        if (filename == null || filename.isEmpty()) {
            return REJECTED_MESSAGE;
        }

        int lastDotPosition = filename.lastIndexOf('.');
        if (lastDotPosition < 0 || lastDotPosition == filename.length() - 1) {
            return REJECTED_MESSAGE;
        }

        String extension = filename.substring(lastDotPosition + 1);
        for (String accepted : ACCEPTED_EXTENSIONS) {
            if (accepted.equalsIgnoreCase(extension)) {
                return "Accepted";
            }
        }
        return REJECTED_MESSAGE;
    }

    private static void runValidation(String filename) {
        System.out.println("Input: \"" + filename + "\"");
        System.out.println(validateFileExtension(filename));
        System.out.println();
    }

    public static void main(String[] args) {
        runValidation("Assignment1.PDF");
        runValidation("notes.txt");
        runValidation("submission.docx");
        runValidation("archive");
    }
}
