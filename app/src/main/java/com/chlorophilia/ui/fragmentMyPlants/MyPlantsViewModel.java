package com.chlorophilia.ui.fragmentMyPlants;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

public class MyPlantsViewModel extends ViewModel {

    private MutableLiveData<String> mText;

    public MyPlantsViewModel() {
        mText = new MutableLiveData<>();
        mText.setValue("My plants");
    }

    public LiveData<String> getText() {
        return mText;
    }
}