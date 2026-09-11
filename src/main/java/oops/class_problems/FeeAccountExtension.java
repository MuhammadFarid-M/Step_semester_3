package oops.class_problems;

public class FeeAccountExtension {

    static class FeeAccount {

        private String regNo;
        private double totalFee;
        private double amountPaid;

        FeeAccount(String regNo, double totalFee) {
            this.regNo = regNo;
            this.totalFee = totalFee;
            this.amountPaid = 0.0;
        }

        void pay(double amount) {
            if (amount <= 0) {
                System.out.println("Payment rejected for " + regNo + ": amount must be positive");
                return;
            }
            amountPaid += amount;
        }

        double getDue() {
            return totalFee - amountPaid;
        }
    }

    static class HostelFeeAccount extends FeeAccount {

        HostelFeeAccount(String regNo, double totalFee) {
            super(regNo, totalFee);
        }

        void payInTwoInstallments(double amount) {
            if (amount <= 0) {
                System.out.println("Payment rejected: amount must be positive");
                return;
            }
            double installment = amount / 2;
            pay(installment);
            pay(installment);
        }
    }

    static class ScholarshipFeeAccount extends FeeAccount {

        private double scholarshipPercent;

        ScholarshipFeeAccount(String regNo, double totalFee, double scholarshipPercent) {
            super(regNo, totalFee);
            if (scholarshipPercent < 0) {
                this.scholarshipPercent = 0;
            } else if (scholarshipPercent > 100) {
                this.scholarshipPercent = 100;
            } else {
                this.scholarshipPercent = scholarshipPercent;
            }
        }

        double effectiveDue() {
            double due = getDue();
            return due - (due * scholarshipPercent / 100);
        }
    }

    public static void main(String[] args) {
        FeeAccount plainAccount = new FeeAccount("RA2311003010101", 150000);
        plainAccount.pay(150000);

        HostelFeeAccount hostelAccount = new HostelFeeAccount("RA2311003010102", 200000);
        hostelAccount.payInTwoInstallments(60000);

        ScholarshipFeeAccount scholarshipAccount =
                new ScholarshipFeeAccount("RA2311003010103", 180000, 20);

        FeeAccount[] accounts = { plainAccount, hostelAccount, scholarshipAccount };
        for (FeeAccount account : accounts) {
            if (account instanceof ScholarshipFeeAccount) {
                ScholarshipFeeAccount scholarship = (ScholarshipFeeAccount) account;
                System.out.println("Scholarship account effective due: Rs " + scholarship.effectiveDue());
            } else if (account instanceof HostelFeeAccount) {
                System.out.println("Hostel account due: Rs " + account.getDue());
            } else {
                System.out.println("Plain account due: Rs " + account.getDue());
            }
        }

        System.out.println();
        plainAccount.pay(-500);
    }
}
