package abstraction_and_interfaces.assigment_problems;

public class OneClickDataExport {

    private static int totalExports = 0;

    interface Exportable {
        String exportData();
    }

    static class ReportGenerator implements Exportable {

        private final String reportName;

        public ReportGenerator(String reportName) {
            this.reportName = reportName;
        }

        @Override
        public String exportData() {
            totalExports++;
            return "Exported report: " + reportName;
        }
    }

    static class UserProfile implements Exportable {

        private final String username;

        public UserProfile(String username) {
            this.username = username;
        }

        @Override
        public String exportData() {
            totalExports++;
            return "Exported profile: " + username;
        }
    }

    static int getTotalExports() {
        return totalExports;
    }

    static void exportAll(Exportable[] items) {
        if (items == null) {
            return;
        }
        for (Exportable item : items) {
            if (item != null) {
                System.out.println(item.exportData());
            }
        }
    }

    public static void main(String[] args) {
        ReportGenerator report = new ReportGenerator("Sales Q1");
        UserProfile profile = new UserProfile("jane_doe");

        // Upcasting: a ReportGenerator object stored in an Exportable interface-typed variable.
        Exportable reference = report;
        exportAll(new Exportable[] { reference, profile });
        System.out.println("getTotalExports() -> " + getTotalExports());

        System.out.println();
        System.out.println(report.exportData());
        System.out.println(profile.exportData());
        System.out.println("getTotalExports() -> " + getTotalExports());

        System.out.println();
        System.out.println("ReportGenerator and UserProfile share no parent class at all,");
        System.out.println("only the Exportable contract and the one shared export counter.");
    }
}
