package se.berg.thomas.thingshub;

import android.Manifest;
import android.bluetooth.le.ScanFilter;
import android.bluetooth.le.ScanResult;
import android.content.BroadcastReceiver;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.content.ServiceConnection;
import android.content.pm.PackageManager;
import android.os.IBinder;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v4.content.LocalBroadcastManager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.widget.Toast;

import java.util.ArrayList;

import se.berg.thomas.commonfunclib.WalnutDevice;

public class MainActivity extends AppCompatActivity {

    private static final int MY_PERMISSIONS_REQUEST_ACCESS_COARSE_LOCATION = 1;
    private String TAG = "MainActivity";
    private static final long THINGSPEAK_UPDATE_PERIOD = 120000;

    protected BleService mService;
    protected boolean mBound = false;
    LocalBroadcastManager mLocalBroadcastManager;
    BleBroadcastReceiver mBleReceiver = new BleBroadcastReceiver();
    private ArrayList<WalnutDevice> mWalnutList;
//    private ThingspeakCloud mThingspeak;
//    private Handler mThingspeakUpdateHandler;

    private RecyclerView mRecyclerView;
    private RecyclerView.Adapter mAdapter;
    private RecyclerView.LayoutManager mLayoutManager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        mRecyclerView = (RecyclerView) findViewById(R.id.my_recycler_view);

        // use this setting to improve performance if you know that changes
        // in content do not change the layout size of the RecyclerView
        //mRecyclerView.setHasFixedSize(true);

        // use a linear layout manager
        mLayoutManager = new LinearLayoutManager(this);
        mRecyclerView.setLayoutManager(mLayoutManager);

//        mWalnutArrayList = new ArrayList<>();
        // Create list of Walnuts
        mWalnutList = new ArrayList<>();

