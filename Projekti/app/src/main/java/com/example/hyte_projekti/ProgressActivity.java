package com.example.hyte_projekti;

import androidx.appcompat.app.AppCompatActivity;

import android.app.Activity;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.widget.TextView;

import static com.example.hyte_projekti.MainActivity.AGEKEY;
import static com.example.hyte_projekti.MainActivity.GENDERKEY;
import static com.example.hyte_projekti.MainActivity.HEIGHTKEY;
import static com.example.hyte_projekti.MainActivity.KEY;
import static com.example.hyte_projekti.MainActivity.WEIGHTKEY;

public class ProgressActivity extends AppCompatActivity {

    private TextView idealWeightView;
    private TextView currentWeightView;
    private TextView startingWeightView;
    private TextView kilosBurnedPerWeekView;
    private TextView weeksToLoseWeightView;
    private SharedPreferences prefGet;
    Calculator calculator;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_progress);
        prefGet = getSharedPreferences(KEY, Activity.MODE_PRIVATE);
        calculator = new Calculator(
                prefGet.getInt(AGEKEY,0),
                Double.longBitsToDouble(prefGet.getLong(HEIGHTKEY,0)),
                Double.longBitsToDouble(prefGet.getLong(WEIGHTKEY,0)),
                prefGet.getString(GENDERKEY,"Male")
        );
        idealWeightView = findViewById(R.id.idealWeight);
        currentWeightView = findViewById(R.id.currentWeight);
        startingWeightView = findViewById(R.id.startingWeight);
        kilosBurnedPerWeekView = findViewById(R.id.kilos);
        weeksToLoseWeightView = findViewById(R.id.weeks);
    }
}
