package com.chlorophilia.ui.fragmentWhatsnew;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

public class WhatsNewViewModel extends ViewModel {

    private MutableLiveData<String> mText;

    public WhatsNewViewModel() {
        mText = new MutableLiveData<>();
        mText.setValue("Notification are not managed yet !");
    }

    public LiveData<String> getText() {
        return mText;
    }
}