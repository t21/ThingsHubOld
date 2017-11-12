package se.berg.thomas.commonfunclib;

import android.bluetooth.BluetoothDevice;
import android.bluetooth.le.ScanResult;
import android.content.Context;
import android.os.ParcelUuid;

import java.util.ArrayList;
import java.util.UUID;

/**
 * Created by thomas on 2017-06-19.
 */

public class WalnutDevice {

    private static final String TAG = WalnutDevice.class.getSimpleName();

    public static final UUID DEVICE_INFORMATION_SERVICE_UUID = UUID.fromString("0000180a-0000-1000-8000-00805f9b34fb");
    public static final UUID DEVICE_INFORMATION_MANUFACTURER_CHARACTERISTIC_UUID = UUID.fromString("00002a29-0000-1000-8000-00805f9b34fb");
    public static final UUID DEVICE_INFORMATION_MODEL_CHARACTERISTIC_UUID = UUID.fromString("00002a24-0000-1000-8000-00805f9b34fb");
    public static final UUID DEVICE_INFORMATION_HW_REV_CHARACTERISTIC_UUID = UUID.fromString("00002a27-0000-1000-8000-00805f9b34fb");
    public static final UUID DEVICE_INFORMATION_SW_REV_CHARACTERISTIC_UUID = UUID.fromString("00002a28-0000-1000-8000-00805f9b34fb");
    public static final UUID DEVICE_INFORMATION_FW_REV_CHARACTERISTIC_UUID = UUID.fromString("00002a28-0000-1000-8000-00805f9b34fb");

    public static final UUID BATTERY_SERVICE_UUID = UUID.fromString("0000180f-0000-1000-8000-00805F9B34FB");
    public static final UUID BATTERY_CHARACTERISTIC_UUID = UUID.fromString("00002a19-0000-1000-8000-00805f9b34fb");

    public static final UUID WALNUT_SERVICE_UUID = UUID.fromString("00001100-0f58-2ba7-72c3-4d8d58fa16de");
    public static final UUID WALNUT_ID_CHARACTERISTIC_UUID = UUID.fromString("00001101-0f58-2ba7-72c3-4d8d58fa16de");
    public static final UUID WALNUT_CONFIG_CHARACTERISTIC_UUID = UUID.fromString("00001102-0f58-2ba7-72c3-4d8d58fa16de");
    public static final UUID WALNUT_UPTIME_CHARACTERISTIC_UUID = UUID.fromString("00001103-0f58-2ba7-72c3-4d8d58fa16de");
    public static final UUID WALNUT_FOTA_CHARACTERISTIC_UUID = UUID.fromString("00001104-0f58-2ba7-72c3-4d8d58fa16de");
    public static final UUID WALNUT_SW_VERSION_CHARACTERISTIC_UUID = UUID.fromString("00001105-0f58-2ba7-72c3-4d8d58fa16de");
    public static final UUID WALNUT_VERSION_CHARACTERISTIC_UUID = UUID.fromString("00001106-0f58-2ba7-72c3-4d8d58fa16de");
    public static final UUID WALNUT_SENSOR_DATA_1_CHARACTERISTIC_UUID = UUID.fromString("00001107-0f58-2ba7-72c3-4d8d58fa16de");
    public static final UUID WALNUT_SENSOR_DATA_2_CHARACTERISTIC_UUID = UUID.fromString("00001108-0f58-2ba7-72c3-4d8d58fa16de");
    public static final UUID WALNUT_ACTUATOR_CHARACTERISTIC_UUID = UUID.fromString("00001109-0f58-2ba7-72c3-4d8d58fa16de");

    public static final UUID WALNUT_CONFIG_SERVICE_UUID = UUID.fromString("6e400001-b5a3-f393-e0a9-e50e24dcca9e");
    public static final UUID WALNUT_CONFIG_RX_CHARACTERISTIC_UUID = UUID.fromString("6e400002-b5a3-f393-e0a9-e50e24dcca9e");
    public static final UUID WALNUT_CONFIG_TX_CHARACTERISTIC_UUID = UUID.fromString("6e400003-b5a3-f393-e0a9-e50e24dcca9e");

    public static final UUID CLIENT_CHARACTERISTIC_CONFIG_UUID = UUID.fromString("00002902-0000-1000-8000-00805f9b34fb");

    public static final ParcelUuid WALNUT_UUID = ParcelUuid.fromString("00001100-0f58-2ba7-72c3-4d8d58fa16de");
    public static final ParcelUuid BATTERY_UUID = ParcelUuid.fromString("0000180f-0000-1000-8000-00805F9B34FB");
    private static final int SIGMA_MANUFACTURER_ID = 0x03d3;
    private static final int SIGMA_MANUFACTURER_ID_OLD = 0xfef3;

