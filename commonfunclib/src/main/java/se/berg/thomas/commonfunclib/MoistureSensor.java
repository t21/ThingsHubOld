package se.berg.thomas.commonfunclib;

import java.util.Locale;

/**
 * Created by thomas on 2017-09-03.
 */

public class MoistureSensor extends Sensor {

    private int moistureLevel;

    MoistureSensor(int rawSensorValue) {
        sensorName = "moisture";
        sensorUnit = "";
        moistureLevel = rawSensorValue;
    }


    @Override
    String getSensorName() {
        return sensorName;
    }

    @Override
    String getSensorValue() {
        return String.format(Locale.getDefault(),"%d%s", moistureLevel, sensorUnit);
    }

}
