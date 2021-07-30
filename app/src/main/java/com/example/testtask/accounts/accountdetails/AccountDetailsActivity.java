package com.example.testtask.accounts.accountdetails;

import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.provider.Settings;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;

import com.example.testtask.R;
import com.example.testtask.accounts.accountdetails.camera.CameraActivity;
import com.example.testtask.models.accounts.AccountData;
import com.google.android.material.textfield.TextInputEditText;

public class AccountDetailsActivity extends AppCompatActivity {

    private int REQUEST_CODE_PERMISSIONS = 1001;
    private final String[] REQUIRED_PERMISSIONS = new String[]{"android.permission.CAMERA", "android.permission.WRITE_EXTERNAL_STORAGE"};

    @Override
    protected void onCreate(@Nullable @org.jetbrains.annotations.Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.account_details);
        TextView name = findViewById(R.id.name);
        TextInputEditText editName = findViewById(R.id.editName);
        TextView description = findViewById(R.id.description);
        TextView price = findViewById(R.id.price);
        Button changeNameButton = findViewById(R.id.changeNameButton);
        Button enableCamera = findViewById(R.id.cameraButton);
        enableCamera.setOnClickListener((i) -> {
            if (hasPermissions()){
                enableCamera();
            } else{
                requestPermission();
            }
        });

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

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);

        if (requestCode == REQUEST_CODE_PERMISSIONS) {
            for (String permission : REQUIRED_PERMISSIONS){
                if (ContextCompat.checkSelfPermission(this, permission) == PackageManager.PERMISSION_DENIED) {
                    boolean showRationale = shouldShowRequestPermissionRationale( permission );
                    if (! showRationale) {
                        Toast.makeText(this, "Permission(s) not granted by the user", Toast.LENGTH_SHORT).show();
                        startActivity(new Intent(Settings.ACTION_SETTINGS));
                    }
                    else {
                        Toast.makeText(this, "Please try again and grant the required permissions", Toast.LENGTH_SHORT).show();
                    }
                }
            }
        }
    }

    private boolean hasPermissions() {

        for (String permission : REQUIRED_PERMISSIONS) {
            if (ContextCompat.checkSelfPermission(this, permission) == PackageManager.PERMISSION_DENIED)
                return false;
        }

        return true;
    }

    private void requestPermission() {
        ActivityCompat.requestPermissions(this, REQUIRED_PERMISSIONS, REQUEST_CODE_PERMISSIONS);
    }

    private void enableCamera() {
        Intent intent = new Intent(this, CameraActivity.class);
        startActivity(intent);
    }
}