    private static final int TEMPERATURE_SENSOR = 1;
    private static final int HUMIDITY_SENSOR = 2;
    private static final int AMBIENT_LIGHT_SENSOR = 3;
    private static final int MOISTURE_SENSOR = 4;
    private static final int ACCELEROMETER_SENSOR = 5;
    private static final int GYRO_SENSOR = 6;
    private static final int MAGNETOMETER_SENSOR = 7;
    private static final int PIR_SENSOR = 8;
    private static final int BAROMETRIC_PRESSURE_SENSOR = 9;
    private static final int LED_SENSOR = 10;
    private static final int BATTERY_SENSOR = 11;
    private static final int CO2_SENSOR = 12;
    private static final int PEOPLE_CROSSLINE_IN_SENSOR = 13;
    private static final int PEOPLE_CROSSLINE_OUT_SENSOR = 14;
    private static final int PEOPLE_CROSSLINE_COUNT_SENSOR = 15;
    private static final int PM25_SENSOR = 16;
    private static final int PM10_SENSOR = 17;
    private static final int ELECTRICITY_ACCUMULATED_SENSOR = 128;
    private static final int ELECTRICITY_INSTANTANEOUS_SENSOR = 129;

    private Context context;
    private BluetoothDevice device;
    private String deviceName;
    private String deviceAddress;
    private Integer rssi;
    private Integer batteryCapacity;
    private Long timestampNanos;
    private byte[] sensorData;
    private Integer receivedAdvMessagesCount;
    private Long receivedAdvMessagesTimeStamp;

    private ArrayList<Sensor> sensorList = new ArrayList<>();

    public WalnutDevice(Context context, ScanResult scanResult) {
        this.context = context;
        device = scanResult.getDevice();
        parseScanResult(scanResult);
//        deviceName = scanResult.getDevice().getName();
//        deviceAddress = scanResult.getDevice().getAddress();
//        rssi = scanResult.getRssi();
//        byte[] b = scanResult.getScanRecord().getServiceData(BATTERY_UUID);
//        if (b != null) {
//            batteryCapacity = Integer.valueOf(b[0]);
//        }
//        timestampNanos = scanResult.getTimestampNanos();
//        sensorData = scanResult.getScanRecord().getManufacturerSpecificData(SIGMA_MANUFACTURER_ID);
//        if (sensorData == null) {
//            sensorData = scanResult.getScanRecord().getManufacturerSpecificData(SIGMA_MANUFACTURER_ID_OLD);
//        }
        receivedAdvMessagesCount = 1;
        receivedAdvMessagesTimeStamp = System.currentTimeMillis();
    }

    public void update(ScanResult scanResult) {
        parseScanResult(scanResult);
    }

