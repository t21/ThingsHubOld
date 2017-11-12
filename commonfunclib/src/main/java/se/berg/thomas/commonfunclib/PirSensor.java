package se.berg.thomas.commonfunclib;

/**
 * Created by thomas on 2017-09-03.
 */

public class PirSensor extends Sensor {

    private boolean presence;

    PirSensor(int rawSensorValue) {
        sensorName = "presence";
        sensorUnit = "";
        if (rawSensorValue == 0) {
            presence = false;
        } else {
            presence = true;
        }
    }

    @Override
    String getSensorName() {
        return sensorName;
    }

    @Override
    String getSensorValue() {
        if (presence) {
            return "true";
        } else {
            return "false";
        }
    }

}
