package com.example.fungus.ui.NFC;

import android.app.PendingIntent;
import android.content.Intent;
import android.content.IntentFilter;
import android.nfc.NfcAdapter;
import android.nfc.Tag;
import android.nfc.tech.MifareClassic;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;

import com.example.fungus.R;
import com.example.fungus.databinding.FragmentNfcBinding;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.android.material.snackbar.Snackbar;

import java.util.Arrays;

public class NFCFragment extends Fragment {
    PendingIntent pendingIntent;
    Intent intent;
    IntentFilter intentFilter;
    Tag tag;


    private void processIntent(Intent intent) {
        Tag tagFromIntent = intent.getParcelableExtra(NfcAdapter.EXTRA_TAG);
        for (String tech : tagFromIntent.getTechList()) {
            System.out.println(tech);
        }
        boolean auth = false;
        MifareClassic mfc = MifareClassic.get(tagFromIntent);
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
    }

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        View root = inflater.inflate(R.layout.fragment_nfc, container, false);
        /*FloatingActionButton button = root.findViewById(R.id.readNFC_but);
        button.setOnClickListener(view -> {
            Log.i("NFC", "READ NFC TAG");

            Snackbar.make(view, "READ NFC TAG", Snackbar.LENGTH_LONG)
                    .setAction("Action", null).show();
        });

        NfcAdapter nfcAdapter = NfcAdapter.getDefaultAdapter(getContext());*/



        return root;
    }



    @Override
    public void onDestroyView() {
        super.onDestroyView();
    }
}