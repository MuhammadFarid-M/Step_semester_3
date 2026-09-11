package oops.class_problems;

class SrmStudent {

    private String name;
    private String regNo;
    private int attendance;

    SrmStudent(String name, String regNo, int attendance) {
        this.name = name;
        this.regNo = regNo;
        this.attendance = attendance;
    }

    String getName() {
        return name;
    }

    int getAttendance() {
        return attendance;
    }

    void addAttendanceUpdate(int newAttendance) {
        if (newAttendance < 0 || newAttendance > 100) {
            System.out.println("Rejected attendance update for " + name + ": " + newAttendance);
            return;
        }
        this.attendance = newAttendance;
    }

    boolean isEligible() {
        return attendance >= 75;
    }

    // classAverage is static because an average belongs to the whole group of students rather than
    // to any one of them: it is meaningless without the array, and there is no single student it
    // naturally lives on. isEligible is an instance method because it answers a question about one
    // particular student, using only that student's own attendance field.
    static double classAverage(SrmStudent[] students) {
        if (students == null || students.length == 0) {
            return 0.0;
        }
        int totalAttendance = 0;
        for (SrmStudent student : students) {
            totalAttendance += student.attendance;
        }
        return (double) totalAttendance / students.length;
    }
}

public class AttendanceSystem {

    private static void printRegister(SrmStudent[] students) {
        for (SrmStudent student : students) {
            String status = student.isEligible() ? "Eligible" : "Detained";
            System.out.println(student.getName() + " - " + student.getAttendance() + "% - " + status);
        }
        System.out.println("Class average: " + SrmStudent.classAverage(students) + "%");
    }

    public static void main(String[] args) {
        SrmStudent[] students = {
            new SrmStudent("Ravi", "RA2311003010101", 82),
            new SrmStudent("Anitha", "RA2311003010102", 68),
            new SrmStudent("Karthik", "RA2311003010103", 91),
            new SrmStudent("Meera", "RA2311003010104", 74),
            new SrmStudent("Suresh", "RA2311003010105", 60)
        };

        printRegister(students);

        System.out.println();
        System.out.println("After re-check (Meera updated to 78):");
        students[3].addAttendanceUpdate(78);
        printRegister(students);
    }
}
