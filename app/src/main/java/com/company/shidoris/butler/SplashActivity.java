package com.company.shidoris.butler;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import com.company.shidoris.butler.utils.DatabaseCUD;

public class SplashActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        Intent intent = new Intent(this, TestDatabaseActivity.class);
        startActivity(intent);
        finish();

    }
}
