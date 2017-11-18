package se.berg.thomas.thingshub;

import android.arch.persistence.room.*;

import se.berg.thomas.commonfunclib.WalnutDevice;

/**
 * Created by thomas on 2017-11-12.
 */

@Entity
public class SensorData {
    @PrimaryKey
    private int uid;

    // Bluetooth deviceAddress
    private String deviceAddress;

//    private Int sensorId;

//    @ColumnInfo(name = "first_name")
//    private String firstName;

//    @ColumnInfo(name = "last_name")
//    private String lastName;

    // Getters and setters are ignored for brevity,
    // but they're required for Room to work.

    public SensorData(int uid, String deviceAddress) {
        this.uid = uid;
        this.deviceAddress = deviceAddress;
    }

    public int getUid() {
        return uid;
    }

    public String getDeviceAddress() {
        return deviceAddress;
    }
}