package com.example.myapplication.fragments;

import android.app.ProgressDialog;
import android.content.Context;
import android.net.Uri;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;
import android.widget.RelativeLayout;
import android.widget.Toast;

import com.example.myapplication.R;
import com.example.myapplication.activitys.AddsDemoActivity;
import com.google.android.gms.ads.AdListener;
import com.google.android.gms.ads.AdRequest;
import com.google.android.gms.ads.AdSize;
import com.google.android.gms.ads.AdView;
import com.google.android.gms.ads.InterstitialAd;
import com.google.android.gms.ads.rewarded.RewardItem;
import com.google.android.gms.ads.rewarded.RewardedAd;
import com.google.android.gms.ads.rewarded.RewardedAdCallback;
import com.google.android.gms.ads.rewarded.RewardedAdLoadCallback;

import java.util.Objects;

public class SampleAddFragment extends BaseFragment implements View.OnClickListener {


    private OnHomeFragmentInteractionListener mListener;
    private FrameLayout mAddPlaceholder;
    private RelativeLayout mAddContent;
    ProgressDialog progressdialog;

    public SampleAddFragment() {
        // Required empty public constructor
    }

    public static SampleAddFragment newInstance() {
        return new SampleAddFragment();
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_sample_add, container, false);

        view.findViewById(R.id.close).setOnClickListener(this);
        view.findViewById(R.id.add_interstitial).setOnClickListener(this);
        view.findViewById(R.id.add_native).setOnClickListener(this);
        view.findViewById(R.id.add_rewarded).setOnClickListener(this);
        view.findViewById(R.id.add_rewarded_video).setOnClickListener(this);


        progressdialog = new ProgressDialog(getActivity());
        progressdialog.setMessage("Please Wait....");

        mAddPlaceholder = view.findViewById(R.id.add_placeholder);
        mAddContent = view.findViewById(R.id.content);

        return view;
    }

    private void showAddVideo() {
        progressdialog.show();
        //rewardedAd;
        final RewardedAdCallback adCallback = new RewardedAdCallback() {
            @Override
            public void onRewardedAdOpened() {
                Toast.makeText(Objects.requireNonNull(getActivity()), "onRewardedAdOpened", Toast.LENGTH_SHORT).show();
            }

            @Override
            public void onRewardedAdClosed() {
                // Ad closed.
                Toast.makeText(Objects.requireNonNull(getActivity()), "Ad closed.", Toast.LENGTH_SHORT).show();
            }

            @Override
            public void onUserEarnedReward(@NonNull RewardItem reward) {
                // User earned reward.
                Toast.makeText(Objects.requireNonNull(getActivity()), "User earned reward.", Toast.LENGTH_SHORT).show();
            }

            @Override
            public void onRewardedAdFailedToShow(int errorCode) {
                // Ad failed to display
                Toast.makeText(Objects.requireNonNull(getActivity()), "Ad failed to display.", Toast.LENGTH_SHORT).show();
            }
        };

        final RewardedAd rewardedAd = new RewardedAd(Objects.requireNonNull(getActivity()),
                "ca-app-pub-3940256099942544/5224354917");

        RewardedAdLoadCallback adLoadCallback = new RewardedAdLoadCallback() {
            @Override
            public void onRewardedAdLoaded() {
                // Ad successfully loaded.
                progressdialog.dismiss();
                rewardedAd.show(Objects.requireNonNull(getActivity()), adCallback);
                Toast.makeText(Objects.requireNonNull(getActivity()), "Ad successfully loaded.", Toast.LENGTH_SHORT).show();
            }

            @Override
            public void onRewardedAdFailedToLoad(int errorCode) {
                // Ad failed to load.
                progressdialog.dismiss();
                Toast.makeText(Objects.requireNonNull(getActivity()), "Ad failed to load.", Toast.LENGTH_SHORT).show();
            }
        };



        rewardedAd.loadAd(new AdRequest.Builder().build(), adLoadCallback);
    }


    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        if (context instanceof OnHomeFragmentInteractionListener) {
            mListener = (OnHomeFragmentInteractionListener) context;
        } else {
            throw new RuntimeException(context.toString()
                    + " must implement OnFragmentInteractionListener");
        }
    }

    @Override
    public void onDetach() {
        super.onDetach();
        mListener = null;
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.close:
                mAddContent.setVisibility(View.GONE);
                break;

            case R.id.add_placeholder:

                break;

            /*case R.id.add_banner:

                mAddContent.setVisibility(View.VISIBLE);
                mAddPlaceholder.removeAllViews();

                AdView adView = new AdView(Objects.requireNonNull(getActivity()));
                adView.setAdUnitId("ca-app-pub-3940256099942544/6300978111");
                adView.setAdSize(AdSize.SMART_BANNER);
                mAddPlaceholder.addView(adView);
                break;*/

            case R.id.add_interstitial:
                showInterstitialAdd();
                /*InterstitialAd interstitialAd = new InterstitialAd(Objects.requireNonNull(getActivity()));
                interstitialAd.setAdUnitId("ca-app-pub-3940256099942544/1033173712");
                interstitialAd.loadAd(new AdRequest.Builder().build());
                if (interstitialAd.isLoaded()) {
                    interstitialAd.show();
                } else {
                    Log.d("TAG", "The interstitial wasn't loaded yet.");
                }*/
                break;

            case R.id.add_native:

                break;

            case R.id.add_rewarded:

                break;

            case R.id.add_rewarded_video:
                showAddVideo();
                break;
        }
    }

    private void showInterstitialAdd() {

        final InterstitialAd mInterstitialAd = new InterstitialAd(Objects.requireNonNull(getActivity()));
        mInterstitialAd.setAdUnitId("ca-app-pub-3940256099942544/1033173712");
        mInterstitialAd.loadAd(new AdRequest.Builder().build());
        mInterstitialAd.setAdListener(new AdListener() {
            @Override
            public void onAdLoaded() {
                showInterstitial(mInterstitialAd);
                Toast.makeText(getActivity(), "onAdLoaded()", Toast.LENGTH_SHORT).show();
            }

            @Override
            public void onAdFailedToLoad(int errorCode) {
                Toast.makeText(getActivity(),
                        "onAdFailedToLoad() with error code: " + errorCode,
                        Toast.LENGTH_SHORT).show();
            }

            @Override
            public void onAdClosed() {
                //startGame();
                Toast.makeText(getActivity(),
                        "onAdClosed : ",
                        Toast.LENGTH_SHORT).show();
            }
        });

    }

    private void showInterstitial(InterstitialAd interstitialAd) {
        // Show the ad if it's ready. Otherwise toast and restart the game.
        if (interstitialAd != null && interstitialAd.isLoaded()) {
            interstitialAd.show();
        } else {
            Toast.makeText(getActivity(), "Ad did not load", Toast.LENGTH_SHORT).show();
            //startGame();
        }
    }
}
