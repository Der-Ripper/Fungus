package com.example.fungus;

import android.Manifest;
import android.app.Activity;
import android.bluetooth.BluetoothDevice;
import android.content.BroadcastReceiver;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.net.wifi.ScanResult;
import android.nfc.NfcAdapter;
import android.nfc.Tag;
import android.nfc.tech.MifareClassic;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.Menu;

import com.example.fungus.ui.WiFi.WiFiAdapter;
import com.example.fungus.ui.WiFi.WiFiElement;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.android.material.snackbar.Snackbar;
import com.google.android.material.navigation.NavigationView;

import androidx.core.app.ActivityCompat;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;
import androidx.navigation.ui.AppBarConfiguration;
import androidx.navigation.ui.NavigationUI;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.appcompat.app.AppCompatActivity;

import com.example.fungus.databinding.ActivityMainBinding;

import android.net.wifi.WifiManager;
import android.content.Context;
import android.net.wifi.ScanResult;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.TextView;

import java.util.Arrays;
import java.util.List;


public class MainActivity extends AppCompatActivity {

    private AppBarConfiguration mAppBarConfiguration;
    private ActivityMainBinding binding;

    private WifiManager wifiManager;
    private List<ScanResult> wifiList;
    private WiFiElement[] wifiElements;

    private final static int REQUEST_CODE_ASK_PERMISSIONS = 1;

    /*@Override
    protected void onNewIntent(Intent intent) {
        super.onNewIntent(intent);
        setIntent(intent);
        processIntent(intent);
    }

    private void processIntent(Intent intent) {
        Log.i("NFC", "AAAAAAAAAAAAAAAAAAAAAA-65");
        Tag tagFromIntent = intent.getParcelableExtra(NfcAdapter.EXTRA_TAG);
        for (String tech : tagFromIntent.getTechList()) {
            System.out.println(tech);
        }
        boolean auth = false;
        MifareClassic mfc = MifareClassic.get(tagFromIntent);
        Log.i("NFC", "AAAAAAAAAAAAAAAAAAAAAA-72");
        try {
            String metaInfo = "";
            //Enable I/O operations to the tag from this TagTechnology object.
            mfc.connect();
            int type = mfc.getType();
            int sectorCount = mfc.getSectorCount();
            String typeS = "";
            switch (type) {
                case MifareClassic.TYPE_CLASSIC:
                    typeS = "TYPE_CLASSIC";
                    break;
                case MifareClassic.TYPE_PLUS:
                    typeS = "TYPE_PLUS";
                    break;
                case MifareClassic.TYPE_PRO:
                    typeS = "TYPE_PRO";
                    break;
                case MifareClassic.TYPE_UNKNOWN:
                    typeS = "TYPE_UNKNOWN";
                    break;
            }
            metaInfo += "Card type：" + typeS + "n with" + sectorCount + " Sectorsn, "
                    + mfc.getBlockCount() + " BlocksnStorage Space: " + mfc.getSize() + "Bn";
            for (int j = 0; j < sectorCount; j++) {
                //Authenticate a sector with key A.
                auth = mfc.authenticateSectorWithKeyA(j,
                        MifareClassic.KEY_DEFAULT);
                int bCount;
                int bIndex;
                if (auth) {
                    metaInfo += "Sector " + j + ": Verified successfullyn";
                    bCount = mfc.getBlockCountInSector(j);
                    bIndex = mfc.sectorToBlock(j);
                    for (int i = 0; i < bCount; i++) {
                        byte[] data = mfc.readBlock(bIndex);
                        metaInfo += "Block " + bIndex + " : "
                                + Arrays.toString(data) + "n";
                        bIndex++;
                    }
                } else {
                    metaInfo += "Sector " + j + ": Verified failuren";
                }
            }
            Log.i("NFC", metaInfo);
        } catch (Exception e) {
            Log.i("NFC", "catch error: " + e);
        }
    }*/


