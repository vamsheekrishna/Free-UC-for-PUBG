package com.example.myapplication.fragments;

import android.content.Context;
import android.content.DialogInterface;
import android.net.Uri;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AlertDialog;
import androidx.databinding.DataBindingUtil;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.myapplication.R;
import com.example.myapplication.databinding.FragmentWalletBinding;
import com.example.myapplication.models.UserViewModel;
import com.example.myapplication.models.Utilities;

public class WalletFragment extends HomeBaseFragment implements View.OnClickListener {
    public WalletFragment() {
        // Required empty public constructor
    }
    public static WalletFragment newInstance() {
        return new WalletFragment();
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        FragmentWalletBinding fragmentWalletBinding =  DataBindingUtil.inflate(inflater, R.layout.fragment_wallet, container, false);
        fragmentWalletBinding.setUserModel(Utilities.getUser(getActivity()));
        return fragmentWalletBinding.getRoot();
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        view.findViewById(R.id.submit).setOnClickListener(this);
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
        UserViewModel temp = Utilities.getUser(getActivity());
        assert temp != null;
        int score = temp.getScore();
        if(score < 1000) {
            score = 1000;
        } else {
            score = ((score/1000)+1)*1000;

        }
        String text = "Minimum "+score+" UC required to Redeem.";
        new AlertDialog.Builder(getActivity())
                .setTitle("Sorry")
                .setMessage(text)

                // Specifying a listener allows you to take an action before dismissing the dialog.
                // The dialog is automatically dismissed when a dialog button is clicked.
                .setPositiveButton(android.R.string.yes, new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int which) {
                        // Continue with delete operation
                    }
                })

                .setIcon(android.R.drawable.ic_dialog_alert)
                .show();
    }
}
