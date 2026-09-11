package oops.class_problems;

class BrokenSrmStudent {

    // Every field below is static, which is exactly the bug being reproduced:
    // name    - a name identifies one person, but a static field gives the whole class a single
    //           shared slot, so creating the second student silently overwrites the first one's name.
    // regNo   - a registration number must be unique per student; as a static field there is only
    //           one for every object ever created, so all students report the same regNo.
    // attendance - attendance is measured separately for each student; making it static merges
    //           everyone's percentage into one shared counter and no individual record survives.
    static String name;
    static String regNo;
    static int attendance;

    BrokenSrmStudent(String studentName, String studentRegNo, int studentAttendance) {
        name = studentName;
        regNo = studentRegNo;
        attendance = studentAttendance;
    }

    String getName() {
        return name;
    }
}

class FixedSrmStudent {

    static String university = "SRM";
    static int admissionCount = 0;

    private String name;
    private String regNo;
    private int attendance;

    FixedSrmStudent(String name, int attendance) {
        admissionCount++;
        this.name = name;
        this.regNo = "RA23110030101" + admissionCount;
        this.attendance = attendance;
    }

    int getAttendance() {
        return attendance;
    }

    void printIdCard() {
        System.out.println(name + " | " + regNo);
    }

    static void printTotalAdmissions() {
        System.out.println("Students admitted so far: " + admissionCount);
    }
}

public class InstanceStaticBoundary {

    public static void main(String[] args) {
        System.out.println("Broken version:");
        BrokenSrmStudent firstBroken = new BrokenSrmStudent("Ravi", "RA231100301011", 82);
        BrokenSrmStudent secondBroken = new BrokenSrmStudent("Meera", "RA231100301012", 74);
        System.out.println(firstBroken.getName());
        System.out.println(secondBroken.getName());

        System.out.println();
        System.out.println("Fixed version:");
        FixedSrmStudent ravi = new FixedSrmStudent("Ravi", 82);
        FixedSrmStudent meera = new FixedSrmStudent("Meera", 74);
        ravi.printIdCard();
        meera.printIdCard();
        FixedSrmStudent.printTotalAdmissions();
        System.out.println("Ravi attendance: " + ravi.getAttendance()
                + " | Meera attendance: " + meera.getAttendance());
    }
}
