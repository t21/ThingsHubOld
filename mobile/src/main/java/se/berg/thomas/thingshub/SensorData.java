package se.berg.thomas.thingshub;

import android.arch.persistence.room.*;
import android.support.annotation.NonNull;

import se.berg.thomas.commonfunclib.WalnutDevice;

/**
 * Created by thomas on 2017-11-12.
 */

@Entity
public class SensorData {

    @PrimaryKey @NonNull
    private String deviceAddress;

    private int timeStamp;

    private int sensorId;

    private double sensorVal;

    public SensorData(String deviceAddress, int timeStamp, int sensorId, double sensorVal) {
        this.deviceAddress = deviceAddress;
        this.timeStamp = timeStamp;
        this.sensorId = sensorId;
        this.sensorVal = sensorVal;
    }

    public String getDeviceAddress() {
        return deviceAddress;
    }

    public int getTimeStamp() {
        return timeStamp;
    }

    public int getSensorId() {
        return sensorId;
    }

    public double getSensorVal() {
        return sensorVal;
    }
}