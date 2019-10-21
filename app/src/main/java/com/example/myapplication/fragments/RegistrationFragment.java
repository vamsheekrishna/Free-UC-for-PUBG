package com.example.myapplication.fragments;

import androidx.databinding.DataBindingUtil;

import android.content.Context;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.example.myapplication.R;
import com.example.myapplication.databinding.RegistrationFragmentBinding;
import com.example.myapplication.models.Utilities;
import com.example.myapplication.models.UserViewModel;

public class RegistrationFragment extends Fragment implements View.OnClickListener {

    private UserViewModel mViewModel = new UserViewModel();
    private OnHomeFragmentInteractionListener mListener;


    public static RegistrationFragment newInstance() {
        return new RegistrationFragment();
    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        mListener.setText(String.valueOf(this.getTag()));
        RegistrationFragmentBinding registrationFragmentBinding = DataBindingUtil.inflate(inflater,R.layout.registration_fragment,container,false);
        View view = registrationFragmentBinding.getRoot();//inflater.inflate(R.layout.registration_fragment, container, false);
        view.findViewById(R.id.submit).setOnClickListener(this);
        registrationFragmentBinding.setRegistration(mViewModel);
        return view;
    }

    @Override
    public void onClick(View view) {
       if(!TextUtils.isEmpty(mViewModel.getName()) && !TextUtils.isEmpty(mViewModel.getMobile())) {
            Utilities.generateProfile(getActivity(), mViewModel);
            mListener.gotoHomeFragment();
       } else {
           showToast();
       }
    }

    private void showToast() {
        Toast.makeText(getActivity(), "Please fill all fields",Toast.LENGTH_SHORT).show();
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
}
