package se.berg.thomas.commonfunclib;

/**
 * Created by thomas on 2017-09-03.
 */

public class AccelerometerSensor extends Sensor {

    private int x;
    private int y;
    private int z;

    AccelerometerSensor(int raw_x, int raw_y, int raw_z) {
        sensorName = "acceleration";
        sensorUnit = "mg";
        x = raw_x;
        y = raw_y;
        z = raw_z;
    }


    //                    accelerometerPresent = true;
    //int x = (int)((unsignedToInt(manufData[i]) << 8) | unsignedToInt(manufData[i+1]));
    //Log.d(TAG, "" + manufData[i] + " " + unsignedToInt(manufData[i]));
    //Log.d(TAG, "" + manufData[i+1] + " " + unsignedToInt(manufData[i+1]));
    //Log.d(TAG, "" + x);
//                    accelerometerX = ((short)((unsignedToInt(manufData[i]) << 8) | unsignedToInt(manufData[i+1]))) / 1000.0;
    //Log.d(TAG, "" + accelerometerX);
//                    accelerometerY = ((short)((unsignedToInt(manufData[i+2]) << 8) | unsignedToInt(manufData[i+3]))) / 1000.0;
//                    accelerometerZ = ((short)((unsignedToInt(manufData[i+4]) << 8) | unsignedToInt(manufData[i+5]))) / 1000.0;

    @Override
    String getSensorName() {
        return sensorName;
    }

    @Override
    String getSensorValue() {
        return Double.toString(0.0) + sensorUnit;
    }

}
