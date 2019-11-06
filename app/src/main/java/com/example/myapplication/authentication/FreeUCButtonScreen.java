package com.example.myapplication.authentication;


import android.content.Context;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.myapplication.R;


/**
 * A simple {@link Fragment} subclass.
 * Use the {@link FreeUCButtonScreen#newInstance} factory method to
 * create an instance of this fragment.
 */
public class FreeUCButtonScreen extends Fragment {

    OnAuthenticationInteractionListener onAuthenticationInteractionListener;
    public FreeUCButtonScreen() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment FreeUCButtonScreen.
     */
    // TODO: Rename and change types and number of parameters
    public static FreeUCButtonScreen newInstance(String param1, String param2) {
        FreeUCButtonScreen fragment = new FreeUCButtonScreen();
        return fragment;
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
