package abstraction_and_interfaces.assigment_problems;

public class ConnectedHomeControlPanel {

    interface RemoteControllable {
        String connect(String appId);
    }

    interface EnergyTrackable {
        double getConsumptionWatts();
    }

    abstract static class HomeDevice {

        private static final int SERIAL_BASE = 1000;
        private static int devicesCreated = 0;

        private final String serialNumber;

        public HomeDevice() {
            devicesCreated++;
            this.serialNumber = "HD-" + (SERIAL_BASE + devicesCreated);
        }

        public abstract String activate();

        public String getSerialNumber() {
            return serialNumber;
        }
    }

    static class WashingMachine extends HomeDevice implements RemoteControllable, EnergyTrackable {

        private final double consumptionWatts;

        public WashingMachine(double consumptionWatts) {
            this.consumptionWatts = consumptionWatts;
        }

        @Override
        public String activate() {
            return "Washing machine " + getSerialNumber() + " started a cycle";
        }

        @Override
        public String connect(String appId) {
            return getSerialNumber() + " connected to " + appId;
        }

        @Override
        public double getConsumptionWatts() {
            return consumptionWatts;
        }
    }

    static class Refrigerator extends HomeDevice implements EnergyTrackable {

        private final double consumptionWatts;

        public Refrigerator(double consumptionWatts) {
            this.consumptionWatts = consumptionWatts;
        }

        @Override
        public String activate() {
            return "Refrigerator " + getSerialNumber() + " started cooling";
        }

        @Override
        public double getConsumptionWatts() {
            return consumptionWatts;
        }
    }

    static class MobileApp implements RemoteControllable {

        private final String appName;

        public MobileApp(String appName) {
            this.appName = appName;
        }

        @Override
        public String connect(String appId) {
            return appName + " connected to " + appId;
        }
    }

    static void connectAll(RemoteControllable[] items, String appId) {
        if (items == null) {
            return;
        }
        for (RemoteControllable item : items) {
            if (item != null) {
                System.out.println(item.connect(appId));
            }
        }
    }

    static double getConsumptionIfTrackable(HomeDevice device) {
        if (device instanceof EnergyTrackable) {
            EnergyTrackable trackable = (EnergyTrackable) device;
            return trackable.getConsumptionWatts();
        }
        System.out.println("No energy tracking on " + device.getSerialNumber());
        return 0.0;
    }

    public static void main(String[] args) {
        WashingMachine washingMachine = new WashingMachine(500.0);
        System.out.println("wm.activate()             -> " + washingMachine.activate());
        System.out.println("wm.connect(\"HomeConnect\") -> " + washingMachine.connect("HomeConnect"));

        System.out.println();
        Refrigerator refrigerator = new Refrigerator(150.0);
        System.out.println("fridge.activate() -> " + refrigerator.activate());
        System.out.println("getConsumptionIfTrackable(fridge) -> "
                + getConsumptionIfTrackable(refrigerator));

        System.out.println();
        MobileApp mobileApp = new MobileApp("HomeConnect App");
        System.out.println("app.connect(\"HomeConnect\") -> " + mobileApp.connect("HomeConnect"));

        System.out.println();
        // Upcasting: a WashingMachine object stored in a HomeDevice-typed variable.
        HomeDevice reference = washingMachine;
        System.out.println("getConsumptionIfTrackable(ref) -> " + getConsumptionIfTrackable(reference));

        System.out.println();
        System.out.println("connectAll over a device and an app that is not a device:");
        connectAll(new RemoteControllable[] { washingMachine, mobileApp }, "HomeConnect");
    }
}
