package com.example.myapplication.authentication;

import android.content.Intent;
import android.os.Bundle;
import android.view.WindowManager;

import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import com.example.myapplication.R;
import com.example.myapplication.activitys.BaseActivity;
import com.example.myapplication.activitys.MainActivity;
import com.example.myapplication.models.UserViewModel;

public class AuthenticationActivity extends BaseActivity implements OnAuthenticationInteractionListener {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.base_layout);
        this.getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN); //show the activity in full screen
        fireBaseAds();
        FragmentManager fragmentManager = getSupportFragmentManager();
        FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
        fragmentTransaction.add(R.id.container , FreeUCButtonScreen.newInstance(), "FreeUCButtonScreen");
        fragmentTransaction.commit();
    }
    /*@Override
    protected void onStart() {
        super.onStart();
        refreshAd(false, true);
    }*/
    @Override
    public void goToValidateOTPFragment(UserViewModel userViewModel) {
        FragmentManager fragmentManager = getSupportFragmentManager();
        FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
        fragmentTransaction.replace(R.id.container ,ValidateOTPFragment.newInstance(userViewModel), "ValidateOTPFragment");
        fragmentTransaction.addToBackStack("ValidateOTPFragment");
        fragmentTransaction.commit();
    }

    @Override
    public void gotoHome() {
        Intent intent = new Intent(AuthenticationActivity.this, MainActivity.class);
        startActivity(intent);
        finish();
    }

    @Override
    public void goToFreeUCButtonScreen() {

    }

    @Override
    public void goToSignUpFragment() {
        FragmentManager fragmentManager = getSupportFragmentManager();
        FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
        fragmentTransaction.replace(R.id.container ,SignUpFragment.newInstance("", ""), "SignUpFragment");
        fragmentTransaction.addToBackStack("ValidateOTPFragment");
        fragmentTransaction.commit();
    }

    @Override
    public void goToPrivacyContent() {
        FragmentManager fragmentManager = getSupportFragmentManager();
        FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
        fragmentTransaction.replace(R.id.container ,PrivacyTextFragment.newInstance());
        fragmentTransaction.addToBackStack("PrivacyTextFragment");
        fragmentTransaction.commit();
    }

    @Override
    public void goToSignUpOptionScreen() {
        FragmentManager fragmentManager = getSupportFragmentManager();
        FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
        fragmentTransaction.replace(R.id.container ,SignUpOptionScreen.newInstance("",""), "SignUpOptionScreen");
        fragmentTransaction.addToBackStack("PrivacyTextFragment");
        fragmentTransaction.commit();
    }
}
