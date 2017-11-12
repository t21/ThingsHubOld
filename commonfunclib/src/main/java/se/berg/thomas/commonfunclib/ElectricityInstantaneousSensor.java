package se.berg.thomas.commonfunclib;

import java.util.Locale;

/**
 * Created by thomas on 2017-09-10.
 */

public class ElectricityInstantaneousSensor extends Sensor {

    private int instantaneousPower;

    ElectricityInstantaneousSensor(int rawSensorValue) {
        sensorName = "instantaneous power";
        sensorUnit = "W";
        instantaneousPower = rawSensorValue;
    }

    @Override
    String getSensorName() {
        return sensorName;
    }

    @Override
    String getSensorValue() {
        return String.format(Locale.getDefault(),"%d %s", instantaneousPower, sensorUnit);
    }

}
