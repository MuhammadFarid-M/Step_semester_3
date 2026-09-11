package oops.assigment_problems;

import oops.assigment_problems.EmployeeExtension.Employee;
import oops.assigment_problems.EmployeeExtension.InternEmployee;
import oops.assigment_problems.EmployeeExtension.ManagerEmployee;
import oops.assigment_problems.ParkingSlotAllotment.ParkingSlot;

public class HrAndParkingMiniSystem {

    static class CompanyEmployeeRecord {

        static int totalRecords = 0;

        String name;
        String empId;
        Employee employee;
        ParkingSlot slot;

        CompanyEmployeeRecord(String name, String empId, Employee employee) {
            totalRecords++;
            this.name = name;
            this.empId = empId;
            this.employee = employee;
            this.slot = null;
        }

        void assignSlot(ParkingSlot slot) {
            this.slot = slot;
        }

        double effectivePay() {
            if (employee instanceof ManagerEmployee) {
                ManagerEmployee manager = (ManagerEmployee) employee;
                return manager.effectiveSalary();
            }
            if (employee instanceof InternEmployee) {
                InternEmployee intern = (InternEmployee) employee;
                return intern.effectiveSalary();
            }
            return employee.getSalary();
        }

        String fullProfile() {
            String slotLabel = (slot == null) ? "no parking assigned" : slot.getSlotNo();
            return name + " | Pay: Rs " + effectivePay() + " | Slot: " + slotLabel;
        }
    }

    private static void allotParking(CompanyEmployeeRecord record, ParkingSlot[] slots) {
        ParkingSlot availableSlot = ParkingSlotAllotment.findAvailableSlot(slots);
        if (availableSlot == null) {
            System.out.println("No slots available for " + record.name);
            return;
        }
        availableSlot.allot(record.name);
        record.assignSlot(availableSlot);
    }

    public static void main(String[] args) {
        ParkingSlot[] slots = {
            new ParkingSlot("A1", 4, 3),
            new ParkingSlot("A2", 5, 4)
        };

        ManagerEmployee divyaEmployee = new ManagerEmployee("E101", "Divya", 70000, 8000);
        Employee karanEmployee = new Employee("E100", "Karan", 40000);
        InternEmployee meeraEmployee = new InternEmployee("E102", "Meera", 12000, 10000);

        CompanyEmployeeRecord divya = new CompanyEmployeeRecord("Divya", "E101", divyaEmployee);
        CompanyEmployeeRecord karan = new CompanyEmployeeRecord("Karan", "E100", karanEmployee);
        CompanyEmployeeRecord meera = new CompanyEmployeeRecord("Meera", "E102", meeraEmployee);

        allotParking(divya, slots);
        allotParking(karan, slots);

        for (CompanyEmployeeRecord record : new CompanyEmployeeRecord[] { divya, karan, meera }) {
            System.out.println(record.fullProfile());
        }
        System.out.println("Total records: " + CompanyEmployeeRecord.totalRecords);
    }
}
