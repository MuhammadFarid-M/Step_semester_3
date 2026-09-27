package oop_design_and_uml.class_problems;

import java.time.LocalDate;
import java.time.temporal.ChronoUnit;

public class HotelBookingSystem {

    abstract static class Room {

        private final String roomName;

        public Room(String roomName) {
            this.roomName = roomName;
        }

        public abstract double getNightlyRate();

        public abstract String getCategory();

        public String getRoomName() {
            return roomName;
        }

        public double calculatePrice(LocalDate checkIn, LocalDate checkOut) {
            long nights = ChronoUnit.DAYS.between(checkIn, checkOut);
            return nights * getNightlyRate();
        }
    }

    static class StandardRoom extends Room {

        public StandardRoom(String roomName) {
            super(roomName);
        }

        @Override
        public double getNightlyRate() {
            return 150.0;
        }

        @Override
        public String getCategory() {
            return "Standard";
        }
    }

    static class DeluxeRoom extends Room {

        public DeluxeRoom(String roomName) {
            super(roomName);
        }

        @Override
        public double getNightlyRate() {
            return 200.0;
        }

        @Override
        public String getCategory() {
            return "Deluxe";
        }
    }

    static class SuiteRoom extends Room {

        public SuiteRoom(String roomName) {
            super(roomName);
        }

        @Override
        public double getNightlyRate() {
            return 350.0;
        }

        @Override
        public String getCategory() {
            return "Suite";
        }
    }

    static class Customer {

        private final String customerName;

        public Customer(String customerName) {
            this.customerName = customerName;
        }

        public String getCustomerName() {
            return customerName;
        }
    }

    enum ReservationStatus {
        ACTIVE, CANCELLED
    }

    static class Reservation {

        private static final int CANCELLATION_WINDOW_DAYS = 2;

        private final Room room;
        private final Customer customer;
        private final LocalDate checkIn;
        private final LocalDate checkOut;
        private final double totalPrice;
        private ReservationStatus status;

        Reservation(Room room, Customer customer, LocalDate checkIn, LocalDate checkOut) {
            this.room = room;
            this.customer = customer;
            this.checkIn = checkIn;
            this.checkOut = checkOut;
            this.totalPrice = room.calculatePrice(checkIn, checkOut);
            this.status = ReservationStatus.ACTIVE;
        }

        public Room getRoom() {
            return room;
        }

        public Customer getCustomer() {
            return customer;
        }

        public LocalDate getCheckIn() {
            return checkIn;
        }

        public LocalDate getCheckOut() {
            return checkOut;
        }

        public double getTotalPrice() {
            return totalPrice;
        }

        public ReservationStatus getStatus() {
            return status;
        }

        public LocalDate getCancellationDeadline() {
            return checkIn.minusDays(CANCELLATION_WINDOW_DAYS);
        }

        public boolean overlaps(LocalDate otherCheckIn, LocalDate otherCheckOut) {
            return status == ReservationStatus.ACTIVE
                    && otherCheckIn.isBefore(checkOut)
                    && checkIn.isBefore(otherCheckOut);
        }

        void cancel() {
            this.status = ReservationStatus.CANCELLED;
        }
    }

    static class BookingManager {

        private static final int MAX_RESERVATIONS = 200;

        private final Reservation[] reservations = new Reservation[MAX_RESERVATIONS];
        private int reservationCount;

        public boolean isAvailable(Room room, LocalDate checkIn, LocalDate checkOut) {
            for (int index = 0; index < reservationCount; index++) {
                Reservation reservation = reservations[index];
                if (reservation.getRoom() == room && reservation.overlaps(checkIn, checkOut)) {
                    return false;
                }
            }
            return true;
        }

        public Reservation book(Room room, Customer customer, LocalDate checkIn, LocalDate checkOut) {
            if (!checkIn.isBefore(checkOut)) {
                System.out.println("Booking failed: check-out must be after check-in.");
                return null;
            }
            if (!isAvailable(room, checkIn, checkOut)) {
                System.out.println("Booking failed: " + room.getRoomName()
                        + " is not available for " + checkIn + " to " + checkOut + ".");
                return null;
            }
            if (reservationCount >= MAX_RESERVATIONS) {
                System.out.println("Booking failed: reservation register is full.");
                return null;
            }

            Reservation reservation = new Reservation(room, customer, checkIn, checkOut);
            reservations[reservationCount] = reservation;
            reservationCount++;
            System.out.println(room.getRoomName() + " booked from " + checkIn + " to " + checkOut
                    + ". Total price: " + String.format("$%.2f", reservation.getTotalPrice()));
            return reservation;
        }

        public void cancel(Reservation reservation, LocalDate requestDate) {
            if (reservation == null) {
                System.out.println("Cancellation failed: no such reservation.");
                return;
            }
            if (reservation.getStatus() == ReservationStatus.CANCELLED) {
                System.out.println("Cancellation failed: reservation for "
                        + reservation.getRoom().getRoomName() + " is already cancelled.");
                return;
            }
            if (requestDate.isAfter(reservation.getCancellationDeadline())) {
                System.out.println("Cancellation failed: deadline for "
                        + reservation.getRoom().getRoomName() + " was "
                        + reservation.getCancellationDeadline() + ".");
                return;
            }
            reservation.cancel();
            System.out.println("Reservation for " + reservation.getRoom().getRoomName()
                    + " cancelled successfully.");
        }
    }

    public static void main(String[] args) {
        BookingManager bookingManager = new BookingManager();
        Room deluxeRoom = new DeluxeRoom("Deluxe Room 101");
        Room standardRoom = new StandardRoom("Standard Room 205");
        Customer customer = new Customer("Customer");

        Reservation deluxeReservation = bookingManager.book(deluxeRoom, customer,
                LocalDate.of(2024, 12, 1), LocalDate.of(2024, 12, 5));
        bookingManager.book(standardRoom, customer,
                LocalDate.of(2024, 12, 3), LocalDate.of(2024, 12, 7));
        bookingManager.book(deluxeRoom, customer,
                LocalDate.of(2024, 12, 3), LocalDate.of(2024, 12, 7));
        bookingManager.cancel(deluxeReservation, LocalDate.of(2024, 11, 20));

        System.out.println();
        Reservation lateCancel = bookingManager.book(standardRoom, customer,
                LocalDate.of(2025, 1, 10), LocalDate.of(2025, 1, 12));
        bookingManager.cancel(lateCancel, LocalDate.of(2025, 1, 9));

        System.out.println();
        bookingManager.book(deluxeRoom, customer,
                LocalDate.of(2024, 12, 3), LocalDate.of(2024, 12, 7));
        System.out.println("The overlapping slot frees up once the earlier reservation is cancelled.");

        System.out.println();
        Room suite = new SuiteRoom("Suite 900");
        bookingManager.book(suite, customer, LocalDate.of(2024, 12, 1), LocalDate.of(2024, 12, 3));
        System.out.println("A new category plugged in without touching the booking workflow.");
    }
}