    private void parseScanResult(ScanResult scanResult) {
        deviceName = scanResult.getDevice().getName();
        deviceAddress = scanResult.getDevice().getAddress();
        rssi = scanResult.getRssi();
        byte[] b = scanResult.getScanRecord().getServiceData(BATTERY_UUID);
        if (b != null) {
            batteryCapacity = Integer.valueOf(b[0]);
        }
        timestampNanos = scanResult.getTimestampNanos();
        sensorData = scanResult.getScanRecord().getManufacturerSpecificData(SIGMA_MANUFACTURER_ID);
        if (sensorData == null) {
            sensorData = scanResult.getScanRecord().getManufacturerSpecificData(SIGMA_MANUFACTURER_ID_OLD);
        }

        sensorList.clear();
        parseSensorData();
    }

//    private void parseSensorData() {
//
//        Sensor s;
//
//        if (WalnutParser.isTemperatureFound(sensorData)) {
//            s = new TemperatureSensor();
//            s.setValue(WalnutParser.getTemperature(sensorData));
//            s.setTimeStamp(timestampNanos);
//            sensorList.add(s);
//        }
//
//        if (WalnutParser.isHumidityFound(sensorData)) {
//            s = new HumiditySensor();
//            s.setValue(WalnutParser.getHumidity(sensorData));
//            s.setTimeStamp(timestampNanos);
//            sensorList.add(s);
//        }
//
//        if (WalnutParser.isAmbientLightFound(sensorData)) {
//            s = new AmbientLightSensor();
//            s.setValue(WalnutParser.getAmbientLight(sensorData));
//            s.setTimeStamp(timestampNanos);
//            sensorList.add(s);
//        }
//
//        if (WalnutParser.isMoistureFound(sensorData)) {
//            s = new MoistureSensor();
//            s.setValue(WalnutParser.getMoisture(sensorData));
//            s.setTimeStamp(timestampNanos);
//            sensorList.add(s);
//        }
//
//        if (WalnutParser.isAccelerometerFound(sensorData)) {
//            s = new AccelerometerSensor();
//            s.setValue(WalnutParser.getAccelerometer(sensorData));
//            s.setTimeStamp(timestampNanos);
//            sensorList.add(s);
//        }
//
//        if (WalnutParser.isGyroFound(sensorData)) {
//            s = new GyroSensor();
//            s.setValue(WalnutParser.getGyro(sensorData));
//            s.setTimeStamp(timestampNanos);
//            sensorList.add(s);
//        }
//
//        if (WalnutParser.isMagnetometerFound(sensorData)) {
//            s = new MagnetometerSensor();
//            s.setValue(WalnutParser.getMagnetometer(sensorData));
//            s.setTimeStamp(timestampNanos);
//            sensorList.add(s);
//        }
//
//        if (WalnutParser.isPirFound(sensorData)) {
//            s = new PirSensor();
//            s.setValue(WalnutParser.getPir(sensorData));
//            s.setTimeStamp(timestampNanos);
//            sensorList.add(s);
//        }
//
//        if (WalnutParser.isBarometricPressureFound(sensorData)) {
//            s = new BarometricPressureSensor();
//            s.setValue(WalnutParser.getBarometricPressure(sensorData));
//            s.setTimeStamp(timestampNanos);
//            sensorList.add(s);
//        }
//
//        if (WalnutParser.isCO2Found(sensorData)) {
//            s = new CO2Sensor();
//            s.setValue(WalnutParser.getCO2(sensorData));
//            s.setTimeStamp(timestampNanos);
//            sensorList.add(s);
//        }
//
//        if (WalnutParser.isElInstFound(sensorData)) {
//            s = new ElectricityInstantaneousSensor();
//            s.setValue(WalnutParser.getElInst(sensorData));
//            s.setTimeStamp(timestampNanos);
//            sensorList.add(s);
//        }
//
//        if (WalnutParser.isElAccuFound(sensorData)) {
//            s = new ElectricityAccumulatedSensor();
//            s.setValue(WalnutParser.getElAccu(sensorData));
//            s.setTimeStamp(timestampNanos);
//            sensorList.add(s);
//        }
//
//    }

    public BluetoothDevice getDevice() {
        return device;
    }

    public String getName() {
        if (deviceName.equals("W")) {
            return "Sensmitter (" + deviceName + ")";
        } else {
            return deviceName;
        }
    }

    public String getAddress() {
        return deviceAddress;
    }

    public Integer getRssi() {
        return rssi;
    }

    public Integer getBatteryCapacity() {
        return batteryCapacity;
    }

    public String getSensorName(int pos) {
        return sensorList.get(pos).getSensorName();
    }

