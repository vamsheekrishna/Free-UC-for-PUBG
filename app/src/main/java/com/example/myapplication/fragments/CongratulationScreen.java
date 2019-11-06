package com.example.myapplication.fragments;

import android.content.Context;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.example.myapplication.R;

public class CongratulationScreen extends HomeBaseFragment implements View.OnClickListener {
    private int mUC;
    public static final String UCS = "UCs";

    public CongratulationScreen() {
        // Required empty public constructor
    }

    public static CongratulationScreen newInstance(int uc) {
        CongratulationScreen freeRoyalPassScreen = new CongratulationScreen();
        Bundle bundle = new Bundle();
        bundle.putInt(UCS, uc);
        freeRoyalPassScreen.setArguments(bundle);
        return freeRoyalPassScreen;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if(null != getArguments()) {
            mUC = getArguments().getInt(UCS);
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        mListener.setText(String.valueOf(this.getTag()));
        View view = inflater.inflate(R.layout.congratulations_screen, container, false);
        ((TextView)view.findViewById(R.id.uc_count)).setText(String.format(getString(R.string.uc_count), mUC));
        return view;
    }
    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        /*Random r = new Random();
        Integer low = 100;
        Integer high = 200;
        Integer result = r.nextInt(high-low) + low;

        Utilities.updateCredit(result, getActivity());
        showDialog(result.toString(), R.drawable.daily_reward_icon, Objects.requireNonNull(getActivity()).getString(R.string.sucess_msg));*/
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
    }
}
