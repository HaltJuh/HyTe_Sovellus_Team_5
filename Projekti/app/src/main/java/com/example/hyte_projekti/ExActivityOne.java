package com.example.hyte_projekti;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;

public class ExActivityOne extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_ex_one);
        Bundle b = getIntent().getExtras();
        int i = b.getInt(DaysActivity.EXTRA, 0);
    }
}