    /*public void detectWifi() {
        this.wifiManager = (WifiManager) getSystemService(Context.WIFI_SERVICE);
        this.wifiManager.startScan();

        if (ActivityCompat.checkSelfPermission(this,
                Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED
                && ActivityCompat.checkSelfPermission(this,
                Manifest.permission.ACCESS_COARSE_LOCATION) !=
                PackageManager.PERMISSION_GRANTED) {
            requestPermissions(new String[]{
                    Manifest.permission.ACCESS_FINE_LOCATION}, REQUEST_CODE_ASK_PERMISSIONS);
            Log.i("PPP", "User location NOT ENABLED, waiting for permission");
        } else {
            this.wifiList = this.wifiManager.getScanResults();
            System.out.println(this.wifiList.toString());
            this.wifiElements = new WiFiElement[wifiList.size()];
            for (int i = 0; i < wifiList.size(); i++) {
                String net = wifiList.get(i).toString();
                String[] net_data = net.split(",");
                String net_ssid = net_data[0].split(": ")[1];
                String net_protection = net_data[2].split(": ")[1];
                String net_level = net_data[3].split(": ")[1];
                wifiElements[i] = new WiFiElement(net_ssid, net_protection, net_level);
            }

            WiFiAdapter wifiAdapter = new WiFiAdapter(this, wifiElements);
            ListView netList = findViewById(R.id.list_wifi_items);
            netList.setAdapter(wifiAdapter);
        }
    }*/

    /*private final BroadcastReceiver receiver = new BroadcastReceiver() {
        public void onReceive(Context context, Intent intent) {
            String action = intent.getAction();
            if (BluetoothDevice.ACTION_FOUND.equals(action)) {
                // Discovery has found a device. Get the BluetoothDevice
                // object and its info from the Intent.
                BluetoothDevice device = intent.getParcelableExtra(BluetoothDevice.EXTRA_DEVICE);
                if (ActivityCompat.checkSelfPermission(context, Manifest.permission.BLUETOOTH_CONNECT) != PackageManager.PERMISSION_GRANTED) {

                    return;
                }
                String deviceName = device.getName();
                String deviceHardwareAddress = device.getAddress(); // MAC address
                Log.i("A", deviceName);
            }
        }
    };*/

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        binding = ActivityMainBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        setSupportActionBar(binding.appBarMain.toolbar);

        FloatingActionButton button = findViewById(R.id.scanWiFi_but);

        /*button.setOnClickListener(view -> {
            System.out.println("-----");
            detectWifi();
            //netList.deferNotifyDataSetChanged();
            Snackbar.make(view, "Scanning Wi-Fi", Snackbar.LENGTH_LONG)
                    .setAction("Action", null).show();
        });*/

        /*button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                detectWifi();
                Snackbar.make(view, "Scanning Wi-Fi", Snackbar.LENGTH_LONG)
                        .setAction("Action", null).show();
            }
        });*/


        /*binding.appBarMain.fab.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View view) {
                //detectWifi();
                Snackbar.make(view, "Scanning Wi-Fi", Snackbar.LENGTH_LONG)
                        .setAction("Action", null).show();
            }
        });*/
        DrawerLayout drawer = binding.drawerLayout;
        NavigationView navigationView = binding.navView;
        // Passing each menu ID as a set of Ids because each
        // menu should be considered as top level destinations.
        mAppBarConfiguration = new AppBarConfiguration.Builder(
                R.id.nav_WiFi, R.id.nav_BT, R.id.nav_NFC)
                .setOpenableLayout(drawer)
                .build();
        NavController navController = Navigation.findNavController(this, R.id.nav_host_fragment_content_main);
        NavigationUI.setupActionBarWithNavController(this, navController, mAppBarConfiguration);
        NavigationUI.setupWithNavController(navigationView, navController);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.main, menu);
        return true;
    }

    @Override
    public boolean onSupportNavigateUp() {
        NavController navController = Navigation.findNavController(this, R.id.nav_host_fragment_content_main);
        return NavigationUI.navigateUp(navController, mAppBarConfiguration)
                || super.onSupportNavigateUp();
    }

    class WiFiAdapter1 extends ArrayAdapter<Object> {
        Activity context;

        public WiFiAdapter1(Activity context){
            super(context, R.layout.wifi_item, wifiElements);
            this.context = context;
        }

        public View getView(int position, View convertView, ViewGroup parent) {
            LayoutInflater inflater = context.getLayoutInflater();
            View item = inflater.inflate(R.layout.wifi_item, null);

            TextView tvSsid = (TextView) item.findViewById(R.id.SSID);
            tvSsid.setText(wifiElements[position].getName());

            TextView tvSecurity = (TextView) item.findViewById(R.id.protection_type);
            tvSecurity.setText(wifiElements[position].getProtection());

            TextView tvLevel = (TextView) item.findViewById(R.id.signal_level);
            tvLevel.setText(wifiElements[position].getLevel());

            return item;
        }
    }

}