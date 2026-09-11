package oops.assigment_problems;

class BookIssue {

    private String title;
    private String borrowerName;
    private int daysOverdue;

    BookIssue(String title, String borrowerName, int daysOverdue) {
        this.title = title;
        this.borrowerName = borrowerName;
        this.daysOverdue = daysOverdue;
    }

    String getTitle() {
        return title;
    }

    String getBorrowerName() {
        return borrowerName;
    }

    int getDaysOverdue() {
        return daysOverdue;
    }

    double fineAmount() {
        if (daysOverdue > 0) {
            return daysOverdue * 5.0;
        }
        return 0.0;
    }

    boolean isSeverelyOverdue() {
        return daysOverdue > 14;
    }

    // totalFineCollected is static because a total across many BookIssue objects is a property of
    // the whole collection rather than of any single issue: it cannot be computed without the array
    // and there is no one book it belongs to. fineAmount is an instance method because it is worked
    // out entirely from one book's own daysOverdue field.
    static double totalFineCollected(BookIssue[] issues) {
        if (issues == null || issues.length == 0) {
            return 0.0;
        }
        double total = 0.0;
        for (BookIssue issue : issues) {
            total += issue.fineAmount();
        }
        return total;
    }
}

public class LibraryFineSystem {

    public static void main(String[] args) {
        BookIssue[] issues = {
            new BookIssue("Clean Code", "Aditi", 18),
            new BookIssue("Effective Java", "Rohan", 5),
            new BookIssue("Refactoring", "Sneha", 0),
            new BookIssue("DSA Handbook", "Vikram", 21),
            new BookIssue("Design Patterns", "Priya", 9)
        };

        for (BookIssue issue : issues) {
            String status = issue.isSeverelyOverdue() ? "Severely overdue" : "OK";
            System.out.println(issue.getTitle() + " - " + issue.getDaysOverdue() + " days - " + status);
        }

        System.out.println("Total fine collected: Rs " + BookIssue.totalFineCollected(issues));

        System.out.println();
        for (BookIssue issue : issues) {
            System.out.println(issue.getBorrowerName() + " owes Rs " + issue.fineAmount());
        }
    }
}
