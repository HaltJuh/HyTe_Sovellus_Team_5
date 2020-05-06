package com.example.hyte_projekti;

import androidx.appcompat.app.AppCompatActivity;

import android.app.Activity;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import static com.example.hyte_projekti.ExActivityOne.EXTRA_DAY_INDEX;
import static com.example.hyte_projekti.ExActivityOne.EXTRA_DAY_NAME;
import static com.example.hyte_projekti.ExActivityOne.EXTRA_DAY_TIME;
import static com.example.hyte_projekti.MainActivity.AGEKEY;
import static com.example.hyte_projekti.MainActivity.GENDERKEY;
import static com.example.hyte_projekti.MainActivity.HEIGHTKEY;
import static com.example.hyte_projekti.MainActivity.KEY;
import static com.example.hyte_projekti.MainActivity.WEEKLYCALORIESTOBURN;
import static com.example.hyte_projekti.MainActivity.WEIGHTKEY;

/**
 * This activity shows the user their exercise for the selected day
 * @author Juho Halttunen
 * @version 1.0
 */
public class DayActivity extends AppCompatActivity {

    private int time;
    private int dayIndex;
    private double met;
    private TextView dayText;
    private TextView exerciseText;
    private TextView infoText;
    private TextView timeText;
    private SharedPreferences prefGet;
    private Exercise exercise;
    private WeightLossList exercises;
    private Days day;
    Calculator calculator;

    /**
     * On create setups the correct layout and instantiates needed fields
     * @param savedInstanceState
     */
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Bundle b = getIntent().getExtras();
        time = b.getInt(EXTRA_DAY_TIME);
        if(time == 0){
            setContentView(R.layout.activity_day_empty);
            dayText = findViewById(R.id.day);
            dayText.setText(b.getString(EXTRA_DAY_NAME));
        }else{
            setContentView(R.layout.activity_day);
            dayIndex = b.getInt(EXTRA_DAY_INDEX);
            day = DaysList.getInstance().getDay(dayIndex);
            prefGet = getSharedPreferences(KEY, Activity.MODE_PRIVATE);
            exercises = WeightLossList.getInstance();
            exercise = exercises.getWeightLossExercise(prefGet.getInt(Integer.toString(day.getIndex()),0));
            dayText = findViewById(R.id.day);
            dayText.setText(b.getString(EXTRA_DAY_NAME));
            exerciseText = findViewById(R.id.exerciseName);
            exerciseText.setText(exercise.getName());
            infoText = findViewById(R.id.exerciseInfo);
            infoText.setText(exercise.getInfo());
            timeText = findViewById(R.id.exerciseTime);
            timeText.setText("For "+time+" minutes");
            calculator = new Calculator(
                    prefGet.getInt(AGEKEY,0),
                    Double.longBitsToDouble(prefGet.getLong(HEIGHTKEY,0)),
                    Double.longBitsToDouble(prefGet.getLong(WEIGHTKEY,0)),
                    prefGet.getString(GENDERKEY,"Male"));
        }

    }

    /**
     * Calculates how many calories the selected activity burns with the given time and reduces it from the weekly total.
     * Returns to the ExActivityOne activity
     * @param view
     * @retutn void
     * @See {@link #calculator}
     */
    public void onDone(View view){
        if(prefGet.getInt(day.getDoneKey(),0)==0){
            double burnedCalories = calculator.getCaloriesBurned(exercise.getMetMultiplier(),time);
            double weeklyCaloriesLeft = Double.longBitsToDouble(prefGet.getLong(WEEKLYCALORIESTOBURN,0))-burnedCalories;
            SharedPreferences.Editor prefEditor = prefGet.edit();
            prefEditor.putLong(WEEKLYCALORIESTOBURN,Double.doubleToLongBits(weeklyCaloriesLeft));
            prefEditor.putInt(day.getDoneKey(),1);
            prefEditor.commit();
            Intent intent = new Intent(DayActivity.this,ExActivityOne.class);
            startActivity(intent);
        }else {
            Toast.makeText(this,"You've already done the activity for this day.",Toast.LENGTH_SHORT).show();
        }
    }
}
