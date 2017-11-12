package se.berg.thomas.commonfunclib;

import java.util.Locale;

/**
 * Created by thomas on 2017-08-23.
 */

public class HumiditySensor extends Sensor {

    private double relativeHumidity;

    HumiditySensor(int rawSensorValue) {
        sensorName = "relative humidity";
        sensorUnit = "%";
        relativeHumidity = rawSensorValue / 10.0;
    }


    @Override
    String getSensorName() {
        return sensorName;
    }

    @Override
    String getSensorValue() {
        return String.format(Locale.getDefault(),"%.1f%s", relativeHumidity, sensorUnit);
    }

}
