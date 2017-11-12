package se.berg.thomas.thingshub;

import android.app.Service;
import android.bluetooth.BluetoothAdapter;
import android.bluetooth.BluetoothDevice;
import android.bluetooth.BluetoothGatt;
import android.bluetooth.BluetoothGattCallback;
import android.bluetooth.BluetoothGattCharacteristic;
import android.bluetooth.BluetoothGattDescriptor;
import android.bluetooth.BluetoothGattService;
import android.bluetooth.BluetoothManager;
import android.bluetooth.BluetoothProfile;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.content.pm.PackageManager;
import android.os.Binder;
import android.os.IBinder;
import android.util.Log;

import java.util.ArrayList;

import se.berg.thomas.commonfunclib.WalnutDevice;

import static android.bluetooth.BluetoothGatt.GATT_SUCCESS;

public class WalnutConfigBleService extends Service {

    private static final String TAG = "WalnutConfigBleService";
    private static final boolean LOG_ENABLED = true;

    private BluetoothAdapter mBluetoothAdapter;

    private final IBinder mBinder = new WalnutConfigBleService.LocalBinder();

    private int state = 0;
    private static final int WC_STATE_NONE = 0;
    private static final int WC_STATE_CONNECTING = 1;
    private static final int WC_STATE_CONNECTED = 2;

    private int receivedMessages;
    private int sentMessages;
    private int sentMessageCounter;

    private ArrayList<String> commandList = new ArrayList<>();

    public WalnutConfigBleService() {
    }

    public class LocalBinder extends Binder {
        WalnutConfigBleService getService() {
            return WalnutConfigBleService.this;
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
        boolean connect(BluetoothDevice bluetoothDevice) {
            if (LOG_ENABLED) Log.v(TAG, "connect()");
            state = WC_STATE_CONNECTING;

            commandList.add("W=0,0,1");
            commandList.add("W=0,1,3e8");
            commandList.add("W=0,2,4");
            commandList.add("W=0,fffe,fffe");

            bluetoothDevice.connectGatt(WalnutConfigBleService.this, false, bluetoothGattCallback);

            // TODO: Handle errors
            return true;
        }
    }

