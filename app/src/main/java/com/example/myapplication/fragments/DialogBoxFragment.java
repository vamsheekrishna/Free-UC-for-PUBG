package com.example.myapplication.fragments;

import android.os.Bundle;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.databinding.DataBindingUtil;
import androidx.fragment.app.DialogFragment;

import com.example.myapplication.R;
import com.example.myapplication.databinding.FragmentMyCustomDialogBinding;
import com.example.myapplication.models.EnrollViewDataItem;

public class DialogBoxFragment extends DialogFragment {

    public static final String TITLE = "title";
    public static final String AMOUNT = "amount";
    public static final String RESOURCE_ID = "resourceID";
    EnrollViewDataItem enrollViewDataItem;
    public static DialogBoxFragment newInstance(String title, String amount, int resourceID) {
        Bundle args = new Bundle();
        args.putString(TITLE, title);
        args.putString(AMOUNT, amount);
        args.putInt(RESOURCE_ID, resourceID);
        DialogBoxFragment fragment = new DialogBoxFragment();
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        if(null != getArguments()) {
            enrollViewDataItem = new EnrollViewDataItem(getArguments().getString(TITLE), getArguments().getString(AMOUNT),getArguments().getInt(RESOURCE_ID));
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        FragmentMyCustomDialogBinding fragmentMyCustomDialogBinding = DataBindingUtil.inflate(inflater,
                R.layout.fragment_my_custom_dialog, container, false);
        View v = fragmentMyCustomDialogBinding.getRoot();
        fragmentMyCustomDialogBinding.setCongratsModel(enrollViewDataItem);
        v.setFocusableInTouchMode(true);
        v.requestFocus();
        v.setOnKeyListener(new View.OnKeyListener() {
            @Override
            public boolean onKey(View v, int keyCode, KeyEvent event) {
                if (event.getAction() == KeyEvent.ACTION_DOWN) {
                    return keyCode == KeyEvent.KEYCODE_BACK;
                }
                return false;
            }
        });
        return v;
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        Animation shake = AnimationUtils.loadAnimation(getActivity(), R.anim.shake);
        view.startAnimation(shake);
    }
}
