package com.example.myapplication.models;

import android.content.Context;
import android.content.SharedPreferences;
import android.view.View;

import androidx.databinding.BaseObservable;

import java.util.Objects;

public class UserViewModel extends BaseObservable {
    private String name = "Vamshee Krishna";
    private String mobile;
    private Integer score = 0;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getMobile() {
        return mobile;
    }

    public void setMobile(String mobile) {
        this.mobile = mobile;
    }

    public Integer getScore() {
        return score;
    }

    public void setScore(Integer score) {
        this.score = score;
    }
}
