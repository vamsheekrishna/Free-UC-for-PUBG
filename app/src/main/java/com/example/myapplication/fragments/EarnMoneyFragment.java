package com.example.myapplication.fragments;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;

import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.example.myapplication.R;
import com.example.myapplication.models.EarnMoneyModel;
import com.example.myapplication.models.Utilities;
import com.example.myapplication.viewmodels.EarnMoneyAdapter;

import java.util.ArrayList;

public class EarnMoneyFragment extends HomeBaseFragment implements View.OnClickListener {
    //private OnHomeFragmentInteractionListener mListener;

    public EarnMoneyFragment() {
        // Required empty public constructor
    }
    public static EarnMoneyFragment newInstance() {

        return new EarnMoneyFragment();
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        mListener.setText(this.getTag());
        mListener.setText(String.valueOf(this.getTag()));
        View view = inflater.inflate(R.layout.fragment_earn_money, container, false);
        if(!Utilities.internetConnectionAvailable(30000)) {
            showDialog(null, R.drawable.install_apps, getActivity().getString(R.string.network_error_msg));
        }
        RecyclerView recyclerView = view.findViewById(R.id.earn_money);
        ArrayList<EarnMoneyModel> earnMoneyModels = new ArrayList<>();
        earnMoneyModels.add(new EarnMoneyModel("com.tencent.ig", "PUBG MOBILE", true));
        earnMoneyModels.add(new EarnMoneyModel("com.herogame.gplay.hopelessland", "Hopeless Land", false));
        earnMoneyModels.add(new EarnMoneyModel("com.titan.cd.gb", "Creative Destruction", true));
        earnMoneyModels.add(new EarnMoneyModel("com.dle.respawnables", "Respawnables - TPS Special Forces", false));
        EarnMoneyAdapter earnMoneyAdapter = new EarnMoneyAdapter(earnMoneyModels, this);
        recyclerView.setAdapter(earnMoneyAdapter);
        return view;
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
        EarnMoneyModel earnMoneyModel = (EarnMoneyModel) view.getTag();

        Toast.makeText(getActivity(), "App Name: "+earnMoneyModel.getName(),Toast.LENGTH_SHORT).show();

        Intent intent = new Intent(Intent.ACTION_VIEW);
        intent.setData(Uri.parse("market://details?id="+earnMoneyModel.getURL()));
        startActivity(intent);
    }
}
