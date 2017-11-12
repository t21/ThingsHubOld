package se.berg.thomas.commonfunclib;

import java.util.Locale;

/**
 * Created by thomas on 2017-09-10.
 */

public class ElectricityAccumulatedSensor extends Sensor {

    private int accumalatedPower;

    ElectricityAccumulatedSensor(int rawSensorValue) {
        sensorName = "accumulated power";
        sensorUnit = "W";
        accumalatedPower = rawSensorValue;
    }

    @Override
    String getSensorName() {
        return sensorName;
    }

    @Override
    String getSensorValue() {
        return String.format(Locale.getDefault(),"%d %s", accumalatedPower, sensorUnit);
    }

}
