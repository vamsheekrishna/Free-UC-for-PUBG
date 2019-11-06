package com.example.myapplication.authentication;

import android.content.Context;
import android.content.Intent;
import android.graphics.Paint;
import android.os.Bundle;

import android.text.TextUtils;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckBox;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.databinding.DataBindingUtil;

import com.example.myapplication.R;
import com.example.myapplication.databinding.FragmentSignUpBinding;
import com.example.myapplication.fragments.BaseFragment;
import com.example.myapplication.models.UserViewModel;
import com.example.myapplication.models.Utilities;
import com.google.android.gms.auth.api.signin.GoogleSignIn;
import com.google.android.gms.auth.api.signin.GoogleSignInAccount;
import com.google.android.gms.auth.api.signin.GoogleSignInClient;
import com.google.android.gms.auth.api.signin.GoogleSignInOptions;
import com.google.android.gms.common.SignInButton;
import com.google.android.gms.common.api.ApiException;
import com.google.android.gms.tasks.Task;

import java.util.Objects;

public class SignUpFragment extends BaseFragment implements View.OnClickListener {

    private UserViewModel mViewModel = new UserViewModel();

    private OnAuthenticationInteractionListener mListener;
    public SignUpFragment() {
        // Required empty public constructor
    }

    public static SignUpFragment newInstance(String param1, String param2) {
        SignUpFragment fragment = new SignUpFragment();
        /*Bundle args = new Bundle();
        args.putString(ARG_PARAM1, param1);
        args.putString(ARG_PARAM2, param2);
        fragment.setArguments(args);*/
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        /*if (getArguments() != null) {
            mParam1 = getArguments().getString(ARG_PARAM1);
            mParam2 = getArguments().getString(ARG_PARAM2);
        }*/
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        //mListener.setText(String.valueOf(this.getTag()));
        FragmentSignUpBinding FragmentGoogleAuthBindingImpl = DataBindingUtil.inflate(inflater,R.layout.fragment_sign_up,container,false);
        View view = FragmentGoogleAuthBindingImpl.getRoot();//inflater.inflate(R.layout.registration_fragment, container, false);


        view.findViewById(R.id.submit).setOnClickListener(this);

        // google sign in start
        FragmentGoogleAuthBindingImpl.setRegistration(mViewModel);

        return view;
    }

    @Override
    public void onClick(View view) {

        switch (view.getId()) {
            case R.id.sign_in_button:
                /*if(privacyClick.isChecked()) {
                    signIn();
                } else {
                    Toast.makeText(getActivity(), "Please Selected the Privacy text.", Toast.LENGTH_SHORT).show();
                }*/
                break;
            case R.id.submit:
                if(!TextUtils.isEmpty(mViewModel.getName()) && !TextUtils.isEmpty(mViewModel.getMobile())) {
                    mobileVerification();
                } else {
                    Toast.makeText(getActivity(), "Please enter all fields", Toast.LENGTH_SHORT).show();
                }
                break;
        }
    }

    private void mobileVerification() {
        //UserViewModel userViewModel  = new UserViewModel();
        mListener.goToValidateOTPFragment(mViewModel);
        //Toast.makeText(getActivity(), "mobileVerification", Toast.LENGTH_SHORT).show();
    }

    private void showToast() {
        Toast.makeText(getActivity(), "Please fill all fields",Toast.LENGTH_SHORT).show();
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        if (context instanceof OnAuthenticationInteractionListener) {
            mListener = (OnAuthenticationInteractionListener) context;
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

}
