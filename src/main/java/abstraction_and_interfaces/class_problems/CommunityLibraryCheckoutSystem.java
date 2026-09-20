package abstraction_and_interfaces.class_problems;

public class CommunityLibraryCheckoutSystem {

    interface Renewable {
        String renew();
    }

    interface Reservable {
        String reserve();
    }

    abstract static class LibraryItem {

        private static final int ITEM_ID_BASE = 1000;
        private static int itemsCreated = 0;

        private final String itemId;

        public LibraryItem() {
            itemsCreated++;
            this.itemId = "LIB-" + (ITEM_ID_BASE + itemsCreated);
        }

        public abstract int getLoanPeriodDays();

        public String getItemId() {
            return itemId;
        }
    }

    static class Textbook extends LibraryItem implements Renewable, Reservable {

        private static final int LOAN_PERIOD_DAYS = 14;

        private final String title;

        public Textbook(String title) {
            this.title = title;
        }

        @Override
        public int getLoanPeriodDays() {
            return LOAN_PERIOD_DAYS;
        }

        @Override
        public String renew() {
            return title + " renewed";
        }

        @Override
        public String reserve() {
            return title + " reserved";
        }
    }

    static class Magazine extends LibraryItem implements Renewable {

        private static final int LOAN_PERIOD_DAYS = 7;

        private final String title;

        public Magazine(String title) {
            this.title = title;
        }

        @Override
        public int getLoanPeriodDays() {
            return LOAN_PERIOD_DAYS;
        }

        @Override
        public String renew() {
            return title + " renewed";
        }
    }

    static class DigitalPass implements Renewable {

        private final String resourceName;

        public DigitalPass(String resourceName) {
            this.resourceName = resourceName;
        }

        @Override
        public String renew() {
            return resourceName + " renewed";
        }
    }

    static void processCheckouts(LibraryItem[] items) {
        if (items == null) {
            return;
        }
        for (LibraryItem item : items) {
            if (item != null) {
                System.out.println(item.getItemId() + " -> loan period "
                        + item.getLoanPeriodDays() + " days");
            }
        }
    }

    static String reserveIfSupported(Object candidate) {
        if (candidate instanceof Reservable) {
            Reservable reservable = (Reservable) candidate;
            return reservable.reserve();
        }
        return "Reservation not supported";
    }

    public static void main(String[] args) {
        Textbook textbook = new Textbook("Java Fundamentals");
        System.out.println("t.getLoanPeriodDays() -> " + textbook.getLoanPeriodDays());
        System.out.println("t.renew()             -> " + textbook.renew());
        System.out.println("t.reserve()           -> " + textbook.reserve());

        System.out.println();
        Magazine magazine = new Magazine("Tech Monthly");
        DigitalPass digitalPass = new DigitalPass("E-Journal Access");
        System.out.println("reserveIfSupported(m) -> " + reserveIfSupported(magazine));
        System.out.println("reserveIfSupported(d) -> " + reserveIfSupported(digitalPass));

        System.out.println();
        // Upcasting: a Textbook object stored in a LibraryItem-typed variable.
        LibraryItem reference = textbook;
        System.out.println("reserveIfSupported(ref) -> " + reserveIfSupported(reference));

        System.out.println();
        System.out.println("processCheckouts over the physical items:");
        processCheckouts(new LibraryItem[] { textbook, magazine });
        System.out.println(digitalPass.renew() + " (DigitalPass is Renewable but not a LibraryItem)");
    }
}
