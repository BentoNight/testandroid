package com.example.testtask;

import android.content.Context;
import android.graphics.Color;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import org.jetbrains.annotations.NotNull;

import java.util.List;

public class UserDetailAdapter extends RecyclerView.Adapter<UserDetailAdapter.ViewHolder> {

    private final List<UserData> userDates;
    private final LayoutInflater inflater;
    private View view;
    private final OnClickListener callback;
    private Integer selectedPosition = -1;

    public interface OnClickListener {
        void onItemClicked(UserData data);
    }

    public UserDetailAdapter(Context context, List<UserData> userData, OnClickListener callback) {
        this.userDates = userData;
        this.inflater = LayoutInflater.from(context);
        this.callback = callback;
    }

    @NonNull
    @org.jetbrains.annotations.NotNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull @org.jetbrains.annotations.NotNull ViewGroup parent, int viewType) {
        view = inflater.inflate(R.layout.user_detail_list_item, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull @NotNull UserDetailAdapter.ViewHolder holder, int position) {
        UserData data = userDates.get(position);

        if (selectedPosition == position)
            holder.itemView.setBackgroundColor(Color.RED);
        else
            holder.itemView.setBackgroundColor(Color.GRAY);

        holder.first_name.setText(data.getFirstName());
        holder.last_name.setText(data.getLastName());
        holder.itemView.setOnClickListener(v -> {
            selectedPosition = position;
            notifyDataSetChanged();
            callback.onItemClicked(data);
        });
    }

    @Override
    public int getItemCount() {
        return userDates.size();
    }

    public static class ViewHolder extends RecyclerView.ViewHolder {
        final TextView first_name, last_name;
        ViewHolder(View view){
            super(view);
            first_name = view.findViewById(R.id.first_name);
            last_name = view.findViewById(R.id.last_name);
        }
    }
}
