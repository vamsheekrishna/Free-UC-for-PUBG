package com.example.myapplication.activitys;
import android.os.Bundle;

import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import com.example.myapplication.R;
import com.example.myapplication.fragments.DailyBonusFragment;
import com.example.myapplication.fragments.EarnMoneyFragment;
import com.example.myapplication.fragments.HomeFragment;
import com.example.myapplication.fragments.InvitationLinkFragment;
import com.example.myapplication.fragments.OnHomeFragmentInteractionListener;
import com.example.myapplication.fragments.RegistrationFragment;
import com.example.myapplication.fragments.SampleAddFragment;
import com.example.myapplication.fragments.SpinnerBonusFragment;
import com.example.myapplication.models.Utilities;

import java.util.Objects;

public class MainActivity extends BaseActivity implements OnHomeFragmentInteractionListener {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        FragmentManager fragmentManager = getSupportFragmentManager();
        FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
        if(!Utilities.isNewUser(this)) {
            fragmentTransaction.add(R.id.root_content, RegistrationFragment.newInstance(), "Registration");
        } else {
            fragmentTransaction.add(R.id.root_content, HomeFragment.newInstance(), "Home");
        }
        fragmentTransaction.commit();
    }

    @Override
    public void gotoHomeFragment() {
        replaceFragment(HomeFragment.newInstance(),null, false);
    }

    @Override
    public void goToDailyBonus() {
        replaceFragment(DailyBonusFragment.newInstance(),getString(R.string.daily_bonus), true);
    }

    @Override
    public void goToSpinnerBonus() {
        replaceFragment(SpinnerBonusFragment.newInstance(),getString(R.string.spinner_bonus), true);
    }

    @Override
    public void goToEarnMoney() {
        replaceFragment(EarnMoneyFragment.newInstance(),getString(R.string.earn_money), true);
    }

    @Override
    public void goToInvitationLink() {
        replaceFragment(InvitationLinkFragment.newInstance(),getString(R.string.invitation_link), true);
    }

    public void setText(String text) {
        Objects.requireNonNull(getSupportActionBar()).setTitle(text);
    }

    @Override
    public void goToAddsDemo() {
        replaceFragment(SampleAddFragment.newInstance(),getString(R.string.adds_demo), true);
    }
}
