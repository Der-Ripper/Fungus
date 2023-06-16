package com.example.fungus.ui.WiFi;

import android.app.Activity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import com.example.fungus.R;

public class WiFiAdapter extends ArrayAdapter<Object> {
    Activity context;
    private WiFiElement[] wifiElements;

    public WiFiAdapter(Activity context, WiFiElement[] wifiElements){
        super(context, R.layout.wifi_item, wifiElements);
        this.context = context;
        this.wifiElements = wifiElements;
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
