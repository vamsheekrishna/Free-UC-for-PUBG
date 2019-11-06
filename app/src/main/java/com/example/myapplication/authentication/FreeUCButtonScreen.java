package com.example.myapplication.authentication;


import android.content.Context;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.myapplication.R;
import com.example.myapplication.fragments.BaseFragment;


/**
 * A simple {@link Fragment} subclass.
 * Use the {@link FreeUCButtonScreen#newInstance} factory method to
 * create an instance of this fragment.
 */
public class FreeUCButtonScreen extends BaseFragment {

    private OnAuthenticationInteractionListener onAuthenticationInteractionListener;

    public FreeUCButtonScreen() {
        // Required empty public constructor
    }

    public static FreeUCButtonScreen newInstance() {
        return new FreeUCButtonScreen();
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public void onAttach(@NonNull Context context) {
        super.onAttach(context);
        if(context instanceof  OnAuthenticationInteractionListener) {
            onAuthenticationInteractionListener = (OnAuthenticationInteractionListener) context;
        }
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        onAuthenticationInteractionListener = null;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_sign_up_screen_one, container, false);
        view.findViewById(R.id.submit).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                onAuthenticationInteractionListener.goToSignUpOptionScreen();
            }
        });
        return view;
    }

}
