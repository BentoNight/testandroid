package com.example.testtask;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.splash);

        new Handler().postDelayed(() -> {
            Intent mainIntent = new Intent(MainActivity.this, Menu.class);
            MainActivity.this.startActivity(mainIntent);
            MainActivity.this.finish();
        }, 1000);
    }
}