    public String getSensorValue(int pos) {
        return sensorList.get(pos).getSensorValue();
    }

//    public Boolean hasSensorData(Integer sensorId) {
//        if (sensorId == WalnutParser.BATTERY_SENSOR) {
//            return true;
//        }
//        return WalnutParser.hasSensorData(sensorId, sensorData);
//    }
//
//    public String getSensorData2(Integer sensorId) {
//        String sd = new String();
//        if (sensorId == WalnutParser.TEMPERATURE_SENSOR &&
//                WalnutParser.isTemperatureFound(sensorData)) {
//            sd = String.valueOf(WalnutParser.getTemperature(sensorData));
//        } else if (sensorId == WalnutParser.HUMIDITY_SENSOR &&
//                WalnutParser.isHumidityFound(sensorData)) {
//            sd = String.valueOf(WalnutParser.getHumidity(sensorData));
//        } else if (sensorId == WalnutParser.AMBIENT_LIGHT_SENSOR &&
//                WalnutParser.isAmbientLightFound(sensorData)) {
//            sd = String.valueOf(WalnutParser.getAmbientLight(sensorData));
//        } else if (sensorId == WalnutParser.MOISTURE_SENSOR &&
//                WalnutParser.isMoistureFound(sensorData)) {
//            sd = String.valueOf(WalnutParser.getMoisture(sensorData));
//        } else if (sensorId == WalnutParser.ACCELEROMETER_SENSOR &&
//                WalnutParser.isAccelerometerFound(sensorData)) {
//            sd = String.valueOf(WalnutParser.getAccelerometer(sensorData));
//        } else if (sensorId == WalnutParser.GYRO_SENSOR &&
//                WalnutParser.isGyroFound(sensorData)) {
//            sd = String.valueOf(WalnutParser.getGyro(sensorData));
//        } else if (sensorId == WalnutParser.MAGNETOMETER_SENSOR &&
//                WalnutParser.isMagnetometerFound(sensorData)) {
//            sd = String.valueOf(WalnutParser.getMagnetometer(sensorData));
//        } else if (sensorId == WalnutParser.PIR_SENSOR &&
//                WalnutParser.isPirFound(sensorData)) {
//            sd = String.valueOf(WalnutParser.getPir(sensorData));
//        } else if (sensorId == WalnutParser.BAROMETRIC_PRESSURE_SENSOR &&
//                WalnutParser.isBarometricPressureFound(sensorData)) {
//            sd = String.valueOf(WalnutParser.getBarometricPressure(sensorData));
//        } else if (sensorId == WalnutParser.LED_SENSOR &&
//                WalnutParser.isLedFound(sensorData)) {
//            sd = String.valueOf(WalnutParser.getLed(sensorData));
//        } else if (sensorId == WalnutParser.BATTERY_SENSOR) {
//            sd = String.valueOf(getBatteryCapacity());
//        } else if (sensorId == WalnutParser.CO2_SENSOR &&
//                WalnutParser.isCO2Found(sensorData)) {
//            sd = String.valueOf(WalnutParser.getCO2(sensorData));
//        } else if (sensorId == WalnutParser.PM25_SENSOR &&
//                WalnutParser.isPM25Found(sensorData)) {
//            sd = String.valueOf(WalnutParser.getPM25(sensorData));
//        } else if (sensorId == WalnutParser.PM10_SENSOR &&
//                WalnutParser.isPM10Found(sensorData)) {
//            sd = String.valueOf(WalnutParser.getPM10(sensorData));
//        }
//
//        return sd;
//    }
//
//    public String getSensorData() {
//        String sd = new String();
//        if (WalnutParser.isTemperatureFound(sensorData)) {
//            sd = sd + "\r\ntemperature: " + WalnutParser.getTemperature(sensorData);
//        }
//        if (WalnutParser.isHumidityFound(sensorData)) {
//            sd = sd + "\r\nhumidity: " + WalnutParser.getHumidity(sensorData);
//        }
//        if (WalnutParser.isAmbientLightFound(sensorData)) {
//            sd = sd + "\r\nambient light: " + WalnutParser.getAmbientLight(sensorData);
//        }
//        if (WalnutParser.isMoistureFound(sensorData)) {
//            sd = sd + "\r\nmoisture: " + WalnutParser.getMoisture(sensorData);
//        }
//        if (WalnutParser.isAccelerometerFound(sensorData)) {
//            sd = sd + "\r\naccelerometer: " + WalnutParser.getAccelerometer(sensorData);
//        }
//        if (WalnutParser.isGyroFound(sensorData)) {
//            sd = sd + "\r\ngyro: " + WalnutParser.getGyro(sensorData);
//        }
//        if (WalnutParser.isMagnetometerFound(sensorData)) {
//            sd = sd + "\r\nmagnetometer: " + WalnutParser.getMagnetometer(sensorData);
//        }
//        if (WalnutParser.isPirFound(sensorData)) {
//            sd = sd + "\r\npir: " + WalnutParser.getPir(sensorData);
//        }
//        if (WalnutParser.isBarometricPressureFound(sensorData)) {
//            sd = sd + "\r\nbarometric pressure: " + WalnutParser.getBarometricPressure(sensorData);
//        }
//        if (WalnutParser.isLedFound(sensorData)) {
//            sd = sd + "\r\nled: " + WalnutParser.getLed(sensorData);
//        }
//        if (WalnutParser.isBatteryFound(sensorData)) {
//            sd = sd + "\r\nbattery: " + WalnutParser.getBattery(sensorData);
//        }
//        if (WalnutParser.isCO2Found(sensorData)) {
//            sd = sd + "\r\nCO2: " + WalnutParser.getCO2(sensorData);
//        }
//        if (WalnutParser.isPM25Found(sensorData)) {
//            sd = sd + "\r\nPM25: " + WalnutParser.getPM25(sensorData);
//        }
//        if (WalnutParser.isPM10Found(sensorData)) {
//            sd = sd + "\r\nPM10: " + WalnutParser.getPM10(sensorData);
//        }
//
//        return sd;
//    }

    public String getLastTimeSeenString() {
        return (Long.toString(timestampNanos));
    }

