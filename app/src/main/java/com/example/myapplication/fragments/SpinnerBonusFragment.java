package com.example.myapplication.fragments;

import android.content.Context;
import android.os.Bundle;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import com.example.myapplication.R;
import com.example.myapplication.custom_views.spinningwheel.SpinningWheelView;
import com.example.myapplication.models.Utilities;

import java.util.Objects;

public class SpinnerBonusFragment extends HomeBaseFragment implements SpinningWheelView.OnRotationListener<String>{
    //private OnHomeFragmentInteractionListener mListener;

    public SpinnerBonusFragment() {
        // Required empty public constructor
    }

    public static SpinnerBonusFragment newInstance() {
        return new SpinnerBonusFragment();
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        final View view = inflater.inflate(R.layout.fragment_spinner_bonus, container, false);

        final SpinningWheelView wheelView = view.findViewById(R.id.wheel);

        Button rotate = view.findViewById(R.id.rotate);

        wheelView.setItems(R.array.dummy);
        wheelView.setOnRotationListener(this);

        rotate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // max angle 50
                // duration 10 second
                // every 50 ms rander rotation
                wheelView.rotate(50, 3000, 50);
            }
        });

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
    public void onRotation() {

    }

    @Override
    public void onStopRotation(String item) {
        String[] temp = item.split(" ");
        //Toast.makeText(getActivity(), item, Toast.LENGTH_LONG).show();
        Utilities.updateCredit(Integer.parseInt(temp[0]), getActivity());
        mListener.showCongratsFragment(Integer.parseInt(temp[0]));
        //Objects.requireNonNull(getActivity()).onBackPressed();
        //showDialog( temp[0],  R.drawable.wheel, Objects.requireNonNull(getActivity()).getString(R.string.sucess_msg));
    }

}
