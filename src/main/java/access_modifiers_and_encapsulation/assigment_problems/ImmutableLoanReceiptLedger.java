package access_modifiers_and_encapsulation.assigment_problems;

import java.util.Arrays;

public class ImmutableLoanReceiptLedger {

    static class LoanReceipt {

        private final String memberId;
        private final String[] bookIds;

        public LoanReceipt(String memberId, String[] bookIds) {
            if (memberId == null || memberId.trim().isEmpty()) {
                throw new IllegalArgumentException("memberId is required");
            }
            if (bookIds == null) {
                throw new IllegalArgumentException("bookIds is required");
            }
            for (String bookId : bookIds) {
                if (!isValidBookId(bookId)) {
                    throw new IllegalArgumentException("Invalid book id: " + bookId);
                }
            }
            this.memberId = memberId.trim();
            this.bookIds = new String[bookIds.length];
            for (int index = 0; index < bookIds.length; index++) {
                this.bookIds[index] = bookIds[index];
            }
        }

        static boolean isValidBookId(String bookId) {
            if (bookId == null || bookId.length() != 6 || !bookId.startsWith("BK-")) {
                return false;
            }
            for (int index = 3; index < bookId.length(); index++) {
                if (!Character.isDigit(bookId.charAt(index))) {
                    return false;
                }
            }
            return true;
        }

        final String getMemberId() {
            return memberId;
        }

        final String[] getBookIds() {
            String[] copy = new String[bookIds.length];
            for (int index = 0; index < bookIds.length; index++) {
                copy[index] = bookIds[index];
            }
            return copy;
        }

        final LoanReceipt withCorrectedBookId(int index, String newId) {
            if (index < 0 || index >= bookIds.length) {
                throw new IllegalArgumentException("No book at index " + index);
            }
            if (!isValidBookId(newId)) {
                throw new IllegalArgumentException("Invalid book id: " + newId);
            }
            String[] corrected = getBookIds();
            corrected[index] = newId;
            return new LoanReceipt(memberId, corrected);
        }
    }

    static class ReferenceOnlyLoanReceipt extends LoanReceipt {

        private final String roomNumber;

        public ReferenceOnlyLoanReceipt(String memberId, String[] bookIds, String roomNumber) {
            super(memberId, bookIds);
            if (roomNumber == null || roomNumber.trim().isEmpty()) {
                throw new IllegalArgumentException("roomNumber is required");
            }
            this.roomNumber = roomNumber.trim();
        }

        final String getRoomNumber() {
            return roomNumber;
        }
    }

    static final String LEDGER_ID;
    private static int ledgerRunCount;

    static {
        LEDGER_ID = "PAGETURNER-NIGHTLY";
        ledgerRunCount = 0;
    }

    static String processNightlyCirculation(LoanReceipt[] receipts) {
        ledgerRunCount++;
        int processed = 0;
        int nullSkipped = 0;
        int referenceOnly = 0;
        int regular = 0;

        if (receipts != null) {
            for (LoanReceipt receipt : receipts) {
                if (receipt == null) {
                    nullSkipped++;
                    continue;
                }
                if (receipt instanceof ReferenceOnlyLoanReceipt) {
                    ReferenceOnlyLoanReceipt reference = (ReferenceOnlyLoanReceipt) receipt;
                    System.out.println("  settled " + reference.getMemberId()
                            + " as reference-only (room " + reference.getRoomNumber() + ", no due date)");
                    referenceOnly++;
                } else {
                    System.out.println("  settled " + receipt.getMemberId() + " as regular loan");
                    regular++;
                }
                processed++;
            }
        }

        return processed + " processed | " + nullSkipped + " null skipped | "
                + referenceOnly + " reference-only | " + regular + " regular";
    }

    public static void main(String[] args) {
        try {
            new LoanReceipt("LIB-8841", new String[] { "BK-100", "bad" });
            System.out.println("accepted");
        } catch (IllegalArgumentException rejected) {
            System.out.println("BK-100 + bad -> construction rejected");
        }

        LoanReceipt receipt = new LoanReceipt("LIB-8841", new String[] { "BK-100", "BK-101" });
        String[] ids = receipt.getBookIds();
        ids[0] = "HACKED";
        System.out.println("after tampering with the returned array, getBookIds()[0] -> "
                + receipt.getBookIds()[0]);

        LoanReceipt corrected = receipt.withCorrectedBookId(1, "BK-102");
        System.out.println("original  -> " + Arrays.toString(receipt.getBookIds()));
        System.out.println("corrected -> " + Arrays.toString(corrected.getBookIds()));

        System.out.println();
        LoanReceipt[] batch = {
            new ReferenceOnlyLoanReceipt("LIB-001", new String[] { "BK-200" }, "Reading Room 3"),
            null,
            new LoanReceipt("LIB-002", new String[] { "BK-201" })
        };
        System.out.println("Ledger " + LEDGER_ID + ":");
        System.out.println(processNightlyCirculation(batch));
        System.out.println("ledger runs so far: " + ledgerRunCount);
    }
}
