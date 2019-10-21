package com.example.myapplication.activitys;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.view.Window;
import android.view.WindowManager;

import com.example.myapplication.R;

public class SplashActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);


        //Remove title bar
        this.requestWindowFeature(Window.FEATURE_NO_TITLE);

        //Remove notification bar
        this.getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);
        setContentView(R.layout.activity_splash);

        int SPLASH_TIME_OUT = 3000;
        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                /*Intent intent;
                if (UserProfile.getInstance() != null) {
                    UserProfile.setInstance(null);
                }
                final UserProfile profile = new UserProfileManager(SplashScreen.this).getUserProfileObject(UserProfileManager.DEFAULT_USER);
                if (null == profile) {
                    intent = new Intent(SplashScreen.this, CreateAccount.class);
                    startActivity(intent);
                } else {
                    *//*if(BuildConfig.ENABLE_LANGUAGE_SELECTION) {
                        ((OmniTropeApplication)OmniTropeApplication.getContext()).updateLocale();
                        //SplashScreen.super.setLocale();
                    }*//*
                    UserProfile.setInstance(profile);
                    //Log.d("UserProfile", " test : SplashScreen setInstance");
                    Intent treatmentActivity = new Intent(SplashScreen.this, MyTreatmentActivity.class);
                    treatmentActivity.setFlags(Intent.FLAG_ACTIVITY_NO_ANIMATION);
                    startActivity(treatmentActivity);
                    if(BuildConfig.ENABLE_SET_APP_LOCK) {

                        Intent loginAccount = new Intent(SplashScreen.this, LoginAccount.class)
                                .addFlags(Intent.FLAG_ACTIVITY_REORDER_TO_FRONT);
                        startActivity(loginAccount);
                    }

                }*/
                Intent intent = new Intent(SplashActivity.this, MainActivity.class);
                startActivity(intent);
                finish();
            }
        }, SPLASH_TIME_OUT);
    }

}
