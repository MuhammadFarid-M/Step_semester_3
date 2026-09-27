package oop_design_and_uml.class_problems;

public class VehicleRentalSystem {

    abstract static class Vehicle {

        private final String vehicleName;
        private boolean available;

        public Vehicle(String vehicleName) {
            this.vehicleName = vehicleName;
            this.available = true;
        }

        public abstract double calculateCharge(int days);

        public String getVehicleName() {
            return vehicleName;
        }

        public boolean isAvailable() {
            return available;
        }

        void markRented() {
            this.available = false;
        }

        void markAvailable() {
            this.available = true;
        }
    }

    static class StandardCar extends Vehicle {

        private static final double DAILY_RATE = 50.0;

        public StandardCar(String vehicleName) {
            super(vehicleName);
        }

        @Override
        public double calculateCharge(int days) {
            return DAILY_RATE * days;
        }
    }

    static class LuxuryCar extends Vehicle {

        private static final double DAILY_RATE = 100.0;

        public LuxuryCar(String vehicleName) {
            super(vehicleName);
        }

        @Override
        public double calculateCharge(int days) {
            return DAILY_RATE * days;
        }
    }

    static class SuvCar extends Vehicle {

        private static final double DAILY_RATE = 75.0;
        private static final double WEEKLY_DISCOUNT = 0.9;

        public SuvCar(String vehicleName) {
            super(vehicleName);
        }

        @Override
        public double calculateCharge(int days) {
            double charge = DAILY_RATE * days;
            return days >= 7 ? charge * WEEKLY_DISCOUNT : charge;
        }
    }

    static class Rental {

        private final Vehicle vehicle;
        private final String customerName;
        private final int days;
        private final double totalCharge;
        private boolean active;

        Rental(Vehicle vehicle, String customerName, int days) {
            this.vehicle = vehicle;
            this.customerName = customerName;
            this.days = days;
            this.totalCharge = vehicle.calculateCharge(days);
            this.active = true;
        }

        public Vehicle getVehicle() {
            return vehicle;
        }

        public String getCustomerName() {
            return customerName;
        }

        public int getDays() {
            return days;
        }

        public double getTotalCharge() {
            return totalCharge;
        }

        public boolean isActive() {
            return active;
        }

        void close() {
            this.active = false;
        }
    }

    static class RentalService {

        private static final int MAX_RENTALS = 100;

        private final Rental[] rentals = new Rental[MAX_RENTALS];
        private int rentalCount;

        public Rental rent(Vehicle vehicle, String customerName, int days) {
            if (days <= 0) {
                System.out.println("Rental failed: duration must be at least one day.");
                return null;
            }
            if (!vehicle.isAvailable()) {
                System.out.println("Rental failed: " + vehicle.getVehicleName()
                        + " already has an active rental.");
                return null;
            }
            if (rentalCount >= MAX_RENTALS) {
                System.out.println("Rental failed: rental register is full.");
                return null;
            }

            Rental rental = new Rental(vehicle, customerName, days);
            vehicle.markRented();
            rentals[rentalCount] = rental;
            rentalCount++;
            System.out.println(vehicle.getVehicleName() + " rented for " + days
                    + " days. Total charge: " + String.format("$%.2f", rental.getTotalCharge()));
            return rental;
        }

        public void returnVehicle(Vehicle vehicle) {
            for (int index = 0; index < rentalCount; index++) {
                Rental rental = rentals[index];
                if (rental.isActive() && rental.getVehicle() == vehicle) {
                    rental.close();
                    vehicle.markAvailable();
                    System.out.println(vehicle.getVehicleName() + " returned. Now available.");
                    return;
                }
            }
            System.out.println("Return failed: no active rental found for " + vehicle.getVehicleName() + ".");
        }

        public double getTotalRevenue() {
            double revenue = 0.0;
            for (int index = 0; index < rentalCount; index++) {
                revenue += rentals[index].getTotalCharge();
            }
            return revenue;
        }
    }

    public static void main(String[] args) {
        RentalService rentalService = new RentalService();
        Vehicle luxuryCar = new LuxuryCar("Luxury Car A");
        Vehicle standardCar = new StandardCar("Standard Car B");

        rentalService.rent(luxuryCar, "Customer", 3);
        rentalService.rent(standardCar, "Customer", 5);
        rentalService.returnVehicle(luxuryCar);

        System.out.println();
        rentalService.rent(standardCar, "Another Customer", 2);
        rentalService.returnVehicle(luxuryCar);

        System.out.println();
        Vehicle suv = new SuvCar("SUV Car C");
        rentalService.rent(suv, "Customer", 7);
        System.out.println("A new category plugged in without touching the rental workflow.");
        System.out.println("Total revenue: " + String.format("$%.2f", rentalService.getTotalRevenue()));
    }
}
