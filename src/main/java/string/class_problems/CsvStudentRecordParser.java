package string.class_problems;

public class CsvStudentRecordParser {

    private static final int EXPECTED_FIELD_COUNT = 3;

    public static void parseStudentRecord(String csvLine) {
        if (csvLine == null || csvLine.trim().isEmpty()) {
            System.out.println("Invalid Record");
            return;
        }

        String[] fields = csvLine.split(",");
        if (fields.length != EXPECTED_FIELD_COUNT) {
            System.out.println("Invalid Record");
            return;
        }

        System.out.println("Name: " + fields[0].trim()
                + " | Roll No: " + fields[1].trim()
                + " | Dept: " + fields[2].trim());
    }

    private static void runParse(String csvLine) {
        System.out.println("Input: \"" + csvLine + "\"");
        parseStudentRecord(csvLine);
        System.out.println();
    }

    public static void main(String[] args) {
        runParse("Ananya Verma,RA2211003010123,CSE");
        runParse("Ananya Verma,CSE");
        runParse("Ravi Kumar,RA2211003010456,ECE");
    }
}
