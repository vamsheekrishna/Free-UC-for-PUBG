package com.example.myapplication.models;

import android.content.Context;
import android.content.pm.PackageManager;

import com.google.gson.annotations.SerializedName;

public class AppInfoModel {

    @SerializedName("appimage")
    private String URL;
    @SerializedName("appname")
    private String name;
    @SerializedName("apppackage")
    private String appPackage;


    private boolean isAppInstalled;

    public AppInfoModel(String URL, String name, boolean isAppInstalled) {
        this.URL = URL;
        this.name = name;
        this.isAppInstalled = isAppInstalled;
    }

    public String getURL() {
        return URL;
    }

    public void setURL(String URL) {
        this.URL = URL;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public boolean isAppInstalled(Context context) {
        PackageManager pm = context.getPackageManager();
        try {
            pm.getPackageInfo(getAppPackage(), PackageManager.GET_ACTIVITIES);
            return true;
        } catch (PackageManager.NameNotFoundException e) {
        }

        return false;
    }

    public void setAppInstalled(boolean appInstalled) {
        isAppInstalled = appInstalled;
    }

    public String getAppPackage() {
        return appPackage;
    }

    public void setAppPackage(String appPackage) {
        this.appPackage = appPackage;
    }
}
