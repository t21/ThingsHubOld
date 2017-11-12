package se.berg.thomas.thingshub;

import android.app.Service;
import android.bluetooth.BluetoothAdapter;
import android.bluetooth.BluetoothManager;
import android.bluetooth.le.BluetoothLeScanner;
import android.bluetooth.le.ScanCallback;
import android.bluetooth.le.ScanFilter;
import android.bluetooth.le.ScanResult;
import android.bluetooth.le.ScanSettings;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.content.pm.PackageManager;
import android.os.Binder;
import android.os.Handler;
import android.os.IBinder;
import android.support.v4.content.LocalBroadcastManager;
import android.util.Log;

import java.util.ArrayList;
import java.util.List;

public class BleService extends Service {

    private String TAG = "BleService";
    private final boolean LOG_ENABLED = false;

    private BluetoothAdapter mBluetoothAdapter;
    private static final long SCAN_PERIODIC_RESTART_PERIOD = 29 * 60 * 1000;

    protected ArrayList<ScanFilter> scanFilterList = new ArrayList<>();

    // This is the object that receives interactions from clients.  See
    // RemoteService for a more complete example.
    private final IBinder mBinder = new LocalBinder();
    private BluetoothLeScanner mBluetoothLeScanner;
    private boolean isScanRequested  = false;

    private Handler mScanPeriodicRestartHandler;


    public BleService() {
    }

    /**
     * Class for clients to access.  Because we know this service always
     * runs in the same process as its clients, we don't need to deal with
     * IPC.
     */
    public class LocalBinder extends Binder {
        BleService getService() {
            return BleService.this;
        }

        boolean addScanFilter(ScanFilter scanFilter) {
            if (LOG_ENABLED) Log.v(TAG, "addScanFilter: " + scanFilter.toString());
            stopScan();
            scanFilterList.add(scanFilter);
            startScan();
            return true;
        }

        boolean enableScan() {
            if (LOG_ENABLED) Log.v(TAG, "enableScan");
            isScanRequested = true;
            startScan();
            return true;
        }
    }

    private void startScan() {
        if (LOG_ENABLED) Log.v(TAG, "startScan");
        final BluetoothManager bluetoothManager = (BluetoothManager) getSystemService(BLUETOOTH_SERVICE);
        BluetoothAdapter bluetoothAdapter = bluetoothManager.getAdapter();

        if ((bluetoothAdapter == null) || !bluetoothAdapter.isEnabled() || !isScanRequested) {
            if (LOG_ENABLED) Log.v(TAG, "startScan not ready, returning");
            return;
        }

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

        if (bluetoothAdapter.isEnabled()) {
            mBluetoothLeScanner = bluetoothAdapter.getBluetoothLeScanner();
            mBluetoothLeScanner.startScan(scanFilterList, settings, mScanCallback);

            if (mScanPeriodicRestartHandler != null) {
                mScanPeriodicRestartHandler.removeCallbacks(scanPeriodicRestartRunnable);
            }
            mScanPeriodicRestartHandler.postDelayed(scanPeriodicRestartRunnable, SCAN_PERIODIC_RESTART_PERIOD);

        } else {
            if (LOG_ENABLED) Log.v(TAG, "startScan: bluetoothAdapter not enabled");
        }


    }

    private void stopScan() {
        if (LOG_ENABLED) Log.v(TAG, "stopScan");

        if (mBluetoothLeScanner != null) {
            mBluetoothLeScanner.stopScan(mScanCallback);
        }

        if (mScanPeriodicRestartHandler != null) {
            mScanPeriodicRestartHandler.removeCallbacks(scanPeriodicRestartRunnable);
        }
    }

    @Override
    public void onCreate() {
        super.onCreate();
        if (LOG_ENABLED) Log.d(TAG, "onCreate");

        final BluetoothManager bluetoothManager = (BluetoothManager) getSystemService(BLUETOOTH_SERVICE);
        mBluetoothAdapter = bluetoothManager.getAdapter();
        //mBluetoothLeScanner = mBluetoothAdapter.getBluetoothLeScanner();

        mScanPeriodicRestartHandler = new Handler();
    }

