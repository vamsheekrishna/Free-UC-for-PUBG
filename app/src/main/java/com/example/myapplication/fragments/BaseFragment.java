package com.example.myapplication.fragments;

import android.os.Bundle;
import android.os.Handler;

import androidx.annotation.Nullable;
import androidx.fragment.app.DialogFragment;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;

import java.util.Objects;

public class BaseFragment extends Fragment {
    private static final String DIALOG = "dialog";
    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        //Toast.makeText(getActivity(), String.valueOf(this.getTag()), Toast.LENGTH_SHORT).show();
    }


    void showDialog(String score, int bg_image, String title) {
        FragmentTransaction ft = Objects.requireNonNull(getActivity()).getSupportFragmentManager().beginTransaction();
        ft.addToBackStack(null);
        final DialogFragment dialogFragment = DialogBoxFragment.newInstance(title ,score, bg_image);
        dialogFragment.show(ft, DIALOG);

        int SPLASH_TIME_OUT = 3000;
        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                dialogFragment.dismiss();
                Objects.requireNonNull(getActivity()).onBackPressed();
            }
        }, SPLASH_TIME_OUT);
    }
}
