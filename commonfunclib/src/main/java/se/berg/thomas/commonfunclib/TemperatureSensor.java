package se.berg.thomas.commonfunclib;

import android.content.Context;

import java.util.Locale;

//import se.lbhome.thomas.walnuthub.R;

/**
 * Created by thomas on 2017-08-23.
 */

public class TemperatureSensor extends Sensor {

    private double temperature;
    private Context context;

    TemperatureSensor(Context context, int rawSensorValue) {
        this.context = context;
//        sensorName = "temperature";
//        sensorName = context.getResources().getString(R.string.temperature_name);
        sensorUnit = "°C";
        temperature = rawSensorValue / 10.0;
    }

    @Override
    String getSensorName() {
        return context.getResources().getString(R.string.temperature_name);
    }

    @Override
    String getSensorValue() {
        return String.format(Locale.getDefault(),"%.1f%s", temperature, sensorUnit);
    }

}
