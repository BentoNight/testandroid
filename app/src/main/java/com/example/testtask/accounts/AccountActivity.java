package com.example.testtask.accounts;

import android.content.Intent;
import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.FragmentManager;
import androidx.recyclerview.widget.RecyclerView;
import com.example.testtask.R;
import com.example.testtask.models.AccountDetail;

import java.util.List;

public class AccountActivity extends AppCompatActivity implements AccountContract.View, AccountAdapter.OnClickListener {

    private AccountContract.Presenter presenter;
    private RecyclerView recyclerViewDetails;
    private FragmentManager fragmentManager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.accounts);
        recyclerViewDetails = findViewById(R.id.accounts);
        fragmentManager = getSupportFragmentManager();
        presenter = new AccountPresenter(this);

        presenter.getUserDetails();
    }

    @Override
    public void showUsers(List<AccountDetail> accountDetails) {
        AccountAdapter adapter = new AccountAdapter(this, accountDetails, this::onItemClicked);
        recyclerViewDetails.setAdapter(adapter);
    }

    @Override
    public void onItemClicked(AccountDetail data) {
        Intent intent = new Intent(this, AccountDetailsActivity.class);

        intent.putExtra(AccountDetail.class.getSimpleName(), data);

        startActivity(intent);
    }
}
