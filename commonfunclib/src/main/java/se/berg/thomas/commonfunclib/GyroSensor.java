package se.berg.thomas.commonfunclib;

/**
 * Created by thomas on 2017-09-03.
 */

public class GyroSensor extends Sensor {

    private int x;
    private int y;
    private int z;

    GyroSensor(int raw_x, int raw_y, int raw_z) {
        sensorName = "rotation";
        sensorUnit = "rad/s";
        x = raw_x;
        y = raw_y;
        z = raw_z;
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
