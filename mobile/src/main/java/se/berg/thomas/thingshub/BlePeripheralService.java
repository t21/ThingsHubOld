package se.berg.thomas.thingshub;

import android.app.Service;
import android.bluetooth.BluetoothAdapter;
import android.bluetooth.BluetoothManager;
import android.bluetooth.le.AdvertiseCallback;
import android.bluetooth.le.AdvertiseData;
import android.bluetooth.le.AdvertiseSettings;
import android.bluetooth.le.BluetoothLeAdvertiser;
import android.bluetooth.le.ScanSettings;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.os.Binder;
import android.os.IBinder;
import android.util.Log;

public class BlePeripheralService extends Service {

    private static final String TAG = "BlePeripheralService";

    private BluetoothAdapter mBluetoothAdapter;

    // This is the object that receives interactions from clients.  See
    // RemoteService for a more complete example.
    private final IBinder mBinder = new LocalBinder();
    private BluetoothLeAdvertiser mBluetoothLeAdvertiser;

    public BlePeripheralService() {
    }

    /**
     * Class for clients to access.  Because we know this service always
     * runs in the same process as its clients, we don't need to deal with
     * IPC.
     */
    public class LocalBinder extends Binder {
        BlePeripheralService getService() {
            return BlePeripheralService.this;
        }

        //        boolean addScanFilter(ScanFilter scanFilter) {
//            if (LOG_ENABLED) Log.v(TAG, "addScanFilter: " + scanFilter.toString());
//            stopScan();
//            scanFilterList.add(scanFilter);
//            startScan();
//            return true;
//        }
//
//        boolean enableScan() {
//            if (LOG_ENABLED) Log.v(TAG, "enableScan");
//            isScanRequested = true;
//            startScan();
//            return true;
//        }
        boolean enableAdvertisment() {
            boolean ret = startAdvertisement();
            return ret;
        }
    }

    @Override
    public void onCreate() {
        super.onCreate();
        Log.d(TAG, "onCreate");

        final BluetoothManager bluetoothManager = (BluetoothManager) getSystemService(BLUETOOTH_SERVICE);
        mBluetoothAdapter = bluetoothManager.getAdapter();

        //mScanPeriodicRestartHandler = new Handler();
    }

    @Override
    public IBinder onBind(Intent intent) {
        Log.d(TAG, "onBind");

        if (mBluetoothAdapter == null) {
            Log.w(TAG, "Bluetooth is not supported");
            return null;
        }

        if (!getPackageManager().hasSystemFeature(PackageManager.FEATURE_BLUETOOTH_LE)) {
            Log.w(TAG, "Bluetooth LE is not supported");
            return null;
        }

        // Register for system Bluetooth events
//        IntentFilter filter = new IntentFilter(BluetoothAdapter.ACTION_STATE_CHANGED);
//        registerReceiver(mBluetoothReceiver, filter);
//        if (mBluetoothAdapter.isEnabled()) {
//            if (LOG_ENABLED) Log.d(TAG, "Bluetooth is enabled...starting services");
//            startScan();
//        } else {
//            if (LOG_ENABLED) Log.d(TAG, "Bluetooth is disabled...enabling");
//            mBluetoothAdapter.enable();
//        }

        return mBinder;
    }

    @Override
    public boolean onUnbind(Intent intent) {
        Log.d(TAG, "onUnbind");
//        unregisterReceiver(mBluetoothReceiver);
        // TODO: Handle if scan shall keep running in the background with setting
//        stopScan();
        return super.onUnbind(intent);
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        Log.d(TAG, "onDestroy");
    }

    private boolean startAdvertisement() {
        Log.v(TAG, "startAdvertisement");
        final BluetoothManager bluetoothManager = (BluetoothManager) getSystemService(BLUETOOTH_SERVICE);
        BluetoothAdapter bluetoothAdapter = bluetoothManager.getAdapter();

//        if ((bluetoothAdapter == null) || !bluetoothAdapter.isEnabled() || !isScanRequested) {
//            if (LOG_ENABLED) Log.v(TAG, "startScan not ready, returning");
//            return;
//        }

        ScanSettings settings;

        if (bluetoothAdapter.isOffloadedScanBatchingSupported()) {
            settings = new ScanSettings.Builder()
                    .setCallbackType(ScanSettings.CALLBACK_TYPE_ALL_MATCHES)
                    .setMatchMode(ScanSettings.MATCH_MODE_AGGRESSIVE)
                    .setNumOfMatches(ScanSettings.MATCH_NUM_ONE_ADVERTISEMENT)
                    .setReportDelay(1000)
                    .setScanMode(ScanSettings.SCAN_MODE_LOW_LATENCY)
                    .build();
        } else {
            settings = new ScanSettings.Builder()
                    .setCallbackType(ScanSettings.CALLBACK_TYPE_ALL_MATCHES)
                    .setMatchMode(ScanSettings.MATCH_MODE_AGGRESSIVE)
                    .setScanMode(ScanSettings.SCAN_MODE_LOW_LATENCY)
                    .build();
        }

        AdvertiseSettings advSettings = new AdvertiseSettings.Builder()
                .setAdvertiseMode(AdvertiseSettings.ADVERTISE_MODE_LOW_POWER)
                .setConnectable(true)
                .setTimeout(0)
                .setTxPowerLevel(AdvertiseSettings.ADVERTISE_TX_POWER_HIGH)
                .build();

        AdvertiseData advData = new AdvertiseData.Builder()
                .build();

        AdvertiseData scanResponse = new AdvertiseData.Builder()
                .build();

        if (bluetoothAdapter.isEnabled()) {
            mBluetoothLeAdvertiser = bluetoothAdapter.getBluetoothLeAdvertiser();
            if (mBluetoothLeAdvertiser == null) {
                return false;
            }
            mBluetoothLeAdvertiser.startAdvertising(advSettings, advData, scanResponse, advertiseCallback);

//            if (mScanPeriodicRestartHandler != null) {
//                mScanPeriodicRestartHandler.removeCallbacks(scanPeriodicRestartRunnable);
//            }
//            mScanPeriodicRestartHandler.postDelayed(scanPeriodicRestartRunnable, SCAN_PERIODIC_RESTART_PERIOD);

        } else {
            Log.v(TAG, "startScan: bluetoothAdapter not enabled");
        }

        return true;
    }

    private AdvertiseCallback advertiseCallback = new AdvertiseCallback() {
        @Override
        public void onStartSuccess(AdvertiseSettings settingsInEffect) {
            super.onStartSuccess(settingsInEffect);
            Log.v(TAG, "onStartSuccess");
        }

        @Override
        public void onStartFailure(int errorCode) {
            super.onStartFailure(errorCode);
            Log.v(TAG, "onStartFailure");
        }
    };

}
