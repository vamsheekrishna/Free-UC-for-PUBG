package com.example.myapplication.activitys;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Toast;

import com.example.myapplication.R;
import com.google.android.gms.ads.AdRequest;
import com.google.android.gms.ads.rewarded.RewardItem;
import com.google.android.gms.ads.rewarded.RewardedAd;
import com.google.android.gms.ads.rewarded.RewardedAdCallback;
import com.google.android.gms.ads.rewarded.RewardedAdLoadCallback;

public class AddsDemoActivity extends BaseActivity implements View.OnClickListener {

    private RewardedAd rewardedAd;
    RewardedAdCallback adCallback;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_adds_demo);
        findViewById(R.id.click_me).setOnClickListener(this);
        rewardedAd = new RewardedAd(this,
                "ca-app-pub-3940256099942544/5224354917");

        RewardedAdLoadCallback adLoadCallback = new RewardedAdLoadCallback() {
            @Override
            public void onRewardedAdLoaded() {
                // Ad successfully loaded.
                rewardedAd.show(AddsDemoActivity.this, adCallback);
                Toast.makeText(AddsDemoActivity.this, "Ad successfully loaded.", Toast.LENGTH_SHORT).show();
            }

            @Override
            public void onRewardedAdFailedToLoad(int errorCode) {
                // Ad failed to load.
                Toast.makeText(AddsDemoActivity.this, "Ad failed to load.", Toast.LENGTH_SHORT).show();
            }
        };

        adCallback = new RewardedAdCallback() {
            @Override
            public void onRewardedAdOpened() {
                Toast.makeText(AddsDemoActivity.this, "onRewardedAdOpened", Toast.LENGTH_SHORT).show();
            }

            @Override
            public void onRewardedAdClosed() {
                // Ad closed.
                Toast.makeText(AddsDemoActivity.this, "Ad closed.", Toast.LENGTH_SHORT).show();
            }

            @Override
            public void onUserEarnedReward(@NonNull RewardItem reward) {
                // User earned reward.
                Toast.makeText(AddsDemoActivity.this, "User earned reward.", Toast.LENGTH_SHORT).show();
            }

            @Override
            public void onRewardedAdFailedToShow(int errorCode) {
                // Ad failed to display
                Toast.makeText(AddsDemoActivity.this, "Ad failed to display.", Toast.LENGTH_SHORT).show();
            }
        };

        rewardedAd.loadAd(new AdRequest.Builder().build(), adLoadCallback);
    }

    @Override
    public void onClick(View view) {
        if (rewardedAd.isLoaded()) {


        } else {
            Log.d("TAG", "The rewarded ad wasn't loaded yet.");
        }
    }
}
