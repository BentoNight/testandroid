package com.example.testtask.accounts.viewholders;

import android.view.View;
import android.widget.TextView;

import com.example.testtask.R;
import com.example.testtask.models.AccountDetail;

public class ViewHolderRub extends BaseViewHolder {
    final TextView name, price;

    public ViewHolderRub(View view) {
        super(view);
        price = view.findViewById(R.id.price);
        name = view.findViewById(R.id.name);
    }

    @Override
    public void bind(AccountDetail accountDetail) {
        price.setText(String.valueOf(accountDetail.getPrice()));
        name.setText(accountDetail.getName());
    }
}
