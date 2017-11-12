package se.berg.thomas.thingshub;

import android.bluetooth.BluetoothDevice;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.ServiceConnection;
import android.os.Bundle;
import android.os.IBinder;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.Spinner;
import android.widget.Switch;
import android.widget.TextView;
import android.widget.Toast;

import java.text.NumberFormat;
import java.text.ParseException;
import java.util.Locale;

public class ConfigureWalnutActivity extends AppCompatActivity implements AdapterView.OnItemSelectedListener, View.OnClickListener {

    private String TAG = "ConfigureWalnutActivity";
    private int advertising_interval;
    private BluetoothDevice bluetoothDevice;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_configure_walnut);

        Spinner spinner = (Spinner) findViewById(R.id.spinner_walnut_type);
        spinner.setOnItemSelectedListener(this);

        Spinner advIntervalSpinner = (Spinner) findViewById(R.id.spinner_adv_interval);
        advIntervalSpinner.setOnItemSelectedListener(this);

        Button startConfigureButton = findViewById(R.id.button);
        startConfigureButton.setOnClickListener(this);

        Intent intent = getIntent();
        bluetoothDevice = (BluetoothDevice) intent.getParcelableExtra("DEVICE");
        Toast.makeText(this, bluetoothDevice.getAddress(), Toast.LENGTH_SHORT).show();
    }

    @Override
    protected void onStart() {
        super.onStart();
    }

    @Override
    protected void onStop() {
        super.onStop();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        Log.v(TAG, "onDestroy");
    }

    private void handleAdvIntervalSpinner(String s) {

        NumberFormat nf = NumberFormat.getInstance(Locale.ENGLISH);

        try {
            advertising_interval = (int) (nf.parse(s).doubleValue() * 1000);
        } catch (ParseException e) {
            e.printStackTrace();
        }
        Toast.makeText(this, String.valueOf(advertising_interval), Toast.LENGTH_SHORT).show();

    }

    private void handleWalnutTypeSpinner(String s) {
//        String[] stringArray = this.getResources().getStringArray(R.array.advertising_intervals);
//        for (String str: stringArray) {
//
//        }
        if (s.equals(this.getResources().getString(R.string.walnut_type_climate))) {
            Toast.makeText(this, "Climate found", Toast.LENGTH_SHORT).show();
        } else if (s.equals(this.getResources().getString(R.string.walnut_type_conference))) {

        } else if (s.equals(this.getResources().getString(R.string.walnut_type_sensdesk))) {

        } else {
            TextView temperatureTextView = findViewById(R.id.temperature_textView);
            temperatureTextView.setVisibility(View.GONE);
            Switch temperatureEnableSwitch = findViewById(R.id.temperature_enable_switch);
            temperatureEnableSwitch.setVisibility(View.GONE);
        }
    }

    /** Defines callbacks for service binding, passed to bindService() */
    private ServiceConnection mConnection = new ServiceConnection() {

        @Override
        public void onServiceConnected(ComponentName className, IBinder service) {
            Log.v(TAG, "onServiceConnected");
            // We've bound to LocalService, cast the IBinder and get LocalService instance
            WalnutConfigBleService.LocalBinder binder = (WalnutConfigBleService.LocalBinder) service;
//            mService = binder.getService();
//            mBound = true;

//            ScanFilter walnutScanFilter = new ScanFilter.Builder()
//                    .setServiceUuid(WalnutDevice.WALNUT_UUID)
//                    .build();
//            binder.addScanFilter(walnutScanFilter);
//
//            binder.enableScan();
            binder.connect(bluetoothDevice);
        }

        @Override
        public void onServiceDisconnected(ComponentName arg0) {
            Log.v(TAG, "onServiceDisconnected");
//            mBound = false;
        }
    };

    @Override
    public void onItemSelected(AdapterView<?> parent, View view, int pos, long id) {
//        Toast.makeText(parent.getContext(),
//                "OnItemSelectedListener : " + parent.getItemAtPosition(pos).toString(),
//                Toast.LENGTH_SHORT).show();
        switch (parent.getId()) {
            case R.id.spinner_adv_interval:
                //Toast.makeText(this, parent.getSelectedItem().toString() + id, Toast.LENGTH_SHORT).show();
                handleAdvIntervalSpinner(parent.getSelectedItem().toString());
                break;
            case R.id.spinner_walnut_type:
                //Do something
                Toast.makeText(this, parent.getSelectedItem().toString(), Toast.LENGTH_SHORT).show();
                handleWalnutTypeSpinner(parent.getSelectedItem().toString());
                break;
        }

    }

    @Override
    public void onNothingSelected(AdapterView<?> adapterView) {

    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.button:
                // Bind to LocalService
                Intent intent = new Intent(this, WalnutConfigBleService.class);
                bindService(intent, mConnection, Context.BIND_AUTO_CREATE);
                break;
        }

    }
}
