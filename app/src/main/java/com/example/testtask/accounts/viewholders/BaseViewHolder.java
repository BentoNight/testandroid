package com.example.testtask.accounts.viewholders;

import android.view.View;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.testtask.models.AccountDetail;

import org.jetbrains.annotations.NotNull;

public abstract class BaseViewHolder extends RecyclerView.ViewHolder {

    public BaseViewHolder(@NonNull @NotNull View itemView) {
        super(itemView);
    }

    public abstract void bind(AccountDetail accountDetail);
}
