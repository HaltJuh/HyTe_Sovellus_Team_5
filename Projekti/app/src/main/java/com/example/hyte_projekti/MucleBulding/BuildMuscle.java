package com.example.hyte_projekti.MucleBulding;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import com.example.hyte_projekti.R;

/**
 * This activity is an introduction to the Muscle Building program.
 * @author Tino Kankkunen
 * @version 1.0
 */
public class BuildMuscle extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_build_muscle);


    }

    /**
     * This method is set as an onClick function for a button in the activity.
     * It starts the "MuscleDayList" activity.
     * @param view
     */
    public void goToMuscleDayList(View view) {
        Intent intentMuscleDayList = new Intent(this, MuscleDayList.class);
        startActivity(intentMuscleDayList);
    }
}
