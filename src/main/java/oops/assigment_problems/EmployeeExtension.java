package oops.assigment_problems;

public class EmployeeExtension {

    static class Employee {

        private String empId;
        private String empName;
        private double salary;

        Employee(String empId, String empName, double salary) {
            this.empId = empId;
            this.empName = empName;
            this.salary = salary;
        }

        String getEmpId() {
            return empId;
        }

        String getEmpName() {
            return empName;
        }

        double getSalary() {
            return salary;
        }
    }

    static class ManagerEmployee extends Employee {

        private double teamBonus;

        ManagerEmployee(String empId, String empName, double salary, double teamBonus) {
            super(empId, empName, salary);
            this.teamBonus = teamBonus < 0 ? 0 : teamBonus;
        }

        double effectiveSalary() {
            return getSalary() + teamBonus;
        }
    }

    static class InternEmployee extends Employee {

        private double stipendCap;

        InternEmployee(String empId, String empName, double salary, double stipendCap) {
            super(empId, empName, salary);
            this.stipendCap = stipendCap < 0 ? 0 : stipendCap;
        }

        double effectiveSalary() {
            return getSalary() < stipendCap ? getSalary() : stipendCap;
        }
    }

    public static void main(String[] args) {
        Employee plainEmployee = new Employee("E100", "Karan", 40000);
        ManagerEmployee manager = new ManagerEmployee("E101", "Divya", 70000, 8000);
        InternEmployee intern = new InternEmployee("E102", "Meera", 12000, 10000);

        Employee[] employees = { plainEmployee, manager, intern };
        for (Employee employee : employees) {
            if (employee instanceof ManagerEmployee) {
                ManagerEmployee managerEmployee = (ManagerEmployee) employee;
                System.out.println("Manager effective pay: Rs " + managerEmployee.effectiveSalary());
            } else if (employee instanceof InternEmployee) {
                InternEmployee internEmployee = (InternEmployee) employee;
                System.out.println("Intern effective pay: Rs " + internEmployee.effectiveSalary());
            } else {
                System.out.println("Plain employee pay: Rs " + employee.getSalary());
            }
        }

        System.out.println();
        for (Employee employee : employees) {
            System.out.println(employee.getEmpId() + " - " + employee.getEmpName());
        }
    }
}
