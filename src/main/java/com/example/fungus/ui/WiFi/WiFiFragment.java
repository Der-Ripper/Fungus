package com.example.fungus.ui.WiFi;

import android.Manifest;
import android.annotation.SuppressLint;
import android.content.Context;
import android.content.pm.PackageManager;
import android.net.wifi.ScanResult;
import android.net.wifi.WifiManager;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.core.app.ActivityCompat;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;

import com.example.fungus.MainActivity;
import com.example.fungus.R;
import com.example.fungus.databinding.FragmentWifiBinding;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.android.material.snackbar.Snackbar;

import java.util.List;
import java.util.Objects;

public class WiFiFragment extends Fragment {

    private ListView netList;

    private final static int REQUEST_CODE_ASK_PERMISSIONS = 1;
    public void detectWifi() {
        WifiManager wifiManager = (WifiManager) Objects.requireNonNull(this.getActivity()).getSystemService(Context.WIFI_SERVICE);
        wifiManager.startScan();

        if (ActivityCompat.checkSelfPermission(Objects.requireNonNull(this.getContext()),
                Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED
                && ActivityCompat.checkSelfPermission(this.getContext(),
                Manifest.permission.ACCESS_COARSE_LOCATION) !=
                PackageManager.PERMISSION_GRANTED) {
            requestPermissions(new String[]{
                    Manifest.permission.ACCESS_FINE_LOCATION}, REQUEST_CODE_ASK_PERMISSIONS);
            Log.i("PPP", "User location NOT ENABLED, waiting for permission");
        } else {
            List<ScanResult> wifiList = wifiManager.getScanResults();
            WiFiElement[] wifiElements = new WiFiElement[wifiList.size()];
            for (int i = 0; i < wifiList.size(); i++) {
                String net = wifiList.get(i).toString();
                String[] net_data = net.split(",");
                String net_ssid = net_data[0].split(": ")[1];
                String net_protection = net_data[2].split(": ")[1];
                String net_level = net_data[3].split(": ")[1];
                wifiElements[i] = new WiFiElement(net_ssid, net_protection, net_level);
            }
            WiFiAdapter wiFiAdapter = new WiFiAdapter(this.getActivity(), wifiElements);
            wiFiAdapter.notifyDataSetChanged();
            netList.setAdapter(wiFiAdapter);
        }
    }

    @SuppressLint("ResourceType")
    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {

        View root = inflater.inflate(R.layout.fragment_wifi, container, false);
        netList = root.findViewById(R.id.list_wifi_items);
        FloatingActionButton button = root.findViewById(R.id.scanWiFi_but);
        button.setOnClickListener(view -> {
            Log.i("WIFI", "SCAN WI-FI");
            detectWifi();
            Snackbar.make(view, "Scanning Wi-Fi", Snackbar.LENGTH_LONG)
                    .setAction("Action", null).show();
        });
        return root;
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
    }
}