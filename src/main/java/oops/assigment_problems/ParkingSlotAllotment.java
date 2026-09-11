package oops.assigment_problems;

public class ParkingSlotAllotment {

    static class ParkingSlot {

        private String slotNo;
        private int capacity;
        private int occupiedCount;

        ParkingSlot(String slotNo, int capacity, int occupiedCount) {
            this.slotNo = slotNo;
            this.capacity = capacity;
            this.occupiedCount = occupiedCount;
        }

        String getSlotNo() {
            return slotNo;
        }

        int getCapacity() {
            return capacity;
        }

        int getOccupiedCount() {
            return occupiedCount;
        }

        boolean allot(String vehicleNo) {
            if (vehicleNo == null || vehicleNo.trim().isEmpty() || occupiedCount >= capacity) {
                return false;
            }
            occupiedCount++;
            return true;
        }
    }

    static ParkingSlot findAvailableSlot(ParkingSlot[] slots) {
        if (slots == null) {
            return null;
        }
        for (ParkingSlot slot : slots) {
            if (slot != null && slot.getOccupiedCount() < slot.getCapacity()) {
                return slot;
            }
        }
        return null;
    }

    // A ParkingSlot[] stores references to objects rather than the objects themselves, and Java
    // passes that array reference by value. Nothing is duplicated when the array is handed over:
    // findAvailableSlot returns a reference to the same object main created, so the allot() call
    // below raises the occupied count on the original slot instead of on a throwaway copy.
    static void safeAllot(ParkingSlot[] slots, String vehicleNo) {
        ParkingSlot availableSlot = findAvailableSlot(slots);
        if (availableSlot == null) {
            System.out.println("No slots available for " + vehicleNo);
            return;
        }
        if (availableSlot.allot(vehicleNo)) {
            System.out.println(vehicleNo + " allotted to slot " + availableSlot.getSlotNo());
        } else {
            System.out.println("No slots available for " + vehicleNo);
        }
    }

    public static void main(String[] args) {
        ParkingSlot[] slotsWithSpace = {
            new ParkingSlot("A1", 4, 3),
            new ParkingSlot("A2", 5, 5)
        };
        safeAllot(slotsWithSpace, "TN09AB1234");

        ParkingSlot[] fullSlots = {
            new ParkingSlot("A1", 4, 4),
            new ParkingSlot("A2", 5, 5)
        };
        safeAllot(fullSlots, "TN09AB1234");

        safeAllot(null, "TN09AB1234");
    }
}
