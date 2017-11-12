package se.berg.thomas.commonfunclib;

import java.util.Locale;

/**
 * Created by thomas on 2017-09-03.
 */

public class BarometricPressureSensor extends Sensor {

    private double barometricPressure;

    BarometricPressureSensor(int rawSensorValue) {
        sensorName = "barometric pressure";
        sensorUnit = "mbar";
        barometricPressure = rawSensorValue / 10.0;
    }


    @Override
    String getSensorName() {
        return sensorName;
    }

    @Override
    String getSensorValue() {
        return String.format(Locale.getDefault(),"%.1f %s", barometricPressure, sensorUnit);
    }

}
