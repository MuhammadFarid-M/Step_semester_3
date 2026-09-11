package access_modifiers_and_encapsulation.assigment_problems;

public class BookCopyCirculationGuard {

    static class BookInventory {

        private final int copiesTotal;
        private int copiesAvailable;

        BookInventory(int copiesTotal) {
            if (copiesTotal <= 0) {
                throw new IllegalArgumentException("copiesTotal must be positive");
            }
            this.copiesTotal = copiesTotal;
            this.copiesAvailable = copiesTotal;
        }

        void checkOut() {
            if (copiesAvailable <= 0) {
                return;
            }
            copiesAvailable--;
        }

        void checkIn() {
            if (copiesAvailable >= copiesTotal) {
                return;
            }
            copiesAvailable++;
        }

        int getCopiesAvailable() {
            return copiesAvailable;
        }

        int getCopiesTotal() {
            return copiesTotal;
        }
    }

    public static void main(String[] args) {
        try {
            new BookInventory(0);
            System.out.println("BookInventory(0) accepted");
        } catch (IllegalArgumentException rejected) {
            System.out.println("BookInventory(0) -> construction rejected");
        }

        BookInventory inventory = new BookInventory(3);
        inventory.checkOut();
        inventory.checkOut();
        inventory.checkOut();
        inventory.checkOut();
        System.out.println("after 4 checkOut() calls on 3 copies -> " + inventory.getCopiesAvailable());

        inventory.checkIn();
        inventory.checkIn();
        inventory.checkIn();
        inventory.checkIn();
        System.out.println("after 4 checkIn() calls -> " + inventory.getCopiesAvailable()
                + " (total " + inventory.getCopiesTotal() + ")");
    }
}
