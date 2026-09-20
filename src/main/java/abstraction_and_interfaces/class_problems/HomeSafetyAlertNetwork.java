package abstraction_and_interfaces.class_problems;

public class HomeSafetyAlertNetwork {

    interface Alertable {
        String sendAlert(String message);
    }

    static class SecuritySensor {

        private final String zoneName;

        public SecuritySensor(String zoneName) {
            this.zoneName = zoneName;
        }

        public String getZoneName() {
            return zoneName;
        }
    }

    static class MotionSensor extends SecuritySensor implements Alertable {

        public MotionSensor(String zoneName) {
            super(zoneName);
        }

        @Override
        public String sendAlert(String message) {
            return "[" + getZoneName() + "] " + message;
        }
    }

    static class DualZoneMotionSensor extends MotionSensor {

        private final String secondZoneName;

        public DualZoneMotionSensor(String zoneName, String secondZoneName) {
            super(zoneName);
            this.secondZoneName = secondZoneName;
        }

        @Override
        public String sendAlert(String message) {
            return super.sendAlert(message) + " [also covering " + secondZoneName + "]";
        }
    }

    static class SmokeDetector implements Alertable {

        private final String deviceId;

        public SmokeDetector(String deviceId) {
            this.deviceId = deviceId;
        }

        public String getDeviceId() {
            return deviceId;
        }

        @Override
        public String sendAlert(String message) {
            return "[" + deviceId + "] " + message;
        }
    }

    static void broadcastAll(Alertable[] devices, String message) {
        if (devices == null) {
            return;
        }
        for (Alertable device : devices) {
            if (device != null) {
                System.out.println(device.sendAlert(message));
            }
        }
    }

    static String getZoneIfMotionSensor(Alertable device) {
        if (device instanceof MotionSensor) {
            MotionSensor motionSensor = (MotionSensor) device;
            return motionSensor.getZoneName();
        }
        return "Not a motion sensor";
    }

    public static void main(String[] args) {
        MotionSensor motionSensor = new MotionSensor("Living Room");
        DualZoneMotionSensor dualZoneSensor = new DualZoneMotionSensor("Hallway", "Stairwell");
        SmokeDetector smokeDetector = new SmokeDetector("SD-01");

        System.out.println(motionSensor.sendAlert("Motion detected"));
        System.out.println(dualZoneSensor.sendAlert("Motion detected"));
        System.out.println(smokeDetector.sendAlert("Smoke detected"));

        System.out.println();
        System.out.println("broadcastAll over a mixed array:");
        broadcastAll(new Alertable[] { motionSensor, dualZoneSensor, smokeDetector }, "Alarm test");

        System.out.println();
        System.out.println("getZoneIfMotionSensor(m) -> " + getZoneIfMotionSensor(motionSensor));
        System.out.println("getZoneIfMotionSensor(s) -> " + getZoneIfMotionSensor(smokeDetector));
        System.out.println("SmokeDetector " + smokeDetector.getDeviceId()
                + " shares no ancestry with SecuritySensor, only the Alertable contract.");
    }
}
