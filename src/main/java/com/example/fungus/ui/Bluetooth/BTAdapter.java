package com.example.fungus.ui.Bluetooth;

import android.app.Activity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import com.example.fungus.R;

public class BTAdapter extends ArrayAdapter<Object> {
    Activity context;
    private BluetoothElement[] bluetoothElements;

    public BTAdapter(Activity context, BluetoothElement[] bluetoothElements){
        super(context, R.layout.wifi_item, bluetoothElements);
        this.context = context;
        this.bluetoothElements = bluetoothElements;
    }

    public View getView(int position, View convertView, ViewGroup parent) {
        LayoutInflater inflater = context.getLayoutInflater();
        View item = inflater.inflate(R.layout.bluetooth_item, null);

        TextView btName = (TextView) item.findViewById(R.id.BT_NAME);
        btName.setText(bluetoothElements[position].getName());

        TextView btAddress = (TextView) item.findViewById(R.id.BT_MAC);
        btAddress.setText(bluetoothElements[position].getAddress());

        return item;
    }
}
