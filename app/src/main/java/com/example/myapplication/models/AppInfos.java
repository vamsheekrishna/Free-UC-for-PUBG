package com.example.myapplication.models;

import com.google.gson.annotations.SerializedName;

import java.util.List;

public class AppInfos {
    @SerializedName("Apps")
    private
    List<AppInfoModel> appInfoModels;

    public List<AppInfoModel> getAppInfoModels() {
        return appInfoModels;
    }

    public void setAppInfoModels(List<AppInfoModel> appInfoModels) {
        this.appInfoModels = appInfoModels;
    }
}
