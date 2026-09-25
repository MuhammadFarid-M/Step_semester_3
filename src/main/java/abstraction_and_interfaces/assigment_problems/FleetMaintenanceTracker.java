package abstraction_and_interfaces.assigment_problems;

public class FleetMaintenanceTracker {

    interface Insurable {
        String getInsuranceInfo();
    }

    abstract static class ServiceableVehicle {

        private double mileage;

        public ServiceableVehicle() {
            this.mileage = 0.0;
        }

        public abstract String performMaintenance();

        public double getMileage() {
            return mileage;
        }

        public void addMileage(double km) {
            if (km < 0) {
                System.out.println("addMileage(" + km + ") rejected, mileage unchanged");
                return;
            }
            mileage += km;
        }
    }

    static class Forklift extends ServiceableVehicle implements Insurable {

        private final String assetTag;

        public Forklift(String assetTag) {
            this.assetTag = assetTag;
        }

        public String getAssetTag() {
            return assetTag;
        }

        @Override
        public String performMaintenance() {
            return "Forklift " + assetTag + ": hydraulic and fork inspection complete";
        }

        @Override
        public String getInsuranceInfo() {
            return "Insured under fleet policy - Asset " + assetTag;
        }
    }

    static class HeavyDutyForklift extends Forklift {

        public HeavyDutyForklift(String assetTag) {
            super(assetTag);
        }

        @Override
        public String performMaintenance() {
            return super.performMaintenance() + " | high-pressure hydraulic check complete";
        }
    }

    static class Bulldozer extends ServiceableVehicle {

        private final String assetTag;

        public Bulldozer(String assetTag) {
            this.assetTag = assetTag;
        }

        @Override
        public String performMaintenance() {
            return "Bulldozer " + assetTag + ": track and blade inspection complete";
        }
    }

    static String getInsuranceIfApplicable(ServiceableVehicle vehicle) {
        if (vehicle instanceof Insurable) {
            Insurable insurable = (Insurable) vehicle;
            return insurable.getInsuranceInfo();
        }
        return "No insurance record exists";
    }

    public static void main(String[] args) {
        Forklift forklift = new Forklift("FL-22");
        forklift.addMileage(120);
        System.out.println("f.getMileage() -> " + forklift.getMileage());
        System.out.println("f.performMaintenance() -> " + forklift.performMaintenance());

        System.out.println();
        HeavyDutyForklift heavyDuty = new HeavyDutyForklift("HD-9");
        System.out.println("hd.performMaintenance() -> " + heavyDuty.performMaintenance());

        System.out.println();
        forklift.addMileage(-50);
        System.out.println("mileage after the rejected add -> " + forklift.getMileage());

        System.out.println();
        System.out.println("getInsuranceIfApplicable(f)  -> " + getInsuranceIfApplicable(forklift));
        System.out.println("getInsuranceIfApplicable(hd) -> " + getInsuranceIfApplicable(heavyDuty));
        Bulldozer bulldozer = new Bulldozer("BD-4");
        System.out.println("getInsuranceIfApplicable(bd) -> " + getInsuranceIfApplicable(bulldozer));
    }
}
