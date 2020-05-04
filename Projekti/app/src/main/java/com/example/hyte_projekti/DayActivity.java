package com.example.hyte_projekti;

import androidx.appcompat.app.AppCompatActivity;

import android.app.Activity;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

import static com.example.hyte_projekti.ExActivityOne.EXTRA_DAY_INDEX;
import static com.example.hyte_projekti.ExActivityOne.EXTRA_DAY_NAME;
import static com.example.hyte_projekti.ExActivityOne.EXTRA_DAY_TIME;
import static com.example.hyte_projekti.MainActivity.AGEKEY;
import static com.example.hyte_projekti.MainActivity.GENDERKEY;
import static com.example.hyte_projekti.MainActivity.HEIGHTKEY;
import static com.example.hyte_projekti.MainActivity.KEY;
import static com.example.hyte_projekti.MainActivity.WEEKLYCALORIESTOBURN;
import static com.example.hyte_projekti.MainActivity.WEIGHTKEY;

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
    Calculator calculator;
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
            prefGet = getSharedPreferences(KEY, Activity.MODE_PRIVATE);
            exercises = WeightLossList.getInstance();
            exercise = exercises.getWeightLossExercise(prefGet.getInt(Integer.toString(dayIndex),0));
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
    public void onDone(View view){
        double burnedCalories = calculator.getCaloriesBurned(time/60,exercise.getMetMultiplier());
        double weeklyCaloriesLeft = Double.longBitsToDouble(prefGet.getLong(WEEKLYCALORIESTOBURN,0))-burnedCalories;
        SharedPreferences.Editor prefEditor = prefGet.edit();
        prefEditor.putLong(WEEKLYCALORIESTOBURN,Double.doubleToLongBits(weeklyCaloriesLeft));
        prefEditor.commit();
        Intent intent = new Intent(DayActivity.this,ExActivityOne.class);
        startActivity(intent);
    }
}
