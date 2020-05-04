package com.example.hyte_projekti;

import android.app.Activity;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import static com.example.hyte_projekti.MainActivity.AGEKEY;
import static com.example.hyte_projekti.MainActivity.GENDERKEY;
import static com.example.hyte_projekti.MainActivity.HEIGHTKEY;
import static com.example.hyte_projekti.MainActivity.KEY;
import static com.example.hyte_projekti.MainActivity.WEIGHTKEY;
import static com.example.hyte_projekti.WeightLossExercises.EXTRA_EXERCISE_INDEX;
import static com.example.hyte_projekti.WeightLossWeekPlan.EXTRA_WEEK_INDEX;

public class WeightLossExercise extends AppCompatActivity {

    private int exerciseIndex;
    private int dayIndex;
    private TextView dayText;
    private TextView exerciseText;
    private TextView infoText;
    private EditText timeText;
    private DaysList days;
    private Exercise exercise;
    private int time;
    private Calculator calculator;
    private SharedPreferences prefGet;
    private SharedPreferences prefPut;
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_weight_loss_exercise);
        Bundle extras = getIntent().getExtras();
        exerciseIndex = extras.getInt(EXTRA_EXERCISE_INDEX);
        dayIndex = extras.getInt(EXTRA_WEEK_INDEX);
        days = DaysList.getInstance();
        exercise = WeightLossList.getInstance().getWeightLossExercise(exerciseIndex);
        dayText = findViewById(R.id.day);
        exerciseText = findViewById(R.id.exercise);
        infoText = findViewById(R.id.exerciseInfo);
        timeText = findViewById(R.id.timeView);
        dayText.setText(days.getDay(dayIndex).getName());
        exerciseText.setText(exercise.getName());
        infoText.setText(exercise.getInfo());
        prefGet = getSharedPreferences(KEY, Activity.MODE_PRIVATE);
        calculator = new Calculator(
                prefGet.getInt(AGEKEY,0),
                Double.longBitsToDouble(prefGet.getLong(HEIGHTKEY,0)),
                Double.longBitsToDouble(prefGet.getLong(WEIGHTKEY,0)),
                prefGet.getString(GENDERKEY,"Male")
        );

    }
    private boolean checkIfOk(){
        String timeString = timeText.getText().toString();
        try {
            time = Integer.parseInt(timeString);
        }catch (NumberFormatException nfe){
            Log.d("Click","False");
            return false;
        }
        return true;
    }
    public void onClick(View view){
        Log.d("Click","Click");
        Toast.makeText(this,"Toast",Toast.LENGTH_SHORT);
        if(checkIfOk()){
            Log.d("Click",""+time);
            if(time >= 720){
                time = 720;
                Toast.makeText(this,"12 hours? Seriously",Toast.LENGTH_LONG).show();
            }else if(time < 15){
                time = 15;
            }
        }
        double burnedCalories = calculator.getCaloriesBurned(time/60,exercise.getMetMultiplier());
        prefPut = getSharedPreferences(KEY,Activity.MODE_PRIVATE);
        SharedPreferences.Editor prefEditor = prefGet.edit();
        prefEditor.putInt(days.getDay(dayIndex).getSaveKey(),time);
        prefEditor.putInt(Integer.toString(days.getDay(dayIndex).getIndex()),exerciseIndex);
        prefEditor.commit();
        Intent intent = new Intent(WeightLossExercise.this,WeightLossWeekPlan.class);
        startActivity(intent);
    }
}
