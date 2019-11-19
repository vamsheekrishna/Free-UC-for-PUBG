package com.example.myapplication.fragments;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;

import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProviders;
import androidx.recyclerview.widget.RecyclerView;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.example.myapplication.R;
import com.example.myapplication.models.AppInfoModel;
import com.example.myapplication.models.AppInfos;
import com.example.myapplication.models.EarnMoneyViewModel;
import com.example.myapplication.models.Utilities;
import com.example.myapplication.server.MoreMoneyAPI;
import com.example.myapplication.server.MyRetrofit;
import com.example.myapplication.viewmodels.EarnMoneyAdapter;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class EarnMoneyFragment extends HomeBaseFragment implements View.OnClickListener {
    //private OnHomeFragmentInteractionListener mListener;
    RecyclerView recyclerView;
    EarnMoneyViewModel earnMoneyViewModel;
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
        earnMoneyViewModel = ViewModelProviders.of(getActivity()).get(EarnMoneyViewModel.class);
        mListener.setText(this.getTag());
        mListener.setText(String.valueOf(this.getTag()));
        View view = inflater.inflate(R.layout.fragment_earn_money, container, false);
        if(!Utilities.internetConnectionAvailable(30000)) {
            showDialog(null, R.drawable.install_apps, Objects.requireNonNull(getActivity()).getString(R.string.network_error_msg));
        }
        recyclerView = view.findViewById(R.id.earn_money);
        getRecycleViewDate();
       /* ArrayList<AppInfoModel> earnMoneyModels = new ArrayList<>();
        earnMoneyModels.add(new AppInfoModel("com.tencent.ig", "PUBG MOBILE", true));
        earnMoneyModels.add(new AppInfoModel("com.herogame.gplay.hopelessland", "Hopeless Land", false));
        earnMoneyModels.add(new AppInfoModel("com.titan.cd.gb", "Creative Destruction", true));
        earnMoneyModels.add(new AppInfoModel("com.dle.respawnables", "Respawnables - TPS Special Forces", false));
        EarnMoneyAdapter earnMoneyAdapter = new EarnMoneyAdapter(earnMoneyModels, this);
        recyclerView.setAdapter(earnMoneyAdapter);*/
       earnMoneyViewModel.getAppInfoModels().observe(getActivity(), new Observer<AppInfos>() {
           @Override
           public void onChanged(AppInfos appInfos) {

               if(null != appInfos) {
                   //Toast.makeText(getActivity(), "Response"+appInfos.toString(),Toast.LENGTH_SHORT).show();
                   recyclerView.setAdapter(new EarnMoneyAdapter(appInfos.getAppInfoModels(), EarnMoneyFragment.this));
               }
           }
       });
        return view;
    }

    private void updateRecycleView(Response<AppInfos> response) {
        AppInfos appInfos = response.body();
        earnMoneyViewModel.getAppInfoModels().setValue(appInfos);
    }

    private void getRecycleViewDate() {
        MoreMoneyAPI moreMoneyAPI = MyRetrofit.getInstance();
        Call<AppInfos> moreMoneyAPICall = moreMoneyAPI.getAllPhotos();
        moreMoneyAPICall.enqueue(new Callback<AppInfos>() {
            @Override
            public void onResponse(Call<AppInfos> call, Response<AppInfos> response) {
                updateRecycleView(response);
            }

            @Override
            public void onFailure(Call<AppInfos> call, Throwable t) {
                Log.d("onFailure","onFailure: "+t.getMessage());
                Toast.makeText(getActivity(), "onFailure"+t.getMessage(), Toast.LENGTH_SHORT).show();
            }
        });

        /*Call<List<AppInfoModel>> call = moreMoneyAPI.getAllPhotos();
        call.enqueue(new Callback<List<AppInfoModel>>() {
            @Override
            public void onResponse(Call<List<AppInfoModel>> call, Response<List<AppInfoModel>> response) {
                updateRecycleView(response);
            }


            @Override
            public void onFailure(Call<List<AppInfoModel>> call, Throwable t) {
                Log.d("onFailure","onFailure: "+t.getMessage());
                Toast.makeText(getActivity(), "onFailure"+t.getMessage(), Toast.LENGTH_SHORT).show();
            }
        });*/
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
        earnMoneyViewModel = null;
    }

    @Override
    public void onClick(View view) {
        AppInfoModel earnMoneyModel = (AppInfoModel) view.getTag();

        Toast.makeText(getActivity(), "App Name: "+earnMoneyModel.getName(),Toast.LENGTH_SHORT).show();

        Intent intent = new Intent(Intent.ACTION_VIEW);
        intent.setData(Uri.parse("market://details?id="+earnMoneyModel.getURL()));
        startActivity(intent);
        Utilities.updateCredit(1, getContext());
    }
}
