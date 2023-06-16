package com.example.fungus.ui.Bluetooth;

import android.Manifest;
import android.annotation.SuppressLint;
import android.bluetooth.BluetoothAdapter;
import android.bluetooth.BluetoothDevice;
import android.bluetooth.le.BluetoothLeScanner;
import android.bluetooth.le.ScanCallback;
import android.bluetooth.le.ScanResult;
import android.content.pm.PackageManager;
import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;

import androidx.annotation.MainThread;
import androidx.annotation.NonNull;
import androidx.annotation.RequiresApi;
import androidx.core.app.ActivityCompat;
import androidx.fragment.app.Fragment;

import com.example.fungus.R;
import com.example.fungus.ui.WiFi.WiFiAdapter;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.android.material.snackbar.Snackbar;

import java.util.ArrayList;
import java.util.List;

public class BluetoothFragment extends Fragment {

    //private BTAdapter bluetoothAdapter;
    private final static int REQUEST_CODE_ASK_PERMISSIONS = 1;

    //public BluetoothElement[] btList;
    public ListView btList;
    //BluetoothElement[] btElements;

    public ArrayList<BluetoothDevice> btDevices = new ArrayList<>();

    private final ScanCallback scanCallback = new ScanCallback() {
        @Override
        public void onScanResult(int callbackType, ScanResult result) {
            super.onScanResult(callbackType, result);
            Log.i("D", result.getDevice().getAddress());
            BluetoothDevice device = result.getDevice();
            if (ActivityCompat.checkSelfPermission(getContext(), Manifest.permission.BLUETOOTH_CONNECT) != PackageManager.PERMISSION_GRANTED) {
                Log.i("A", "PERF");
            }

            if(!btDevices.isEmpty()){
                for(BluetoothDevice btDevice: btDevices){
                    if(btDevice.equals(device)){
                        return;
                    }
                }
                btDevices.add(device);
            }
            else {
                btDevices.add(device);
            }

            /*Log.i("BT", device.getName() + " " + device.getAddress());
            Log.i("SET", String.valueOf(btDevices));

            BluetoothElement[] btElements = new BluetoothElement[btDevices.size()];
            for(int i = 0; i < btDevices.size(); i++){
                String name = btDevices.get(i).getName();
                String address = btDevices.get(i).getAddress();
                btElements[i] = new BluetoothElement(name, address);
            }
            Log.i("ELEM-s", String.valueOf(btElements.length));

            BTAdapter btAdapter = new BTAdapter(getActivity(), btElements);
            btAdapter.notifyDataSetChanged();
            btList.setAdapter(btAdapter);*/

        }

        @Override
        public void onBatchScanResults(List<ScanResult> results) {
            super.onBatchScanResults(results);
            Log.i("BATCH", "IN MAIN");
            for (ScanResult scanResult : results) {
                Log.i("SCANRES", scanResult.getDevice().getAddress());
            }
            // Ignore for now
        }

        @Override
        public void onScanFailed(int errorCode) {
            // Ignore for now
        }
    };


    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {


        View root = inflater.inflate(R.layout.fragment_bluetooth, container, false);

        btList = root.findViewById(R.id.list_bluetooth_items);

        FloatingActionButton button = (FloatingActionButton) root.findViewById(R.id.scanBT_but);
        button.setOnClickListener(new View.OnClickListener() {
            @RequiresApi(api = Build.VERSION_CODES.S)
            @Override
            public void onClick(View view) {
                Log.i("BT", "BLUETOTH BUT CLICKED");
                BluetoothAdapter bluetoothAdapter = BluetoothAdapter.getDefaultAdapter();
                BluetoothLeScanner scanner = bluetoothAdapter.getBluetoothLeScanner();

                if (scanner != null) {
                    if (ActivityCompat.checkSelfPermission(getContext(), Manifest.permission.BLUETOOTH_SCAN) != PackageManager.PERMISSION_GRANTED) {
                        requestPermissions(new String[]{Manifest.permission.BLUETOOTH_SCAN, Manifest.permission.BLUETOOTH_CONNECT}, REQUEST_CODE_ASK_PERMISSIONS);
                    }
                    Log.i("BT", "PRESCANNING");
                    scanner.startScan(scanCallback);
                    button.setVisibility(View.GONE);
                    new Handler().postDelayed(new Runnable() {
                        @Override
                        public void run() {
                            if (ActivityCompat.checkSelfPermission(getActivity(), Manifest.permission.BLUETOOTH_SCAN) != PackageManager.PERMISSION_GRANTED) {
                                Log.i("a", "a");
                            }
                            scanner.stopScan(scanCallback);
                            BluetoothElement[] btElements = new BluetoothElement[btDevices.size()];
                            for(int i = 0; i < btDevices.size(); i++){
                                String name = btDevices.get(i).getName();
                                String address = btDevices.get(i).getAddress();
                                btElements[i] = new BluetoothElement(name, address);
                            }
                            Log.i("ELEM-s", String.valueOf(btElements.length));

                            BTAdapter btAdapter = new BTAdapter(getActivity(), btElements);
                            btAdapter.notifyDataSetChanged();
                            btList.setAdapter(btAdapter);
                            btDevices.clear();
                            button.setVisibility(View.VISIBLE);
                        }
                    }, 5000);
                    Log.i("BT", "SCANNING");
                } else {
                    Log.i("BT", "NO scanning --");
                }

                /*Set<BluetoothDevice> paired = bluetoothAdapter.getBondedDevices();
                if(paired.size() > 0){
                    for (BluetoothDevice device: paired){
                        String deviceName = device.getName();
                        String deviceAddress = device.getAddress();
                        Log.i("DN", deviceName + " " + deviceAddress);
                    }
                }*/


                //Log.i("F", "SSSSS");
                /*
                BroadcastReceiver receiver = new BroadcastReceiver() {
                    public void onReceive(Context context, Intent intent) {
                        String action = intent.getAction();
                        if (BluetoothDevice.ACTION_FOUND.equals(action)) {

                            BluetoothDevice device = intent.getParcelableExtra(BluetoothDevice.EXTRA_DEVICE);
                            if (ActivityCompat.checkSelfPermission(context, Manifest.permission.BLUETOOTH_CONNECT) != PackageManager.PERMISSION_GRANTED) {
                                Log.i("A", "ERROR");
                                return;
                            }
                            String deviceName = device.getName();
                            String deviceHardwareAddress = device.getAddress(); // MAC address
                            Log.i("A", deviceName);
                        }
                    }
                };*/
                Snackbar.make(view, "///Scanning BLUETOOTH///", Snackbar.LENGTH_LONG)
                        .setAction("Action", null).show();
            }
        });
        return root;
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        //binding = null;
    }
}