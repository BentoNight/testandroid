package com.example.testtask.accounts;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.testtask.accounts.viewholders.BaseViewHolder;
import com.example.testtask.R;
import com.example.testtask.accounts.viewholders.ViewHolderEur;
import com.example.testtask.accounts.viewholders.ViewHolderRub;
import com.example.testtask.accounts.viewholders.ViewHolderUnknown;
import com.example.testtask.accounts.viewholders.ViewHolderUsd;
import com.example.testtask.models.accounts.AccountData;

import org.jetbrains.annotations.NotNull;

import java.util.List;

public class AccountAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {

    private final List<AccountData> accountDetails;
    private static final int RUB = 0;
    private static final int USD = 1;
    private static final int EUR = 2;
    private static final int UNKNOWN = 3;

    private final AccountAdapter.OnClickListener callback;

    public interface OnClickListener {
        void onItemClicked(AccountData accountDetail);
    }

    public AccountAdapter(List<AccountData> accountDetails, OnClickListener callback) {
        this.accountDetails = accountDetails;
        this.callback = callback;
    }

    @NonNull
    @NotNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull @NotNull ViewGroup parent, int viewType) {

        LayoutInflater inflater = LayoutInflater.from(parent.getContext());

        switch (viewType) {
            case RUB:
                return new ViewHolderRub(inflater.inflate(R.layout.account_item_rub, parent, false));
            case USD:
                return new ViewHolderUsd(inflater.inflate(R.layout.account_item_usd, parent, false));
            case EUR:
                return new ViewHolderEur(inflater.inflate(R.layout.account_item_eur, parent, false));
            case UNKNOWN:
            default:
                return new ViewHolderUnknown(inflater.inflate(R.layout.account_item_other, parent, false));
        }
    }

    @Override
    public void onBindViewHolder(@NonNull @NotNull RecyclerView.ViewHolder holder, int position) {
        AccountData accountDetail = accountDetails.get(position);
        BaseViewHolder viewHolder = (BaseViewHolder) holder;
        viewHolder.bind(accountDetail);
        viewHolder.itemView.setOnClickListener(v -> callback.onItemClicked(accountDetail));
    }

    @Override
    public int getItemViewType(int position) {
        AccountData.MyCurrency currency = accountDetails.get(position).getCurrency();
        switch (currency) {
            case RUB:
                return RUB;
            case USD:
                return USD;
            case EUR:
                return EUR;
            case UNKNOWN:
            default:
                return UNKNOWN;
        }
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public int getItemCount() {
        return accountDetails.size();
    }
}
