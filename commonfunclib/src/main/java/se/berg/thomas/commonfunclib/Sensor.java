package se.berg.thomas.commonfunclib;

/**
 * Created by thomas on 2017-08-23.
 */

abstract class Sensor {
    protected String sensorName;
    protected String sensorUnit;
    private Long timeStampNanos;

    abstract String getSensorName();

    abstract String getSensorValue();

    public void setTimeStamp(Long timeStamp) {
        this.timeStampNanos = timeStamp;
    }
}
