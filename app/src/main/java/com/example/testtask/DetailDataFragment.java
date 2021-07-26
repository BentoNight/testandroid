package com.example.testtask;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.RecyclerView;

import com.google.android.material.textfield.TextInputEditText;
import org.jetbrains.annotations.NotNull;

public class DetailDataFragment extends Fragment {

    private UserData data;

    public DetailDataFragment(UserData data) {
        this.data = data;
    }

    @Nullable
    @org.jetbrains.annotations.Nullable
    @Override
    public View onCreateView(@NonNull @NotNull LayoutInflater inflater, @Nullable @org.jetbrains.annotations.Nullable ViewGroup container, @Nullable @org.jetbrains.annotations.Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.detail_list,
                                     container, false
        );

        TextInputEditText editText = view.findViewById(R.id.editName);
        RecyclerView recyclerView = view.findViewById(R.id.details);

        editText.setText(data.getName());

        DetailDataAdapter adapter = new DetailDataAdapter(getContext(), data);
        recyclerView.setAdapter(adapter);

        return view;
    }
}
