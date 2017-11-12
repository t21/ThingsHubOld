package se.berg.thomas.commonfunclib;

import java.util.Locale;

/**
 * Created by thomas on 2017-09-03.
 */

public class CO2Sensor extends Sensor {

    private int co2Level;

    CO2Sensor(int rawSensorValue) {
        sensorName = "CO2 level";
        sensorUnit = "ppm";
        co2Level = rawSensorValue;
    }

    @Override
    String getSensorName() {
        return sensorName;
    }

    @Override
    String getSensorValue() {
        return String.format(Locale.getDefault(),"%d %s", co2Level, sensorUnit);
    }

}
