package oops.class_problems;

public class HostelRoomAllotment {

    static class HostelRoom {

        private String roomNo;
        private int beds;
        private int occupied;

        HostelRoom(String roomNo, int beds, int occupied) {
            this.roomNo = roomNo;
            this.beds = beds;
            this.occupied = occupied;
        }

        String getRoomNo() {
            return roomNo;
        }

        int getBeds() {
            return beds;
        }

        int getOccupied() {
            return occupied;
        }

        boolean allot(String name) {
            if (name == null || name.trim().isEmpty() || occupied >= beds) {
                return false;
            }
            occupied++;
            return true;
        }
    }

    static HostelRoom findAvailableRoom(HostelRoom[] rooms) {
        if (rooms == null) {
            return null;
        }
        for (HostelRoom room : rooms) {
            if (room != null && room.getOccupied() < room.getBeds()) {
                return room;
            }
        }
        return null;
    }

    // A HostelRoom[] holds references to objects, not the objects themselves, and Java passes that
    // array reference by value. So no room is copied when the array is handed to these methods:
    // findAvailableRoom returns a reference to the very same HostelRoom object main created, and
    // the allot() call below increments the occupied count on that original room, not on a copy.
    static void safeAllot(HostelRoom[] rooms, String studentName) {
        HostelRoom availableRoom = findAvailableRoom(rooms);
        if (availableRoom == null) {
            System.out.println("No rooms available for " + studentName);
            return;
        }
        if (availableRoom.allot(studentName)) {
            System.out.println(studentName + " allotted to room " + availableRoom.getRoomNo());
        } else {
            System.out.println("No rooms available for " + studentName);
        }
    }

    public static void main(String[] args) {
        HostelRoom[] roomsWithSpace = {
            new HostelRoom("C-214", 3, 2),
            new HostelRoom("C-507", 2, 2)
        };
        safeAllot(roomsWithSpace, "Divya");

        HostelRoom[] fullRooms = {
            new HostelRoom("C-214", 3, 3),
            new HostelRoom("C-507", 2, 2)
        };
        safeAllot(fullRooms, "Divya");

        safeAllot(null, "Divya");
    }
}
