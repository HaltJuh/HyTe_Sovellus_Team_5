package com.example.hyte_projekti;

import androidx.appcompat.app.AppCompatActivity;

import android.app.Activity;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

public class MuscleWpExerciseActivity extends AppCompatActivity {

    private int i;
    private int iEx;
    private int iTime;
    private int time;
    private TextView dayName;
    private TextView exName;
    private TextView exInfo;
    private int age;
    private Double height;
    private Double weight;
    private String gender;
    private Calculator calculator;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_muscle_wp_exercise);
        Bundle b = getIntent().getExtras();
        i =  b.getInt(WeekPlanActivity.EXTRAWP, 100);
        SharedPreferences prefGet = getSharedPreferences(MainActivity.KEY, Activity.MODE_PRIVATE);
        iEx = prefGet.getInt(Integer.toString(i), 100);
        iTime = (10 * i) + iEx + 10;
        time = prefGet.getInt(Integer.toString(iTime), 0);
        updateUI();
    }

    public void updateUI(){
        dayName = findViewById(R.id.whatDay);
        exName = findViewById(R.id.whatActivity);
        exInfo = findViewById(R.id.whatInfo);
        dayName.setText(DaysList.getInstance().getDay(i).getName());

        if(iEx == 100){
            exName.setText("There is nothing for this day.");
            exInfo.setText("");
        }else{
            exName.setText(GymExerciseList.getThisInstance().getGymExercise(iEx).getName() + " for " + time + " minutes");
            exInfo.setText(GymExerciseList.getThisInstance().getGymExercise(iEx).getInfo());
        }
    }

    public void doneButtonClicked(View view){
        if(iEx != 100) {
            SharedPreferences prefGet = getSharedPreferences(MainActivity.KEY, Activity.MODE_PRIVATE);
            age = prefGet.getInt(MainActivity.AGEKEY, 0);
            height = Double.longBitsToDouble(prefGet.getLong(MainActivity.HEIGHTKEY, 0));
            weight = Double.longBitsToDouble(prefGet.getLong(MainActivity.WEIGHTKEY, 0));
            gender = prefGet.getString(MainActivity.GENDERKEY, "Not found.");
            calculator = new Calculator(age, height, weight, gender);
            resetValue();
            Toast.makeText(this, "Great job! Workout complete!", Toast.LENGTH_LONG).show();
        }
        Intent nextActivity = new Intent(MuscleWpExerciseActivity.this, MuscleWeekPlanActivity.class);
        startActivity(nextActivity);
    }

    public void resetValue(){
        SharedPreferences prefPut = getSharedPreferences(MainActivity.KEY, Activity.MODE_PRIVATE);
        SharedPreferences.Editor prefEditor = prefPut.edit();
        prefEditor.putInt(Integer.toString(i), 100);
        prefEditor.putInt(Integer.toString(iTime), 0);
        prefEditor.commit();
    }
}
