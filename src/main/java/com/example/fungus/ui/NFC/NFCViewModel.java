package com.example.fungus.ui.NFC;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

public class NFCViewModel extends ViewModel {

    private final MutableLiveData<String> mText;

    public NFCViewModel() {
        mText = new MutableLiveData<>();
        mText.setValue("This is NFC fragment");
    }

    public LiveData<String> getText() {
        return mText;
    }
}