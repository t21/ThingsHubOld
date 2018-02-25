package se.berg.thomas.thingshub;

import android.Manifest;
import android.app.Activity;
import android.bluetooth.le.ScanFilter;
import android.bluetooth.le.ScanResult;
import android.content.BroadcastReceiver;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.content.ServiceConnection;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.os.IBinder;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v4.content.LocalBroadcastManager;
import android.util.Log;

import se.berg.thomas.sharedresources.BleScanService;

/**
 * Skeleton of an Android Things activity.
 * <p>
 * Android Things peripheral APIs are accessible through the class
 * PeripheralManagerService. For example, the snippet below will open a GPIO pin and
 * set it to HIGH:
 * <p>
 * <pre>{@code
 * PeripheralManagerService service = new PeripheralManagerService();
 * mLedGpio = service.openGpio("BCM6");
 * mLedGpio.setDirection(Gpio.DIRECTION_OUT_INITIALLY_LOW);
 * mLedGpio.setValue(true);
 * }</pre>
 * <p>
 * For more complex peripherals, look for an existing user-space driver, or implement one if none
 * is available.
 *
 * @see <a href="https://github.com/androidthings/contrib-drivers#readme">https://github.com/androidthings/contrib-drivers#readme</a>
 */
public class MainActivity extends Activity {

    private String TAG = "MainActivity";
    private static final int MY_PERMISSIONS_REQUEST_ACCESS_COARSE_LOCATION = 1;

//    protected BleScanService mBleScanService;
    protected boolean mBound = false;
    LocalBroadcastManager mLocalBroadcastManager;
    BleBroadcastReceiver mBleReceiver = new BleBroadcastReceiver();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Log.v(TAG, "onStart");

        setContentView(R.layout.activity_main);
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

        // Bind to LocalService
        Intent intent = new Intent(this, BleScanService.class);
        bindService(intent, mBleScanServiceConnection, Context.BIND_AUTO_CREATE);

        // Bind to BlePeripheralService
//        intent = new Intent(this, BlePeripheralService.class);
//        bindService(intent, mPeripheralServiceConnection, Context.BIND_AUTO_CREATE);
    }

    @Override
    protected void onResume() {
        super.onResume();
        Log.v(TAG, "onResume");
        IntentFilter intentFilter = new IntentFilter();
        intentFilter.addAction("se.lbhome.thomas.thingshub.ADV_MESSAGE");
        mLocalBroadcastManager = LocalBroadcastManager.getInstance(this);
        mLocalBroadcastManager.registerReceiver(mBleReceiver, intentFilter);

//        Intent intent = new Intent(this, LocalStorageService.class);
//        startService(intent);
    }

    @Override
    protected void onPause() {
        super.onPause();
        Log.v(TAG, "onPause");
        mLocalBroadcastManager.unregisterReceiver(mBleReceiver);

//        Intent intent = new Intent(this, LocalStorageService.class);
//        stopService(intent);
    }

    @Override
    protected void onStop() {
        super.onStop();
        Log.v(TAG, "onStop");
        // Unbind from the service
        if (mBound) {
            unbindService(mBleScanServiceConnection);
            mBound = false;
        }
//        this.getResources().getString(R.string.temperature_name);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        Log.v(TAG, "onDestroy");

//        mWalnutList = null;
    }

    /** Defines callbacks for service binding, passed to bindService() */
    private ServiceConnection mBleScanServiceConnection = new ServiceConnection() {

        @Override
        public void onServiceConnected(ComponentName className, IBinder service) {
            Log.v(TAG, "onServiceConnected");
            // We've bound to LocalService, cast the IBinder and get LocalService instance
            BleScanService.LocalBinder binder = (BleScanService.LocalBinder) service;
//            mService = binder.getService();
            mBound = true;

//            ScanFilter walnutScanFilter = new ScanFilter.Builder()
//                    .setServiceUuid(WalnutDevice.WALNUT_UUID)
//                    .build();
//            binder.addScanFilter(walnutScanFilter);

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
//            if (intent.getAction().equals("se.lbhome.thomas.thingshub.ADV_MESSAGE")) {
//                ScanResult scanResult = intent.getParcelableExtra("ADV");
//                Log.d(TAG, scanResult.toString());
//
//                Log.v(TAG, "" + mWalnutList.size());
//
//                for (WalnutDevice w: mWalnutList) {
//                    if (w.getAddress().equals(scanResult.getDevice().getAddress())) {
//                        // Device is found, update it
//                        w.update(scanResult);
//                        mAdapter.notifyDataSetChanged();
//                        return;
//                    }
//                }
//                // First device or device not found in list
//                WalnutDevice walnut = new WalnutDevice(MainActivity.this, scanResult);
//                mWalnutList.add(walnut);
//                mAdapter.notifyDataSetChanged();
//            }

        }
    }

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

}
