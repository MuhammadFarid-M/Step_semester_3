package abstraction_and_interfaces.class_problems;

public class QuarterlyBonusCalculator {

    interface Auditable {
        String auditRecord();
    }

    abstract static class StaffMember {

        private static final double DEFAULT_BONUS_RATE = 0.10;

        private double baseSalary;
        protected final double bonusRate;

        public StaffMember(double baseSalary) {
            this(baseSalary, DEFAULT_BONUS_RATE);
        }

        public StaffMember(double baseSalary, double bonusRate) {
            this.bonusRate = bonusRate;
            applySalary(baseSalary);
        }

        public abstract double calculateBonus();

        public double getSalary() {
            return baseSalary;
        }

        public void setSalary(double baseSalary) {
            applySalary(baseSalary);
        }

        private void applySalary(double baseSalary) {
            if (baseSalary < 0) {
                System.out.println("setSalary(" + baseSalary + ") rejected, salary unchanged");
                return;
            }
            this.baseSalary = baseSalary;
        }
    }

    static class TeamLead extends StaffMember implements Auditable {

        private final int teamSize;

        public TeamLead(double baseSalary, int teamSize) {
            super(baseSalary);
            this.teamSize = teamSize;
        }

        public TeamLead(double baseSalary, double bonusRate, int teamSize) {
            super(baseSalary, bonusRate);
            this.teamSize = teamSize;
        }

        @Override
        public double calculateBonus() {
            return getSalary() * bonusRate;
        }

        @Override
        public String auditRecord() {
            return "TeamLead audit: " + teamSize + " team members, salary $" + getSalary();
        }
    }

    static class FieldEngineer extends StaffMember {

        public FieldEngineer(double baseSalary) {
            super(baseSalary);
        }

        @Override
        public double calculateBonus() {
            return getSalary() * bonusRate;
        }
    }

    static String getAuditIfApplicable(StaffMember staffMember) {
        if (staffMember instanceof Auditable) {
            Auditable auditable = (Auditable) staffMember;
            return auditable.auditRecord();
        }
        return "No audit required";
    }

    public static void main(String[] args) {
        TeamLead teamLead = new TeamLead(60000, 5);
        System.out.println("new TeamLead(60000, 5).calculateBonus() -> " + teamLead.calculateBonus());

        TeamLead higherRateLead = new TeamLead(60000, 0.20, 5);
        System.out.println("new TeamLead(60000, 0.20, 5).calculateBonus() -> "
                + higherRateLead.calculateBonus());

        System.out.println();
        teamLead.setSalary(-5000);
        System.out.println("salary after the rejected set -> " + teamLead.getSalary());

        System.out.println();
        // Upcasting: a TeamLead object stored in a StaffMember-typed variable.
        StaffMember reference = teamLead;
        System.out.println("getAuditIfApplicable(ref) -> " + getAuditIfApplicable(reference));

        FieldEngineer engineer = new FieldEngineer(45000);
        System.out.println("getAuditIfApplicable(engineer) -> " + getAuditIfApplicable(engineer));
    }
}