    @Override
    public IBinder onBind(Intent intent) {
        if (LOG_ENABLED) Log.d(TAG, "onBind");

        if (mBluetoothAdapter == null) {
            if (LOG_ENABLED) Log.w(TAG, "Bluetooth is not supported");
            return null;
        }

        if (!getPackageManager().hasSystemFeature(PackageManager.FEATURE_BLUETOOTH_LE)) {
            if (LOG_ENABLED) Log.w(TAG, "Bluetooth LE is not supported");
            return null;
        }

        // Register for system Bluetooth events
        IntentFilter filter = new IntentFilter(BluetoothAdapter.ACTION_STATE_CHANGED);
        registerReceiver(mBluetoothReceiver, filter);
        if (mBluetoothAdapter.isEnabled()) {
            if (LOG_ENABLED) Log.d(TAG, "Bluetooth is enabled...starting services");
            startScan();
        } else {
            if (LOG_ENABLED) Log.d(TAG, "Bluetooth is disabled...enabling");
            mBluetoothAdapter.enable();
        }

        return mBinder;
    }

    @Override
    public boolean onUnbind(Intent intent) {
        if (LOG_ENABLED) Log.d(TAG, "onUnbind");
        unregisterReceiver(mBluetoothReceiver);
        // TODO: Handle if scan shall keep running in the background with setting
        stopScan();
        return super.onUnbind(intent);
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        if (LOG_ENABLED) Log.d(TAG, "onDestroy");
    }

    /**
     * Listens for Bluetooth adapter events to enable/disable
     * advertising and server functionality.
     */
    private BroadcastReceiver mBluetoothReceiver = new BroadcastReceiver() {
        @Override
        public void onReceive(Context context, Intent intent) {
            int state = intent.getIntExtra(BluetoothAdapter.EXTRA_STATE, BluetoothAdapter.STATE_OFF);

            switch (state) {
                case BluetoothAdapter.STATE_ON:
                    if (LOG_ENABLED) Log.v(TAG, "Bluetooth state changed ... to ON");
                    startScan();
                    break;
                case BluetoothAdapter.STATE_OFF:
                    if (LOG_ENABLED) Log.v(TAG, "Bluetooth state changed ... to OFF");
                    stopScan();
                    break;
                default:
                    // Do nothing
            }

        }
    };

    private ScanCallback mScanCallback = new ScanCallback() {
        @Override
        public void onScanResult(int callbackType, ScanResult result) {
            super.onScanResult(callbackType, result);
            if (LOG_ENABLED) Log.v(TAG, "onScanResult");
            Log.v(TAG, result.toString());
            Intent intent = new Intent();
            intent.setAction("se.lbhome.thomas.thingshub.ADV_MESSAGE");
            intent.putExtra("ADV", result);
            LocalBroadcastManager localBroadcastManager = LocalBroadcastManager.getInstance(BleService.this);
            localBroadcastManager.sendBroadcast(intent);
        }

        @Override
        public void onBatchScanResults(List<ScanResult> results) {
            super.onBatchScanResults(results);
            if (LOG_ENABLED) Log.v(TAG, "onBatchScanResults");
        }

        @Override
        public void onScanFailed(int errorCode) {
            super.onScanFailed(errorCode);
            if (LOG_ENABLED) Log.v(TAG, "onScanFailed");
        }
    };

    private Runnable scanPeriodicRestartRunnable = new Runnable() {
        @Override
        public void run() {
            if (LOG_ENABLED) Log.d(TAG, "scanPeriodicRestartRunnable");
            stopScan();
            startScan();
//            mScanPeriodicRestartHandler.postDelayed(scanPeriodicRestartRunnable, SCAN_PERIODIC_RESTART_PERIOD);
        }
    };

}
