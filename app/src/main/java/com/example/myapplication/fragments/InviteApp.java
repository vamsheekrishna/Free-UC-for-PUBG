/*
 * Copyright (C) 2015 Google, Inc.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package com.example.myapplication.fragments;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;


import com.example.myapplication.BuildConfig;
import com.example.myapplication.R;
import com.example.myapplication.models.Utilities;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;
import java.util.Objects;

import static com.example.myapplication.fragments.DailyBonusFragment.DAYILY_UC_LIMIT;

public class InviteApp extends HomeBaseFragment implements View.OnClickListener {

    public InviteApp() {
        // Required empty public constructor
    }

    public static InviteApp newInstance() {
        return new InviteApp();
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
        View view = inflater.inflate(R.layout.invight_app_screen, container, false);
        view.findViewById(R.id.submit).setOnClickListener(this);
        return view;
    }

    /*private boolean isNewDay(String currentDate) {
        String date = Objects.requireNonNull(Utilities.getUser(getActivity())).getRoyalPassDate();
        return null == date || !date.equals(currentDate);
    }*/
    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
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
        try {
            Intent shareIntent = new Intent(Intent.ACTION_SEND);
            shareIntent.setType("text/plain");
            shareIntent.putExtra(Intent.EXTRA_SUBJECT, "My application name");
            String shareMessage= "\nLet me recommend you this application\n\n";
            shareMessage = shareMessage + "https://play.google.com/store/apps/details?id=" +"com.tencent.ig"/*BuildConfig.APPLICATION_ID*/ +"\n\n";
            shareIntent.putExtra(Intent.EXTRA_TEXT, shareMessage);
            startActivity(Intent.createChooser(shareIntent, "choose one"));

            Utilities.updateCredit(DAYILY_UC_LIMIT, getActivity());
        } catch(Exception e) {
            //e.toString();
        }
        //Objects.requireNonNull(getActivity()).onBackPressed();
    }
}
