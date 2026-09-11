package oops.class_problems;

import oops.class_problems.FeeAccountExtension.HostelFeeAccount;
import oops.class_problems.HostelRoomAllotment.HostelRoom;

public class FeeAndHostelMiniSystem {

    static class SrmStudent {

        static int totalStudents = 0;

        String name;
        String regNo;
        HostelFeeAccount feeAccount;
        HostelRoom room;

        SrmStudent(String name, String regNo, HostelFeeAccount feeAccount) {
            totalStudents++;
            this.name = name;
            this.regNo = regNo;
            this.feeAccount = feeAccount;
            this.room = null;
        }

        void assignRoom(HostelRoom room) {
            this.room = room;
        }

        String fullStatus() {
            String roomLabel = (room == null) ? "unallotted" : room.getRoomNo();
            return name + " | Due: Rs " + feeAccount.getDue() + " | Room: " + roomLabel;
        }
    }

    private static void allotRoom(SrmStudent student, HostelRoom[] rooms) {
        HostelRoom availableRoom = HostelRoomAllotment.findAvailableRoom(rooms);
        if (availableRoom == null) {
            System.out.println("No rooms available for " + student.name);
            return;
        }
        availableRoom.allot(student.name);
        student.assignRoom(availableRoom);
    }

    public static void main(String[] args) {
        HostelRoom[] rooms = {
            new HostelRoom("C-214", 3, 2),
            new HostelRoom("C-507", 2, 1)
        };

        HostelFeeAccount raviAccount = new HostelFeeAccount("RA2311003010101", 200000);
        raviAccount.payInTwoInstallments(60000);

        HostelFeeAccount anithaAccount = new HostelFeeAccount("RA2311003010102", 180000);
        anithaAccount.pay(-5000);

        HostelFeeAccount karthikAccount = new HostelFeeAccount("RA2311003010103", 200000);

        SrmStudent ravi = new SrmStudent("Ravi", "RA2311003010101", raviAccount);
        SrmStudent anitha = new SrmStudent("Anitha", "RA2311003010102", anithaAccount);
        SrmStudent karthik = new SrmStudent("Karthik", "RA2311003010103", karthikAccount);

        allotRoom(ravi, rooms);
        allotRoom(anitha, rooms);

        System.out.println();
        SrmStudent[] students = { ravi, anitha, karthik };
        for (SrmStudent student : students) {
            System.out.println(student.fullStatus());
        }
        System.out.println("Total students: " + SrmStudent.totalStudents);
    }
}
