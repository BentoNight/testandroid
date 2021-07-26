package com.example.testtask;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import org.jetbrains.annotations.NotNull;

import java.util.List;

public class DetailDataAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {

    private final List<DetailData> detailData;
    private final LayoutInflater inflater;
    private static final int RUB = 0;
    private static final int USD = 1;
    private static final int EUR = 2;
    private static final int UNKNOWN = 3;

    public DetailDataAdapter(Context context, UserData userData) {
        this.detailData = userData.getDetails();
        this.inflater = LayoutInflater.from(context);
    }

    @NonNull
    @NotNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull @NotNull ViewGroup parent, int viewType) {

        LayoutInflater inflater = LayoutInflater.from(parent.getContext());

        switch (viewType) {
            case RUB:
                return new ViewHolderRub(inflater.inflate(R.layout.detail_list_item_rub, parent, false));
            case USD:
                return new ViewHolderUsd(inflater.inflate(R.layout.detail_list_item_usd, parent, false));
            case EUR:
                return new ViewHolderEur(inflater.inflate(R.layout.detail_list_item_eur, parent, false));
            case UNKNOWN:
            default:
                return new ViewHolderUnknown(inflater.inflate(R.layout.detail_list_item_other, parent, false));
        }
    }

    @Override
    public void onBindViewHolder(@NonNull @NotNull RecyclerView.ViewHolder holder, int position) {
        DetailData data = detailData.get(position);

        if (holder instanceof ViewHolderRub) {
            final ViewHolderRub rubHolder = (ViewHolderRub) holder;
            rubHolder.price.setText(String.valueOf(data.getSum()));
        } else if (holder instanceof ViewHolderUsd) {
            final ViewHolderUsd usdHolder = (ViewHolderUsd) holder;
            usdHolder.price.setText(String.valueOf(data.getSum()));
        } else if (holder instanceof ViewHolderEur) {
            final ViewHolderEur eurHolder = (ViewHolderEur) holder;
            eurHolder.price.setText(String.valueOf(data.getSum()));
        } else {
            final ViewHolderUnknown unknownHolder = (ViewHolderUnknown) holder;
            unknownHolder.price.setText(String.valueOf(data.getSum()));
        }
    }

    @Override
    public int getItemViewType(int position) {

        DetailData.MyCurrency currency = detailData.get(position).getCurrency();

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
        return detailData.size();
    }

    public static class ViewHolderRub extends RecyclerView.ViewHolder {
        final TextView price;

        ViewHolderRub(View view) {
            super(view);
            price = view.findViewById(R.id.price);
        }
    }

    public static class ViewHolderUsd extends RecyclerView.ViewHolder {
        final TextView price;

        ViewHolderUsd(View view) {
            super(view);
            price = view.findViewById(R.id.price);
        }
    }

    public static class ViewHolderEur extends RecyclerView.ViewHolder {
        final TextView price;

        ViewHolderEur(View view) {
            super(view);
            price = view.findViewById(R.id.price);
        }
    }

    public static class ViewHolderUnknown extends RecyclerView.ViewHolder {
        final TextView price;

        ViewHolderUnknown(View view) {
            super(view);
            price = view.findViewById(R.id.price);
        }
    }
}
