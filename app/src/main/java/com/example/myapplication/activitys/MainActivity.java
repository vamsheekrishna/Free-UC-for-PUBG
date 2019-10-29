package com.example.myapplication.activitys;
import android.app.ProgressDialog;
import android.content.Context;
import android.os.Bundle;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import com.example.myapplication.BuildConfig;
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
import com.google.android.gms.ads.AdListener;
import com.google.android.gms.ads.AdRequest;
import com.google.android.gms.ads.InterstitialAd;
import com.google.android.gms.ads.rewarded.RewardItem;
import com.google.android.gms.ads.rewarded.RewardedAd;
import com.google.android.gms.ads.rewarded.RewardedAdCallback;
import com.google.android.gms.ads.rewarded.RewardedAdLoadCallback;

import java.util.Objects;

public class MainActivity extends BaseActivity implements OnHomeFragmentInteractionListener {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        com.google.android.gms.ads.AdView mAdView = findViewById(R.id.adView);
        AdRequest adRequest = new AdRequest.Builder().build();
        mAdView.loadAd(adRequest);

        FragmentManager fragmentManager = getSupportFragmentManager();
        FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
        fragmentTransaction.add(R.id.root_content, HomeFragment.newInstance(), "Home");
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

    @Override
    public void showInterstitialAdd() {

        final InterstitialAd mInterstitialAd = new InterstitialAd(Objects.requireNonNull(this));
        mInterstitialAd.setAdUnitId(BuildConfig.INTERSTITIAL_ADD);
        mInterstitialAd.loadAd(new AdRequest.Builder().build());
        mInterstitialAd.setAdListener(new AdListener() {
            @Override
            public void onAdLoaded() {
                showInterstitial(mInterstitialAd);
                Toast.makeText(MainActivity.this, "onAdLoaded()", Toast.LENGTH_SHORT).show();
            }

            @Override
            public void onAdFailedToLoad(int errorCode) {
                Toast.makeText(MainActivity.this,
                        "onAdFailedToLoad() with error code: " + errorCode,
                        Toast.LENGTH_SHORT).show();
            }

            @Override
            public void onAdClosed() {
                //startGame();
                /*Toast.makeText(MainActivity.this,
                        "onAdClosed : ",
                        Toast.LENGTH_SHORT).show();*/
                goToSpinnerBonus();
            }
        });

    }
    private void showInterstitial(InterstitialAd interstitialAd) {
        // Show the ad if it's ready. Otherwise toast and restart the game.
        if (interstitialAd != null && interstitialAd.isLoaded()) {
            interstitialAd.show();
        } else {
            Toast.makeText(MainActivity.this, "Ad did not load", Toast.LENGTH_SHORT).show();
            //startGame();
        }
    }

    @Override
    public void showAddVideo() {
        final ProgressDialog progressdialog = new ProgressDialog(this);
        progressdialog.setMessage("Please Wait....");
        progressdialog.show();
        //rewardedAd;
        final RewardedAdCallback adCallback = new RewardedAdCallback() {
            @Override
            public void onRewardedAdOpened() {
                Toast.makeText(Objects.requireNonNull(MainActivity.this), "onRewardedAdOpened", Toast.LENGTH_SHORT).show();
            }

            @Override
            public void onRewardedAdClosed() {
                // Ad closed.
                goToDailyBonus();
                Toast.makeText(Objects.requireNonNull(MainActivity.this), "Ad closed.", Toast.LENGTH_SHORT).show();
            }

            @Override
            public void onUserEarnedReward(@NonNull RewardItem reward) {
                // User earned reward.
                Toast.makeText(Objects.requireNonNull(MainActivity.this), "User earned reward.", Toast.LENGTH_SHORT).show();
            }

            @Override
            public void onRewardedAdFailedToShow(int errorCode) {
                // Ad failed to display
                Toast.makeText(Objects.requireNonNull(MainActivity.this), "Ad failed to display.", Toast.LENGTH_SHORT).show();
            }
        };

        final RewardedAd rewardedAd = new RewardedAd(Objects.requireNonNull(MainActivity.this),
                "ca-app-pub-3940256099942544/5224354917");

        RewardedAdLoadCallback adLoadCallback = new RewardedAdLoadCallback() {
            @Override
            public void onRewardedAdLoaded() {
                // Ad successfully loaded.
                progressdialog.dismiss();
                rewardedAd.show(Objects.requireNonNull(MainActivity.this), adCallback);
                Toast.makeText(Objects.requireNonNull(MainActivity.this), "Ad successfully loaded.", Toast.LENGTH_SHORT).show();
            }

            @Override
            public void onRewardedAdFailedToLoad(int errorCode) {
                // Ad failed to load.
                progressdialog.dismiss();
                Toast.makeText(Objects.requireNonNull(MainActivity.this), "Ad failed to load.", Toast.LENGTH_SHORT).show();
            }
        };



        rewardedAd.loadAd(new AdRequest.Builder().build(), adLoadCallback);
    }

}
