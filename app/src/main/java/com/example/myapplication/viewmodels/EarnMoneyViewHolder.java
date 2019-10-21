package com.example.myapplication.viewmodels;

import android.content.Context;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.myapplication.R;
import com.example.myapplication.models.EarnMoneyModel;

public class EarnMoneyViewHolder extends RecyclerView.ViewHolder {

    private ImageView appLogo;
    private TextView appName;
    public ImageView installedStatusView;
    private Context context;
    public EarnMoneyViewHolder(@NonNull View itemView, Context _context) {
        super(itemView);
        context = _context;
        appLogo = itemView.findViewById(R.id.app_image);
        appName = itemView.findViewById(R.id.app_name);
        installedStatusView = itemView.findViewById(R.id.status);
    }

    public void bind(EarnMoneyModel earnMoneyModel) {
        appName.setText(earnMoneyModel.getName());
        installedStatusView.setTag(earnMoneyModel);
        if(earnMoneyModel.isAppInstalled()) {
            installedStatusView.setImageDrawable(context.getResources().getDrawable(R.drawable.tick));
        } else {
            installedStatusView.setImageDrawable(context.getResources().getDrawable(R.drawable.download));
        }
    }
}
