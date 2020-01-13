package com.example.myapplication.fragments;

import android.content.ActivityNotFoundException;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.databinding.DataBindingUtil;
import androidx.fragment.app.DialogFragment;

import com.example.myapplication.R;

import java.util.Objects;

public class RateUsBoxFragment extends DialogFragment implements View.OnClickListener {

    public static RateUsBoxFragment newInstance() {
        return new RateUsBoxFragment();
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.rate_us_dialog, container);
        v.findViewById(R.id.rate).setOnClickListener(this);
        v.findViewById(R.id.close_dialog).setOnClickListener(this);
        return v;
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        Animation shake = AnimationUtils.loadAnimation(getActivity(), R.anim.shake);
        view.startAnimation(shake);
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.rate:
                Uri uri = Uri.parse("market://details?id="+ Objects.requireNonNull(getActivity()).getApplicationContext().getPackageName());
                Intent myAppLinkToMarket = new Intent(Intent.ACTION_VIEW, uri);
                try {
                    startActivity(myAppLinkToMarket);
                } catch (ActivityNotFoundException e) {
                    //Toast.makeText(getActivity(), " unable to find market app", Toast.LENGTH_LONG).show();
                }
                dismiss();
                break;
            case R.id.close_dialog:
                dismiss();
                break;
        }
    }
}
