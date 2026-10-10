package data_structures.class_problems;

import java.util.LinkedHashMap;
import java.util.Map;

/**
 * Problem 1: Smart Home Platform.
 *
 * Every device has an ID and an on/off state. Extra abilities - dimming,
 * scheduling, energy monitoring - are modelled as separate interfaces rather
 * than as subclasses, so a device advertises only what it can actually do and
 * any unsupported command is rejected instead of silently ignored.
 */
public class SmartHomePlatform {

    interface Dimmable {
        void dim(int level);
    }

    interface Schedulable {
        void schedule(String time);
    }

    interface EnergyMonitored {
        double energyUsedKwh();
    }

    abstract static class Device {

        private final String id;
        private boolean powered;

        Device(String id) {
            this.id = id;
        }

        String getId() {
            return id;
        }

        void turnOn() {
            powered = true;
        }

        void turnOff() {
            powered = false;
        }

        boolean isPowered() {
            return powered;
        }
    }

    static class Light extends Device implements Dimmable, Schedulable {

        Light(String id) {
            super(id);
        }

        @Override
        public void dim(int level) {
            System.out.println(getId() + " dimmed to " + level);
        }

        @Override
        public void schedule(String time) {
            System.out.println(getId() + " scheduled " + time);
        }
    }

    static class Fan extends Device implements Schedulable {

        Fan(String id) {
            super(id);
        }

        @Override
        public void schedule(String time) {
            System.out.println(getId() + " scheduled " + time);
        }
    }

    static class Plug extends Device implements EnergyMonitored {

        private final double kwh;

        Plug(String id, double kwh) {
            super(id);
            this.kwh = kwh;
        }

        @Override
        public double energyUsedKwh() {
            return kwh;
        }
    }

    static class SmartHome {

        private final Map<String, Device> devices = new LinkedHashMap<>();

        void register(Device device) {
            devices.put(device.getId(), device);
        }

        /** Runs one command, rejecting it when the device lacks the capability. */
        void run(String command) {
            String[] parts = command.trim().split("\\s+");
            String action = parts[0].toUpperCase();
            String deviceId = parts[1];

            Device device = devices.get(deviceId);
            if (device == null) {
                System.out.println(deviceId + " rejected: unknown device");
                return;
            }

            switch (action) {
                case "ON" -> {
                    device.turnOn();
                    System.out.println(deviceId + " is ON");
                }
                case "OFF" -> {
                    device.turnOff();
                    System.out.println(deviceId + " is OFF");
                }
                case "DIM" -> {
                    // The instanceof check is the capability test: a Fan is a
                    // Device but was never declared Dimmable, so it lands here.
                    if (device instanceof Dimmable dimmable) {
                        dimmable.dim(Integer.parseInt(parts[2]));
                    } else {
                        reject(deviceId, "DIM");
                    }
                }
                case "SCHEDULE" -> {
                    if (device instanceof Schedulable schedulable) {
                        schedulable.schedule(parts[2]);
                    } else {
                        reject(deviceId, "SCHEDULE");
                    }
                }
                case "ENERGY" -> {
                    if (device instanceof EnergyMonitored monitored) {
                        System.out.println(deviceId + " energy "
                                + trim(monitored.energyUsedKwh()) + " kWh");
                    } else {
                        reject(deviceId, "ENERGY");
                    }
                }
                default -> System.out.println(deviceId + " rejected: unknown command");
            }
        }

        private void reject(String deviceId, String capability) {
            System.out.println(deviceId + " rejected: " + capability + " unsupported");
        }

        private String trim(double value) {
            return value == Math.floor(value) ? String.valueOf((long) value)
                                              : String.valueOf(value);
        }
    }

    public static void main(String[] args) {
        SmartHome home = new SmartHome();
        home.register(new Light("L1"));
        home.register(new Fan("F1"));
        home.register(new Plug("P1", 12));

        String[] commands = {
            "ON L1",
            "DIM L1 40",
            "DIM F1 30",
            "SCHEDULE F1 22:00",
            "ENERGY P1",
            "ENERGY L1"
        };
        for (String command : commands) {
            home.run(command);
        }
    }
}
