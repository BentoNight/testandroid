package com.example.testtask.accounts;

import android.content.Intent;
import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.RecyclerView;

import com.example.testtask.R;
import com.example.testtask.accounts.accountdetails.AccountDetailsActivity;
import com.example.testtask.models.accounts.AccountData;

import java.util.List;

public class AccountActivity extends AppCompatActivity implements AccountContract.View, AccountAdapter.OnClickListener {

    private RecyclerView recyclerViewDetails;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.accounts);
        recyclerViewDetails = findViewById(R.id.accounts);
        AccountContract.Presenter presenter = new AccountPresenter(this);

        presenter.getUserDetails();
    }

    @Override
    public void showUsers(List<AccountData> accountDetails) {
        AccountAdapter adapter = new AccountAdapter(accountDetails, this::onItemClicked);
        recyclerViewDetails.setAdapter(adapter);
    }

    @Override
    public void onItemClicked(AccountData data) {
        Intent intent = new Intent(this, AccountDetailsActivity.class);

        intent.putExtra(AccountData.class.getSimpleName(), data);

        startActivity(intent);
    }
}
