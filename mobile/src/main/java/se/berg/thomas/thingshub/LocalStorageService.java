package se.berg.thomas.thingshub;

import android.app.Service;
import android.bluetooth.le.ScanResult;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.IBinder;
import android.support.v4.content.LocalBroadcastManager;
import android.util.Log;

import se.berg.thomas.commonfunclib.WalnutDevice;

public class LocalStorageService extends Service {

    private static final String TAG = "LocalStorageService";

    private LocalBroadcastManager mLocalBroadcastManager;
    private BleBroadcastReceiver mBleReceiver = new BleBroadcastReceiver();

    public LocalStorageService() {
    }

    @Override
    public void onCreate() {
        super.onCreate();
        Log.d(TAG, "onCreate");
        IntentFilter intentFilter = new IntentFilter();
        intentFilter.addAction("se.lbhome.thomas.thingshub.ADV_MESSAGE");
        mLocalBroadcastManager = LocalBroadcastManager.getInstance(this);
        mLocalBroadcastManager.registerReceiver(mBleReceiver, intentFilter);
    }

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        Log.d(TAG, "onStartCommand");
        return super.onStartCommand(intent, flags, startId);
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        Log.d(TAG, "onDestroy");
        mLocalBroadcastManager.unregisterReceiver(mBleReceiver);
    }

    @Override
    public IBinder onBind(Intent intent) {
        // TODO: Return the communication channel to the service.
        throw new UnsupportedOperationException("Not yet implemented");
    }

    private class BleBroadcastReceiver extends BroadcastReceiver {

        @Override
        public void onReceive(Context context, Intent intent) {
            Log.v(TAG, "onReceive");
            if (intent.getAction().equals("se.lbhome.thomas.thingshub.ADV_MESSAGE")) {
                ScanResult scanResult = intent.getParcelableExtra("ADV");
                Log.d(TAG, scanResult.toString());

//                Log.v(TAG, "" + mWalnutList.size());

//                for (WalnutDevice w: mWalnutList) {
//                    if (w.getAddress().equals(scanResult.getDevice().getAddress())) {
//                        // Device is found, update it
//                        w.update(scanResult);
//                        mAdapter.notifyDataSetChanged();
//                        return;
//                    }
//                }
                // First device or device not found in list
//                WalnutDevice walnut = new WalnutDevice(MainActivity.this, scanResult);
//                mWalnutList.add(walnut);
//                mAdapter.notifyDataSetChanged();
            }

        }
    }

}
