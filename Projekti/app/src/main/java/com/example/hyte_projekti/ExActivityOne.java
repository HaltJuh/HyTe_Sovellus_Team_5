package com.example.hyte_projekti;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.widget.EditText;

public class ExActivityOne extends AppCompatActivity {

    private EditText weightText;
    private EditText calorieText;
    private int idealWeight;
    private int dailyCalories;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_ex_one);
        Bundle b = getIntent().getExtras();
        //int i = b.getInt(DaysActivity.EXTRA, 0);
        weightText = findViewById(R.id.weightView);
        calorieText = findViewById(R.id.calorieView);
    }
}