    @Override
    public void onCreate() {
        super.onCreate();
        if (LOG_ENABLED) Log.d(TAG, "onCreate");

        final BluetoothManager bluetoothManager = (BluetoothManager) getSystemService(BLUETOOTH_SERVICE);
        mBluetoothAdapter = bluetoothManager.getAdapter();
//        //mBluetoothLeScanner = mBluetoothAdapter.getBluetoothLeScanner();
//
//        mScanPeriodicRestartHandler = new Handler();
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
//            startScan();
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
//        stopScan();
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
//                    startScan();
                    break;
                case BluetoothAdapter.STATE_OFF:
                    if (LOG_ENABLED) Log.v(TAG, "Bluetooth state changed ... to OFF");
//                    stopScan();
                    break;
                default:
                    // Do nothing
            }

        }
    };

    private BluetoothGattCallback bluetoothGattCallback = new BluetoothGattCallback() {
        @Override
        public void onPhyUpdate(BluetoothGatt gatt, int txPhy, int rxPhy, int status) {
            super.onPhyUpdate(gatt, txPhy, rxPhy, status);
            Log.d(TAG, "onPhyUpdate:" + String.valueOf(status));
        }

        @Override
        public void onPhyRead(BluetoothGatt gatt, int txPhy, int rxPhy, int status) {
            super.onPhyRead(gatt, txPhy, rxPhy, status);
            Log.d(TAG, "onPhyRead:" + String.valueOf(status));
        }

        @Override
        public void onConnectionStateChange(BluetoothGatt gatt, int status, int newState) {
            super.onConnectionStateChange(gatt, status, newState);
            Log.d(TAG, "onConnectionStateChange:" + String.valueOf(status));

            if (status == GATT_SUCCESS) {

                switch (newState) {
                    case BluetoothProfile.STATE_CONNECTED:
                        Log.d(TAG, "newState=STATE_CONNECTED");
                        state = WC_STATE_CONNECTED;
                        receivedMessages = 0;
                        sentMessages = 0;
                        sentMessageCounter = 0;
                        boolean ret = gatt.discoverServices();
                        if (ret == false) {
                            Log.d(TAG, "onConnectionStateChange:cannot start service discovery");
                            // TODO: Analyse problem and retry
                        }
                        break;
                    case BluetoothProfile.STATE_DISCONNECTED:
                        Log.d(TAG, "newState=STATE_DISCONNECTED");
                        break;
                }
            }
        }

        @Override
        public void onServicesDiscovered(BluetoothGatt gatt, int status) {
            super.onServicesDiscovered(gatt, status);
            Log.d(TAG, "onServicesDiscovered:" + String.valueOf(status));

            if (status == GATT_SUCCESS) {
                BluetoothGattService configService = gatt.getService(WalnutDevice.WALNUT_CONFIG_SERVICE_UUID);
                BluetoothGattCharacteristic configChar = configService.getCharacteristic(WalnutDevice.WALNUT_CONFIG_TX_CHARACTERISTIC_UUID);

                gatt.setCharacteristicNotification(configChar, true);

                BluetoothGattDescriptor notificationDescriptor = configChar.getDescriptor(WalnutDevice.CLIENT_CHARACTERISTIC_CONFIG_UUID);
                notificationDescriptor.setValue(BluetoothGattDescriptor.ENABLE_NOTIFICATION_VALUE);
                gatt.writeDescriptor(notificationDescriptor);
            }
        }

        @Override
        public void onCharacteristicRead(BluetoothGatt gatt, BluetoothGattCharacteristic characteristic, int status) {
            super.onCharacteristicRead(gatt, characteristic, status);
            Log.d(TAG, "onCharacteristicRead:" + String.valueOf(status));
        }

        @Override
        public void onCharacteristicWrite(BluetoothGatt gatt, BluetoothGattCharacteristic characteristic, int status) {
            super.onCharacteristicWrite(gatt, characteristic, status);
            Log.d(TAG, "onCharacteristicWrite:" + String.valueOf(status));
        }

        @Override
        public void onCharacteristicChanged(BluetoothGatt gatt, BluetoothGattCharacteristic characteristic) {
            super.onCharacteristicChanged(gatt, characteristic);
            Log.d(TAG, "-------");
            Log.d(TAG, "onCharacteristicChanged");
            Log.d(TAG, characteristic.getStringValue(0));
            Log.d(TAG, "commandList.size():" + String.valueOf(commandList.size()));
            Log.d(TAG, "receivedMessages:" + String.valueOf(receivedMessages));
            Log.d(TAG, "sentMessageCounter:" + String.valueOf(sentMessageCounter));
            if (characteristic.getStringValue(0).equals("OK")) {
                Log.d(TAG, "received OK");
                receivedMessages++;
                Log.d(TAG, "receivedMessages:" + String.valueOf(receivedMessages));
                Log.d(TAG, "sentMessageCounter:" + String.valueOf(sentMessageCounter));
                if (receivedMessages < commandList.size()) {
                    BluetoothGattService configService = gatt.getService(WalnutDevice.WALNUT_CONFIG_SERVICE_UUID);
                    BluetoothGattCharacteristic configWriteChar = configService.getCharacteristic(WalnutDevice.WALNUT_CONFIG_RX_CHARACTERISTIC_UUID);

                    configWriteChar.setValue(commandList.get(sentMessageCounter));
                    gatt.writeCharacteristic(configWriteChar);
                } else {
                    gatt.disconnect();
                }
            } else if (characteristic.getStringValue(0).equals(commandList.get(sentMessageCounter))) {
                Log.d(TAG, "received sent message");
                sentMessageCounter++;
                Log.d(TAG, "receivedMessages:" + String.valueOf(receivedMessages));
                Log.d(TAG, "sentMessageCounter:" + String.valueOf(sentMessageCounter));
            }
        }

        @Override
        public void onDescriptorRead(BluetoothGatt gatt, BluetoothGattDescriptor descriptor, int status) {
            super.onDescriptorRead(gatt, descriptor, status);
            Log.d(TAG, "onDescriptorRead:" + String.valueOf(status));
        }

        @Override
        public void onDescriptorWrite(BluetoothGatt gatt, BluetoothGattDescriptor descriptor, int status) {
            super.onDescriptorWrite(gatt, descriptor, status);
            Log.d(TAG, "onDescriptorWrite:" + String.valueOf(status));

            if (status == GATT_SUCCESS) {
                // TODO: Check which descriptor was used, but so far we use only one
                BluetoothGattService configService = gatt.getService(WalnutDevice.WALNUT_CONFIG_SERVICE_UUID);
                BluetoothGattCharacteristic configWriteChar = configService.getCharacteristic(WalnutDevice.WALNUT_CONFIG_RX_CHARACTERISTIC_UUID);

                configWriteChar.setValue(commandList.get(sentMessageCounter));
                gatt.writeCharacteristic(configWriteChar);
            }
        }

        @Override
        public void onReliableWriteCompleted(BluetoothGatt gatt, int status) {
            super.onReliableWriteCompleted(gatt, status);
            Log.d(TAG, "onReliableWriteCompleted:" + String.valueOf(status));
        }

        @Override
        public void onReadRemoteRssi(BluetoothGatt gatt, int rssi, int status) {
            super.onReadRemoteRssi(gatt, rssi, status);
            Log.d(TAG, "onReadRemoteRssi:" + String.valueOf(status));
        }

        @Override
        public void onMtuChanged(BluetoothGatt gatt, int mtu, int status) {
            super.onMtuChanged(gatt, mtu, status);
            Log.d(TAG, "onMtuChanged:" + String.valueOf(status));
        }
    };

}
