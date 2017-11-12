package se.berg.thomas.commonfunclib;

import java.util.Locale;

/**
 * Created by thomas on 2017-09-10.
 */

public class PM25Sensor extends Sensor {

    private double pm25;

    PM25Sensor(int rawSensorValue) {
        sensorName = "particle25";
        sensorUnit = "?";
        pm25 = rawSensorValue / 10.0;
    }

    @Override
    String getSensorName() {
        return sensorName;
    }

    @Override
    String getSensorValue() {
        return String.format(Locale.getDefault(),"%.1f%s", pm25, sensorUnit);
    }

}
