package com.example.myapplication.models;

import android.content.Context;
import android.content.SharedPreferences;
import android.view.View;

import androidx.databinding.BaseObservable;

import java.io.Serializable;
import java.util.Objects;

public class UserViewModel extends BaseObservable implements Serializable {
    private String name;
    private String mobile;
    private String email;
    private String date;
    private String royalPassDate;
    private String SpinnerDate;
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

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getDate() {
        return date;
    }

    public String getSpinnerDate() {
        return SpinnerDate;
    }

    public void setSpinnerDate(String spinnerDate) {
        SpinnerDate = spinnerDate;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public String getRoyalPassDate() {
        return royalPassDate;
    }

    public void setRoyalPassDate(String royalPassDate) {
        this.royalPassDate = royalPassDate;
    }
}