        // specify an adapter (see also next example)
        mAdapter = new MyAdapter(mWalnutList, new MyAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(WalnutDevice item) {
                Toast.makeText(MainActivity.this, item.getAddress(), Toast.LENGTH_SHORT).show();
                Intent intent = new Intent(MainActivity.this, ConfigureWalnutActivity.class);
                intent.putExtra("DEVICE", item.getDevice());
                startActivity(intent);
            }
        });
        mRecyclerView.setAdapter(mAdapter);
    }

    @Override
    protected void onStart() {
        super.onStart();
        Log.v(TAG, "onStart");

        // Here, thisActivity is the current activity
        if (ContextCompat.checkSelfPermission(this,
                Manifest.permission.ACCESS_COARSE_LOCATION)
                != PackageManager.PERMISSION_GRANTED) {

            Log.v(TAG, "requestPermission");

            ActivityCompat.requestPermissions(this,
                    new String[]{Manifest.permission.ACCESS_COARSE_LOCATION},
                    MY_PERMISSIONS_REQUEST_ACCESS_COARSE_LOCATION);
        }

        // Create list of Walnuts
//        mWalnutList = new ArrayList<>();
//        mNbrOfWalnutsTextView.setText(String.valueOf(mWalnutList.size()));

        // Create Thingspeak Cloud instance
//        mThingspeak = new ThingspeakCloud(this.getApplicationContext());

        // Get available Thingspeak channels
//        mThingspeak.getChannels();

        // Create periodic Thingspeak send task
//        mThingspeakUpdateHandler = new Handler();
//        mThingspeakUpdateHandler.postDelayed(thingspeakUpdateRunnable, THINGSPEAK_UPDATE_PERIOD);

        // Bind to LocalService
        Intent intent = new Intent(this, BleService.class);
        bindService(intent, mConnection, Context.BIND_AUTO_CREATE);

        // Bind to BlePeripheralService
        intent = new Intent(this, BlePeripheralService.class);
        bindService(intent, mPeripheralServiceConnection, Context.BIND_AUTO_CREATE);
    }

    @Override
    protected void onResume() {
        super.onResume();
        Log.v(TAG, "onResume");
        IntentFilter intentFilter = new IntentFilter();
        intentFilter.addAction("se.lbhome.thomas.thingshub.ADV_MESSAGE");
        mLocalBroadcastManager = LocalBroadcastManager.getInstance(this);
        mLocalBroadcastManager.registerReceiver(mBleReceiver, intentFilter);
    }

    @Override
    protected void onPause() {
        super.onPause();
        Log.v(TAG, "onPause");
        mLocalBroadcastManager.unregisterReceiver(mBleReceiver);
    }

    @Override
    protected void onStop() {
        super.onStop();
        Log.v(TAG, "onStop");
        // Unbind from the service
        if (mBound) {
            unbindService(mConnection);
            mBound = false;
        }
        this.getResources().getString(R.string.temperature_name);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        Log.v(TAG, "onDestroy");

        mWalnutList = null;
    }

    private BleService mBoundService;

    /** Defines callbacks for service binding, passed to bindService() */
    private ServiceConnection mConnection = new ServiceConnection() {

        @Override
        public void onServiceConnected(ComponentName className, IBinder service) {
            Log.v(TAG, "onServiceConnected");
            // We've bound to LocalService, cast the IBinder and get LocalService instance
            BleService.LocalBinder binder = (BleService.LocalBinder) service;
            mService = binder.getService();
            mBound = true;

            ScanFilter walnutScanFilter = new ScanFilter.Builder()
                    .setServiceUuid(WalnutDevice.WALNUT_UUID)
                    .build();
            binder.addScanFilter(walnutScanFilter);

            binder.enableScan();
        }

        @Override
        public void onServiceDisconnected(ComponentName arg0) {
            Log.v(TAG, "onServiceDisconnected");
            mBound = false;
        }
    };

    private class BleBroadcastReceiver extends BroadcastReceiver {

        @Override
        public void onReceive(Context context, Intent intent) {
            Log.v(TAG, "onReceive");
            if (intent.getAction().equals("se.lbhome.thomas.thingshub.ADV_MESSAGE")) {
                ScanResult scanResult = intent.getParcelableExtra("ADV");
                Log.d(TAG, scanResult.toString());

                Log.v(TAG, "" + mWalnutList.size());

                for (WalnutDevice w: mWalnutList) {
                    if (w.getAddress().equals(scanResult.getDevice().getAddress())) {
                        // Device is found, update it
                        w.update(scanResult);
                        mAdapter.notifyDataSetChanged();
                        return;
                    }
                }
                // First device or device not found in list
                WalnutDevice walnut = new WalnutDevice(MainActivity.this, scanResult);
                mWalnutList.add(walnut);
                mAdapter.notifyDataSetChanged();
            }

        }
    }

    private Runnable thingspeakUpdateRunnable = new Runnable() {
        @Override
        public void run() {
            Log.d(TAG, "thingspeakUpdateRunnable");
//            mThingspeak.updateChannels(mWalnutList);
//            mThingspeakUpdateHandler.postDelayed(thingspeakUpdateRunnable, THINGSPEAK_UPDATE_PERIOD);
        }
    };

    @Override
    public void onRequestPermissionsResult(int requestCode,
                                           String permissions[], int[] grantResults) {
        switch (requestCode) {
            case MY_PERMISSIONS_REQUEST_ACCESS_COARSE_LOCATION: {
                // If request is cancelled, the result arrays are empty.
                if (grantResults.length > 0
                        && grantResults[0] == PackageManager.PERMISSION_GRANTED) {

                    // permission was granted, yay! Do the
                    // contacts-related task you need to do.
                    Log.v(TAG, "permission granted");

                } else {

                    // permission denied, boo! Disable the
                    // functionality that depends on this permission.
                    Log.v(TAG, "permission denied");
                }
                return;
            }

            // other 'case' lines to check for other
            // permissions this app might request
        }
    }

    /** Defines callbacks for service binding, passed to bindService() */
    private ServiceConnection mPeripheralServiceConnection = new ServiceConnection() {

        @Override
        public void onServiceConnected(ComponentName className, IBinder service) {
            Log.v(TAG, "mPeripheralServiceConnection:onServiceConnected");
            // We've bound to LocalService, cast the IBinder and get LocalService instance
            BlePeripheralService.LocalBinder binder = (BlePeripheralService.LocalBinder) service;
            //mService = binder.getService();
            //mBound = true;

//            ScanFilter walnutScanFilter = new ScanFilter.Builder()
//                    .setServiceUuid(WalnutDevice.WALNUT_UUID)
//                    .build();
//            binder.addScanFilter(walnutScanFilter);
//
//            binder.enableScan();
            boolean ret_code = binder.enableAdvertisment();
            if (ret_code) {
                Log.v(TAG, "BLE advertising started");
            } else {
                Log.v(TAG, "BLE advertising not supported");
            }
        }

        @Override
        public void onServiceDisconnected(ComponentName arg0) {
            Log.v(TAG, "mPeripheralServiceConnection:onServiceDisconnected");
            //mBound = false;
        }
    };

}
