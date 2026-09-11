package arrays_and_strings.assigment_problems;

/**
 * Warehouse stock report: compares the total quantity held in Section A and
 * Section B and locates the single highest-quantity item in the warehouse.
 */
public class WarehouseInventoryBalancer {

    public static void analyzeInventory(int[] sectionA, int[] sectionB) {
        if (sectionA == null || sectionB == null || sectionA.length == 0 || sectionA.length != sectionB.length) {
            System.out.println("Inventory analysis unavailable: both sections must list the same non-zero number of items.");
            return;
        }

        int sectionATotal = totalQuantity(sectionA);
        int sectionBTotal = totalQuantity(sectionB);
        String status = sectionATotal == sectionBTotal ? "Balanced" : "Not Balanced";

        int highestQuantity = sectionA[0];
        String highestSection = "Section A";
        int highestItemNumber = 1;

        for (int index = 1; index < sectionA.length; index++) {
            if (sectionA[index] > highestQuantity) {
                highestQuantity = sectionA[index];
                highestSection = "Section A";
                highestItemNumber = index + 1;
            }
        }
        // Strictly greater, so a tie keeps the Section A item that was found first.
        for (int index = 0; index < sectionB.length; index++) {
            if (sectionB[index] > highestQuantity) {
                highestQuantity = sectionB[index];
                highestSection = "Section B";
                highestItemNumber = index + 1;
            }
        }

        System.out.printf("Section A Total: %d | Section B Total: %d | Status: %s | Highest Quantity: %d (%s, Item %d)%n",
                sectionATotal, sectionBTotal, status, highestQuantity, highestSection, highestItemNumber);
    }

    private static int totalQuantity(int[] quantities) {
        int total = 0;
        for (int quantity : quantities) {
            total += quantity;
        }
        return total;
    }

    private static void runAnalysis(int[] sectionA, int[] sectionB) {
        System.out.println("Input: sectionA=" + format(sectionA) + ", sectionB=" + format(sectionB));
        analyzeInventory(sectionA, sectionB);
        System.out.println();
    }

    private static String format(int[] quantities) {
        StringBuilder formatted = new StringBuilder("{");
        for (int index = 0; index < quantities.length; index++) {
            formatted.append(index == 0 ? "" : ",").append(quantities[index]);
        }
        return formatted.append("}").toString();
    }

    public static void main(String[] args) {
        runAnalysis(new int[] { 20, 15, 30 }, new int[] { 25, 10, 30 });
        runAnalysis(new int[] { 40, 12, 18 }, new int[] { 15, 55, 9 });
    }
}
