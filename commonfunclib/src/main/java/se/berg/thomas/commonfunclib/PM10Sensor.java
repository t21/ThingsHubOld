package se.berg.thomas.commonfunclib;

import java.util.Locale;

/**
 * Created by thomas on 2017-09-10.
 */

public class PM10Sensor extends Sensor {

    private double pm10;

    PM10Sensor(int rawSensorValue) {
        sensorName = "particle10";
        sensorUnit = "?";
        pm10 = rawSensorValue / 10.0;
    }

    @Override
    String getSensorName() {
        return sensorName;
    }

    @Override
    String getSensorValue() {
        return String.format(Locale.getDefault(),"%.1f%s", pm10, sensorUnit);
    }

}
