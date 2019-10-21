package com.example.myapplication.models;

public class EarnMoneyModel {

    private String URL = "";
    private String name = "Sample Application";
    private boolean isAppInstalled = false;

    public EarnMoneyModel(String URL, String name, boolean isAppInstalled) {
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

    public boolean isAppInstalled() {
        return isAppInstalled;
    }

    public void setAppInstalled(boolean appInstalled) {
        isAppInstalled = appInstalled;
    }
}
