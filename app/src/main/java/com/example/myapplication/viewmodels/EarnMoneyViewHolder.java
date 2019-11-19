package com.example.myapplication.viewmodels;

import android.content.Context;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.myapplication.R;
import com.example.myapplication.models.AppInfoModel;
import com.squareup.picasso.MemoryPolicy;
import com.squareup.picasso.Picasso;

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

    public void bind(AppInfoModel earnMoneyModel) {
        appName.setText(earnMoneyModel.getName());
        Picasso.with(context).load(earnMoneyModel.getURL()).placeholder(R.drawable.ic_launcher_background).memoryPolicy(MemoryPolicy.NO_CACHE).into(appLogo);
        installedStatusView.setTag(earnMoneyModel);
        if(earnMoneyModel.isAppInstalled(context)) {
            installedStatusView.setImageDrawable(context.getResources().getDrawable(R.drawable.tick));
        } else {
            installedStatusView.setImageDrawable(context.getResources().getDrawable(R.drawable.download));
        }
    }
}
