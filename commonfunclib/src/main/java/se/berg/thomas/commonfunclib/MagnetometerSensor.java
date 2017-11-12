package se.berg.thomas.commonfunclib;

/**
 * Created by thomas on 2017-09-03.
 */

public class MagnetometerSensor extends Sensor {

    private int x;
    private int y;
    private int z;

    MagnetometerSensor(int raw_mag_x, int raw_mag_y, int raw_mag_z) {
        sensorName = "magnetometer";
        sensorUnit = "mGauss";
        x = raw_mag_x;
        y = raw_mag_y;
        z = raw_mag_z;
    }

    @Override
    String getSensorName() {
        return sensorName;
    }

    @Override
    String getSensorValue() {
        return Double.toString(0.0) + sensorUnit;
    }

}
