package com.example.myapplication.fragments;

import android.content.Context;
import android.os.Bundle;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.databinding.DataBindingUtil;

import com.example.myapplication.R;
import com.example.myapplication.databinding.FragmentHomeBinding;
import com.example.myapplication.models.Utilities;
import com.example.myapplication.models.EnrollViewDataItem;
import com.example.myapplication.viewmodels.EnrollViewAdapter;
import com.google.android.gms.ads.AdRequest;

import java.util.Objects;

public class HomeFragment extends BaseFragment implements View.OnClickListener {

    private OnHomeFragmentInteractionListener mListener;
    private com.google.android.gms.ads.AdView mAdView;

    public HomeFragment() {
        // Required empty public constructor
    }
    public static HomeFragment newInstance() {
        return new HomeFragment();
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
        FragmentHomeBinding fragmentHomeBinding =  DataBindingUtil.inflate(inflater,R.layout.fragment_home, container, false);
        View view = fragmentHomeBinding.getRoot();
        fragmentHomeBinding.setEnrollViewAdapter(new EnrollViewAdapter(this, Objects.requireNonNull(getActivity())));
        fragmentHomeBinding.setUserModel(Utilities.getUser(getActivity()));
        mAdView = view.findViewById(R.id.adView);
        AdRequest adRequest = new AdRequest.Builder().build();
        mAdView.loadAd(adRequest);
        return view;
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
        EnrollViewDataItem enrollViewDataItem = (EnrollViewDataItem) view.getTag();
        if(enrollViewDataItem.getName().equals(getString(R.string.daily_bonus))) {
            mListener.goToDailyBonus();
        } else if(enrollViewDataItem.getName().equals(getString(R.string.spinner_bonus))) {
            mListener.goToSpinnerBonus();
        } else if(enrollViewDataItem.getName().equals(getString(R.string.earn_money))) {
            mListener.goToEarnMoney();
        } else if(enrollViewDataItem.getName().equals(getString(R.string.invitation_link))) {
            mListener.goToInvitationLink();
        } else if(enrollViewDataItem.getName().equals(getString(R.string.adds_demo))) {
            mListener.goToAddsDemo();
        }
        //Toast.makeText(getActivity(), "Selected: "+enrollViewDataItem.getName(), Toast.LENGTH_SHORT).show();
    }
}
