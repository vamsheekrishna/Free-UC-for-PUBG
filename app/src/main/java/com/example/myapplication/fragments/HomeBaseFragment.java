package com.example.myapplication.fragments;

import android.content.Context;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

class HomeBaseFragment extends BaseFragment {

    protected OnHomeFragmentInteractionListener mListener;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public void onStart() {
        super.onStart();
        if(null != mListener) {
            mListener.enableBackArrow();
        }
    }

    @Override
    public void onResume() {
        super.onResume();
        mListener.setText(String.valueOf(this.getTag()));
    }

    @Override
    public void onAttach(@NonNull Context context) {
        super.onAttach(context);
    }
}
