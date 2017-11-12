package se.berg.thomas.commonfunclib;

import java.util.Locale;

/**
 * Created by thomas on 2017-09-03.
 */

public class AmbientLightSensor extends Sensor {

    private int ambientLight;

    AmbientLightSensor(int rawSensorValue) {
        sensorName = "ambient light";
        sensorUnit = "Lux";
        ambientLight = rawSensorValue;
    }


    @Override
    String getSensorName() {
        return sensorName;
    }

    @Override
    String getSensorValue() {
        return String.format(Locale.getDefault(),"%d %s", ambientLight, sensorUnit);
    }

}
