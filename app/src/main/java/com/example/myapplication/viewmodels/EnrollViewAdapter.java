package com.example.myapplication.viewmodels;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.databinding.DataBindingUtil;
import androidx.recyclerview.widget.RecyclerView;

import com.example.myapplication.R;
import com.example.myapplication.databinding.EnrollViewItemBinding;
import com.example.myapplication.models.EnrollViewDataItem;

import java.util.ArrayList;

public class EnrollViewAdapter extends RecyclerView.Adapter<EnrollViewHolder> {
    private View.OnClickListener mOnClickListener;
    private ArrayList<EnrollViewDataItem> enrollViewDataItems;
    public EnrollViewAdapter(View.OnClickListener onClickListener, Context context) {
        mOnClickListener = onClickListener;
        enrollViewDataItems = new ArrayList<>();
        enrollViewDataItems.add(new EnrollViewDataItem(context.getString(R.string.sucess_msg), context.getString(R.string.daily_bonus), R.drawable.dailybonus));
        enrollViewDataItems.add(new EnrollViewDataItem(context.getString(R.string.sucess_msg), context.getString(R.string.spinner_bonus), R.drawable.luckyspin));
        enrollViewDataItems.add(new EnrollViewDataItem(context.getString(R.string.sucess_msg), context.getString(R.string.earn_money), R.drawable.freeuc));
        enrollViewDataItems.add(new EnrollViewDataItem(context.getString(R.string.sucess_msg), context.getString(R.string.invitation_link), R.drawable.inviteearn));
    }
    @NonNull
    @Override
    public EnrollViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        EnrollViewItemBinding enrollViewItemBinding = DataBindingUtil.inflate(LayoutInflater.from(parent.getContext()), R.layout.enroll_view_item,
                parent, false);
        return new EnrollViewHolder(enrollViewItemBinding);
    }

    @Override
    public void onBindViewHolder(@NonNull EnrollViewHolder holder, int position) {
        holder.bind(enrollViewDataItems.get(position));
        holder.view.setOnClickListener(mOnClickListener);
    }

    @Override
    public int getItemCount() {
        return enrollViewDataItems.size();
    }
}
