package string.assigment_problems;

public class ProductInventoryCsvParser {

    private static final int EXPECTED_FIELD_COUNT = 3;

    public static void parseInventoryRecord(String csvLine) {
        if (csvLine == null || csvLine.trim().isEmpty()) {
            System.out.println("Invalid Record");
            return;
        }

        String[] fields = csvLine.split(",");
        if (fields.length != EXPECTED_FIELD_COUNT) {
            System.out.println("Invalid Record");
            return;
        }

        System.out.println("Product: " + fields[0].trim()
                + " | SKU: " + fields[1].trim()
                + " | Qty: " + fields[2].trim());
    }

    private static void runParse(String csvLine) {
        System.out.println("Input: \"" + csvLine + "\"");
        parseInventoryRecord(csvLine);
        System.out.println();
    }

    public static void main(String[] args) {
        runParse("Wireless Mouse,WM-2201,150");
        runParse("Wireless Mouse,150");
        runParse("Mechanical Keyboard,MK-7788,40");
    }
}
