package com.example.myapplication.models;

import android.content.Context;
import android.content.pm.PackageManager;

import com.google.gson.annotations.SerializedName;

import java.util.ArrayList;
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

    public void removeInstalledApps(Context context) {
        ArrayList<AppInfoModel> temp = new ArrayList<>(appInfoModels);
        PackageManager pm = context.getPackageManager();
        for (AppInfoModel appInfoModel:temp ) {
            if(isPackageInstalled(appInfoModel.getAppPackage(), pm)) {
                appInfoModels.remove(appInfoModel);
            }
        }
    }

    private boolean isPackageInstalled(String packageName, PackageManager packageManager) {
        try {
            packageManager.getPackageInfo(packageName, 0);
            return true;
        } catch (PackageManager.NameNotFoundException e) {
            return false;
        }
    }
}
