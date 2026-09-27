package oop_design_and_uml.class_problems;

import java.time.LocalDate;
import java.time.temporal.ChronoUnit;

public class EmployeeLeaveRequestSystem {

    abstract static class Employee {

        private final String employeeName;

        public Employee(String employeeName) {
            this.employeeName = employeeName;
        }

        public abstract int getAnnualLeaveAllowance();

        public abstract String getEmployeeType();

        public String getEmployeeName() {
            return employeeName;
        }
    }

    static class FullTimeEmployee extends Employee {

        public FullTimeEmployee(String employeeName) {
            super(employeeName);
        }

        @Override
        public int getAnnualLeaveAllowance() {
            return 24;
        }

        @Override
        public String getEmployeeType() {
            return "Full-time";
        }
    }

    static class PartTimeEmployee extends Employee {

        public PartTimeEmployee(String employeeName) {
            super(employeeName);
        }

        @Override
        public int getAnnualLeaveAllowance() {
            return 12;
        }

        @Override
        public String getEmployeeType() {
            return "Part-time";
        }
    }

    static class ContractEmployee extends Employee {

        public ContractEmployee(String employeeName) {
            super(employeeName);
        }

        @Override
        public int getAnnualLeaveAllowance() {
            return 6;
        }

        @Override
        public String getEmployeeType() {
            return "Contract";
        }
    }

    enum LeaveStatus {
        PENDING, APPROVED, REJECTED;

        public String label() {
            return charAt0Upper(name());
        }

        private static String charAt0Upper(String value) {
            return value.charAt(0) + value.substring(1).toLowerCase();
        }
    }

    static class LeaveRequest {

        private final Employee employee;
        private final LocalDate startDate;
        private final LocalDate endDate;
        private LeaveStatus status;

        public LeaveRequest(Employee employee, LocalDate startDate, LocalDate endDate) {
            this.employee = employee;
            this.startDate = startDate;
            this.endDate = endDate;
            this.status = LeaveStatus.PENDING;
        }

        public Employee getEmployee() {
            return employee;
        }

        public LocalDate getStartDate() {
            return startDate;
        }

        public LocalDate getEndDate() {
            return endDate;
        }

        public LeaveStatus getStatus() {
            return status;
        }

        public long getRequestedDays() {
            return ChronoUnit.DAYS.between(startDate, endDate) + 1;
        }

        boolean moveTo(LeaveStatus target) {
            if (status != LeaveStatus.PENDING) {
                System.out.println("Cannot change status: " + status.label()
                        + " request cannot revert to " + target.label() + ".");
                return false;
            }
            if (target == LeaveStatus.PENDING) {
                System.out.println("Cannot change status: the request is already Pending.");
                return false;
            }
            status = target;
            return true;
        }
    }

    static class LeaveManager {

        public LeaveRequest submit(Employee employee, LocalDate startDate, LocalDate endDate) {
            LeaveRequest request = new LeaveRequest(employee, startDate, endDate);
            System.out.println("Leave request submitted by " + employee.getEmployeeName()
                    + " for " + startDate + " to " + endDate
                    + ". Status: " + request.getStatus().label());
            return request;
        }

        public void approve(LeaveRequest request) {
            if (request.getRequestedDays() > request.getEmployee().getAnnualLeaveAllowance()) {
                System.out.println("Leave request for " + request.getEmployee().getEmployeeName()
                        + " exceeds the " + request.getEmployee().getEmployeeType()
                        + " allowance of " + request.getEmployee().getAnnualLeaveAllowance() + " days.");
                reject(request);
                return;
            }
            if (request.moveTo(LeaveStatus.APPROVED)) {
                System.out.println("Leave request for " + request.getEmployee().getEmployeeName()
                        + " approved. Status: " + request.getStatus().label());
            }
        }

        public void reject(LeaveRequest request) {
            if (request.moveTo(LeaveStatus.REJECTED)) {
                System.out.println("Leave request for " + request.getEmployee().getEmployeeName()
                        + " rejected. Status: " + request.getStatus().label());
            }
        }

        public void revertToPending(LeaveRequest request) {
            request.moveTo(LeaveStatus.PENDING);
        }
    }

    public static void main(String[] args) {
        LeaveManager leaveManager = new LeaveManager();

        Employee johnDoe = new FullTimeEmployee("John Doe");
        LeaveRequest johnsRequest = leaveManager.submit(johnDoe,
                LocalDate.of(2024, 10, 10), LocalDate.of(2024, 10, 12));
        leaveManager.approve(johnsRequest);

        Employee janeSmith = new PartTimeEmployee("Jane Smith");
        LeaveRequest janesRequest = leaveManager.submit(janeSmith,
                LocalDate.of(2024, 11, 1), LocalDate.of(2024, 11, 5));

        leaveManager.revertToPending(johnsRequest);

        System.out.println();
        leaveManager.reject(janesRequest);
        leaveManager.approve(janesRequest);

        System.out.println();
        Employee contractor = new ContractEmployee("Ravi Kumar");
        LeaveRequest longRequest = leaveManager.submit(contractor,
                LocalDate.of(2024, 12, 1), LocalDate.of(2024, 12, 20));
        leaveManager.approve(longRequest);
        System.out.println("A new employee type plugged in without touching the review workflow.");
    }
}
