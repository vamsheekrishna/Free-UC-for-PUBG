package com.example.myapplication.fragments;

import android.content.Context;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.example.myapplication.R;
import com.example.myapplication.models.Utilities;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Objects;

public class DailyBonusFragment extends HomeBaseFragment implements View.OnClickListener {

    public DailyBonusFragment() {
        // Required empty public constructor
    }

    public static DailyBonusFragment newInstance() {
        return new DailyBonusFragment();
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        mListener.setText(String.valueOf(this.getTag()));
        String currentDate = new SimpleDateFormat("yyyy/MM/dd").format(new Date());
        View view;
        if(isNewDay(currentDate)) {
            Utilities.updateDateField(currentDate, getActivity());
            view = inflater.inflate(R.layout.congratulations_screen, container, false);
            ((TextView)view.findViewById(R.id.uc_count)).setText(String.format(getString(R.string.uc_count), 1));
        } else {
            view = inflater.inflate(R.layout.daily_bonus_error, container, false);
        }
        return view;
    }

    private boolean isNewDay(String currentDate) {
        String date = Objects.requireNonNull(Utilities.getUser(getActivity())).getDate();
        return null == date || !date.equals(currentDate);
    }

    /*public RewardedAd createAndLoadRewardedAd() {
        RewardedAd rewardedAd = new RewardedAd(Objects.requireNonNull(getActivity()),
                "ca-app-pub-3940256099942544/5224354917");
        RewardedAdLoadCallback adLoadCallback = new RewardedAdLoadCallback() {
            @Override
            public void onRewardedAdLoaded() {
                // Ad successfully loaded.
            }

            @Override
            public void onRewardedAdFailedToLoad(int errorCode) {
                // Ad failed to load.
            }
        };
        rewardedAd.loadAd(new AdRequest.Builder().build(), adLoadCallback);
        return rewardedAd;
    }*/
    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        /*Random r = new Random();
        Integer low = 100;
        Integer high = 200;
        Integer result = r.nextInt(high-low) + low;

        Utilities.updateCredit(result, getActivity());
        showDialog(result.toString(), R.drawable.daily_reward_icon, Objects.requireNonNull(getActivity()).getString(R.string.sucess_msg));*/
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        if (context instanceof OnHomeFragmentInteractionListener) {
            mListener = (OnHomeFragmentInteractionListener) context;
        } else {
            throw new RuntimeException(context.toString()
                    + " must implement OnHomeFragmentInteractionListener");
        }
    }

    @Override
    public void onDetach() {
        super.onDetach();
        mListener = null;
    }

    @Override
    public void onClick(View view) {
    }
}
