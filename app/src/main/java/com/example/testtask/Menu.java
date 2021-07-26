package com.example.testtask;

import android.os.Bundle;
import android.util.Log;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.FragmentManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

public class Menu extends AppCompatActivity implements UserDetailContract.View, UserDetailAdapter.OnClickListener {

    private UserDetailContract.Presenter presenter;
    private RecyclerView recyclerView;
    private FragmentManager fragmentManager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.main_screen);
        recyclerView = findViewById(R.id.user_details);

        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        presenter = new UserDetailPresenter(this);
        presenter.getUserDetails();

        fragmentManager = getSupportFragmentManager();
    }

    @Override
    public void showUsers(List<UserData> userData) {
        UserDetailAdapter adapter = new UserDetailAdapter(this, userData, this::onItemClicked);
        recyclerView.setAdapter(adapter);
    }

    @Override
    public void onItemClicked(UserData data) {
        DetailDataFragment fragment = new DetailDataFragment(data);

        fragmentManager.beginTransaction()
            .replace(R.id.fragment_container_view, fragment)
            .commit();

        Log.d("SuperTag", "НАЖАЛ: " + data.getId());
    }
}
