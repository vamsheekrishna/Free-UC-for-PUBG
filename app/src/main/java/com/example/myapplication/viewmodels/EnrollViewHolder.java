package com.example.myapplication.viewmodels;

import android.view.View;

import androidx.recyclerview.widget.RecyclerView;

import com.example.myapplication.databinding.EnrollViewItemBinding;
public class EnrollViewHolder extends RecyclerView.ViewHolder {
    public final View view;
    private EnrollViewItemBinding mItemView;
    EnrollViewHolder(EnrollViewItemBinding enrollViewItemBinding) {
        super(enrollViewItemBinding.getRoot());
        mItemView = enrollViewItemBinding;
        view = enrollViewItemBinding.getRoot();
    }
    void bind(Object obj) {
        mItemView.setVariable(com.example.myapplication.BR.enrollViewItem, obj);
        view.setTag(obj);
    }
}
