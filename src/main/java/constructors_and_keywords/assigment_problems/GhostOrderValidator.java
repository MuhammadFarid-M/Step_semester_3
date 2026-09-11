package constructors_and_keywords.assigment_problems;

public class GhostOrderValidator {

    static class FoodOrder {

        private final String studentName;
        private final String dishName;
        private boolean delivered;

        public FoodOrder(String studentName, String dishName) {
            if (isBlank(studentName)) {
                throw new IllegalArgumentException("Student name is missing");
            }
            if (isBlank(dishName)) {
                throw new IllegalArgumentException("Dish name is missing");
            }
            this.studentName = studentName.trim();
            this.dishName = dishName.trim();
            this.delivered = false;
        }

        private static boolean isBlank(String value) {
            return value == null || value.trim().isEmpty();
        }

        void markDelivered() {
            if (delivered) {
                System.out.println("WARNING: " + dishName + " for " + studentName
                        + " was already delivered - possible double-serve");
                return;
            }
            delivered = true;
            System.out.println(dishName + " delivered to " + studentName);
        }
    }

    static void processBatch(String[][] rawOrders) {
        if (rawOrders == null || rawOrders.length == 0) {
            System.out.println("Valid: 0 | Rejected: 0");
            return;
        }

        int validCount = 0;
        int rejectedCount = 0;
        for (String[] rawOrder : rawOrders) {
            if (rawOrder == null || rawOrder.length != 2) {
                rejectedCount++;
                continue;
            }
            try {
                new FoodOrder(rawOrder[0], rawOrder[1]);
                validCount++;
            } catch (IllegalArgumentException rejected) {
                rejectedCount++;
            }
        }
        System.out.println("Valid: " + validCount + " | Rejected: " + rejectedCount);
    }

    public static void main(String[] args) {
        String[][] rawOrders = {
            { "Ravi", "Paneer Butter Masala" },
            { "", "Chole Bhature" },
            { "Meera", "   " },
            { "Divya", "Veg Biryani" }
        };
        processBatch(rawOrders);

        System.out.println();
        FoodOrder order = new FoodOrder("Ravi", "Paneer Butter Masala");
        order.markDelivered();
        order.markDelivered();

        System.out.println();
        try {
            new FoodOrder(null, "Idli");
        } catch (IllegalArgumentException rejected) {
            System.out.println("Construction blocked: " + rejected.getMessage());
        }
    }
}