    public Long getLastTimeSeen() {
        return timestampNanos;
    }


    public int getSensorListNbrOfItems() {
        return sensorList.size();
    }

    private void parseSensorData() {

        Sensor s;

        int i=0;
        while (i < (sensorData.length - 1)) {
            int sensorId = (sensorData[i] << 8) | (sensorData[i+1] & 0xFF);
            //Log.v(TAG, String.valueOf(sensor));
            i += 2;
            switch (sensorId) {
                case TEMPERATURE_SENSOR:
                    s = new TemperatureSensor(context, (sensorData[i] << 8) | (sensorData[i+1] & 0xff));
                    sensorList.add(s);
                    i += 2;
                    break;

                case HUMIDITY_SENSOR:
                    s = new HumiditySensor((sensorData[i] << 8) | (sensorData[i+1] & 0xff));
                    sensorList.add(s);
                    i += 2;
                    break;

                case AMBIENT_LIGHT_SENSOR:
                    s = new AmbientLightSensor((sensorData[i] << 8) | (sensorData[i+1] & 0xff));
                    sensorList.add(s);
                    i += 2;
                    break;

                case MOISTURE_SENSOR:
                    s = new MoistureSensor((sensorData[i] << 8) | (sensorData[i+1] & 0xff));
                    sensorList.add(s);
                    i += 2;
                    break;

                case ACCELEROMETER_SENSOR:
                    s = new AccelerometerSensor((sensorData[i] << 8) | (sensorData[i+1] & 0xff),
                            (sensorData[i+2] << 8) | (sensorData[i+3] & 0xff),
                            (sensorData[i+4] << 8) | (sensorData[i+5] & 0xff));
                    sensorList.add(s);
                    i += 6;
                    break;

                case GYRO_SENSOR:
                    s = new GyroSensor((sensorData[i] << 8) | (sensorData[i+1] & 0xff),
                            (sensorData[i+2] << 8) | (sensorData[i+3] & 0xff),
                            (sensorData[i+4] << 8) | (sensorData[i+5] & 0xff));
                    sensorList.add(s);
                    i += 6;
                    break;

                case MAGNETOMETER_SENSOR:
                    s = new MagnetometerSensor((sensorData[i] << 8) | (sensorData[i+1] & 0xff),
                            (sensorData[i+2] << 8) | (sensorData[i+3] & 0xff),
                            (sensorData[i+4] << 8) | (sensorData[i+5] & 0xff));
                    sensorList.add(s);
                    i += 6;
                    break;

                case PIR_SENSOR:
                    s = new PirSensor(sensorData[i] & 0xff);
                    sensorList.add(s);
                    i += 1;
                    break;

                case BAROMETRIC_PRESSURE_SENSOR:
                    s = new BarometricPressureSensor((sensorData[i] << 8) | (sensorData[i+1] & 0xff));
                    sensorList.add(s);
                    i += 2;
                    break;

                case LED_SENSOR:
//                    s = new LedSensor(sensorData[i] & 0xff);
//                    sensorList.add(s);
                    i += 1;
                    break;

                case BATTERY_SENSOR:
//                    s = new BatterySensor(sensorData[i] & 0xff);
//                    sensorList.add(s);
                    i += 1;
                    break;

                case CO2_SENSOR:
                    s = new CO2Sensor((sensorData[i] << 8) | (sensorData[i+1] & 0xff));
                    sensorList.add(s);
                    i += 2;
                    break;


                case PM25_SENSOR:
                    s = new PM25Sensor((sensorData[i] << 8) | (sensorData[i+1] & 0xff));
                    sensorList.add(s);
                    i += 2;
                    break;

                case PM10_SENSOR:
                    s = new PM10Sensor((sensorData[i] << 8) | (sensorData[i+1] & 0xff));
                    sensorList.add(s);
                    i += 2;
                    break;

                case ELECTRICITY_INSTANTANEOUS_SENSOR:
                    s = new ElectricityInstantaneousSensor((sensorData[i] << 8) | (sensorData[i+1] & 0xff));
                    sensorList.add(s);
                    i += 2;
                    break;

                case ELECTRICITY_ACCUMULATED_SENSOR:
                    s = new ElectricityAccumulatedSensor((sensorData[i] << 8) | (sensorData[i+1] & 0xff));
                    sensorList.add(s);
                    i += 2;
                    break;

                default:
                    // Unknown sensor found, stop parsing
                    i = sensorData.length;
                    break;
            }
        }
//        byte[] result = new byte[1];
//        result[0] = 0;
//        return result;




    }


}
