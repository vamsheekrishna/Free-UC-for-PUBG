package com.example.myapplication.models;

import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import java.util.ArrayList;

public class EarnMoneyViewModel extends ViewModel {
    private MutableLiveData<AppInfos> appInfoModels;

    public MutableLiveData<AppInfos> getAppInfoModels() {

        if(null == appInfoModels) {
            appInfoModels = new MutableLiveData<>();
        }
        return appInfoModels;
    }

    public void setAppInfoModels(MutableLiveData<AppInfos> appInfoModels) {
        this.appInfoModels = appInfoModels;
    }
}
