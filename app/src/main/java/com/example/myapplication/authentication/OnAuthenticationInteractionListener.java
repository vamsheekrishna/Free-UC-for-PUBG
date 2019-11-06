package com.example.myapplication.authentication;

import com.example.myapplication.models.UserViewModel;

public interface OnAuthenticationInteractionListener {

    void goToValidateOTPFragment(UserViewModel userViewModel);

    void gotoHome();
    void goToFreeUCButtonScreen();
    void goToSignUpFragment();
    void goToPrivacyContent();

    void goToSignUpOptionScreen();
}
