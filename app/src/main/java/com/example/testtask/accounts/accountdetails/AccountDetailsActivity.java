package com.example.testtask.accounts.accountdetails;

import android.os.Bundle;
import android.widget.Button;
import android.widget.TextView;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.example.testtask.R;
import com.example.testtask.models.accounts.AccountData;
import com.google.android.material.textfield.TextInputEditText;

public class AccountDetailsActivity extends AppCompatActivity {

    @Override
    protected void onCreate(@Nullable @org.jetbrains.annotations.Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.account_details);
        TextView name = findViewById(R.id.name);
        TextInputEditText editName = findViewById(R.id.editName);
        TextView description = findViewById(R.id.description);
        TextView price = findViewById(R.id.price);
        Button changeNameButton = findViewById(R.id.changeNameButton);

        Bundle arguments = getIntent().getExtras();

        AccountData accountDetail;
        if (arguments != null) {
            accountDetail = (AccountData) arguments.getSerializable(AccountData.class.getSimpleName());

            name.setText(accountDetail.getName());
            editName.setText(accountDetail.getName());
            description.setText(accountDetail.getDescription());
            price.setText(String.valueOf(accountDetail.getPrice()));

            changeNameButton.setOnClickListener(v -> {
                accountDetail.setName(editName.getText().toString());
                name.setText(accountDetail.getName());
            });
        }
    }
}
