package com.example.hyte_projekti;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

public class BuildMuscle extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_build_muscle);


    }
    public void goToMuscleDayList(View view) {
        Intent intentMuscleDayList = new Intent(this, MuscleDayList.class);
        startActivity(intentMuscleDayList);
    }
}
