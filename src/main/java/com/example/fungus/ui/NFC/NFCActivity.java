package com.example.fungus.ui.NFC;

import androidx.appcompat.app.AppCompatActivity;

import android.app.PendingIntent;
import android.content.Intent;
import android.content.IntentFilter;
import android.nfc.NfcAdapter;
import android.os.Bundle;
import android.util.Log;
import android.view.View;

import com.example.fungus.R;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.android.material.snackbar.Snackbar;

public class NFCActivity extends AppCompatActivity {
    NfcAdapter nfcAdapter;
    PendingIntent pendingIntent;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_nfcactivity);
        FloatingActionButton button = findViewById(R.id.readNFC_but);
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Log.i("NFC", "READ NFC TAG IN ACTIVITY");

                Snackbar.make(view, "READ NFC TAG IN ACTIVITY", Snackbar.LENGTH_LONG)
                        .setAction("Action", null).show();
            }
        });
        nfcAdapter = NfcAdapter.getDefaultAdapter(this);
        readFromTag(getIntent());
        pendingIntent = PendingIntent.getActivity(this, 0, new Intent(this, getClass()).addFlags(Intent.FLAG_ACTIVITY_SINGLE_TOP), 0);
        IntentFilter tagDetected = new IntentFilter(NfcAdapter.ACTION_TAG_DISCOVERED);
        tagDetected.addCategory(Intent.CATEGORY_DEFAULT);


    }

    private void readFromTag(Intent intent){
        Log.i("NFC", "___QWQ_WQ_W_QW_QW________________!@!@!@!@!@");
    }

}