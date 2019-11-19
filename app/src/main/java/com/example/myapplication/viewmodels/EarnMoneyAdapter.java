package com.example.myapplication.viewmodels;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.myapplication.R;
import com.example.myapplication.models.AppInfoModel;

import java.util.ArrayList;
import java.util.List;


public class EarnMoneyAdapter extends RecyclerView.Adapter<EarnMoneyViewHolder> {

    private List<AppInfoModel> earnMoneyModels;
    private View.OnClickListener onClickListener;
    public EarnMoneyAdapter(List<AppInfoModel> _earnMoneyModels, View.OnClickListener _onClickListener) {
        earnMoneyModels = _earnMoneyModels;
        onClickListener = _onClickListener;

    }
    @NonNull
    @Override
    public EarnMoneyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.earn_money_row_item, parent, false);
        EarnMoneyViewHolder viewHolder = new EarnMoneyViewHolder(view, parent.getContext());
        viewHolder.installedStatusView.setOnClickListener(onClickListener);
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull EarnMoneyViewHolder holder, int position) {
        holder.bind(earnMoneyModels.get(position));
    }

    @Override
    public int getItemCount() {
        return earnMoneyModels.size();
    }
}
