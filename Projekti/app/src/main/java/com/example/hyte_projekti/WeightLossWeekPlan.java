package com.example.hyte_projekti;

import android.app.Activity;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.provider.Settings;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;


import java.util.Calendar;
import java.util.Date;

import static com.example.hyte_projekti.ExActivityOne.CALORIE_INTAKE;
import static com.example.hyte_projekti.ExActivityOne.IDEAL_WEIGHT;
import static com.example.hyte_projekti.MainActivity.AGEKEY;
import static com.example.hyte_projekti.MainActivity.CREATIONDAY;
import static com.example.hyte_projekti.MainActivity.GENDERKEY;
import static com.example.hyte_projekti.MainActivity.HEIGHTKEY;
import static com.example.hyte_projekti.MainActivity.KEY;
import static com.example.hyte_projekti.MainActivity.WEEKLYCALORIESTOBURN;
import static com.example.hyte_projekti.MainActivity.WEEKPLANKEY;
import static com.example.hyte_projekti.MainActivity.WEIGHTKEY;

public class WeightLossWeekPlan extends AppCompatActivity {

    public static final String TOTALCALORIETEXT= "The amount of kilocalories you\n should burn weekly: ";
    public static final String CURRENTCALORIETEXT = "The amount of kilocalories your\n current week plan burns: ";
    public static final String EXTRA_WEEK_INDEX = "weekIndex";
    public static final String CALORIESBURNEDWEEKLY = "CaloriesBurnedWeekly";
    private DaysList days;
    private WeightLossList exercises;
    private ListView weekList;
    private TextView totalCalorieView;
    private TextView currentCalorieView;
    private SharedPreferences prefGet;
    private Calculator calculator;
    private Calendar calendar;

    private int idealWeight;
    private double dailyCalories;
    private double caloriesToLosePerWeek;
    private double currentBurnedWeeklyCalories;
    private int weeksToLoseExtraWeight;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_weight_loss_week_planner);
        Bundle extras = getIntent().getExtras();
        days = DaysList.getInstance();
        exercises = WeightLossList.getInstance();
        weekList = findViewById(R.id.weekList);
        weekList.setAdapter(new ArrayAdapter<Days>(
                this,
                R.layout.days_item_layout,
                days.getDays())
        );
        prefGet = getSharedPreferences(KEY, Activity.MODE_PRIVATE);
        calculator = new Calculator(
                prefGet.getInt(AGEKEY,0),
                Double.longBitsToDouble(prefGet.getLong(HEIGHTKEY,0)),
                Double.longBitsToDouble(prefGet.getLong(WEIGHTKEY,0)),
                prefGet.getString(GENDERKEY,"Male")
        );
        idealWeight = prefGet.getInt(IDEAL_WEIGHT,0);
        dailyCalories = Double.longBitsToDouble(prefGet.getLong(CALORIE_INTAKE,0));
        Log.d("calories", ""+dailyCalories);
        caloriesToLosePerWeek = calculator.getCaloriesToBurnPerWeek(dailyCalories);
        weeksToLoseExtraWeight = calculator.getWeeksToLoseAllExtraWeight(idealWeight,dailyCalories);
        for(int i=0;i<days.getDays().size();i++){
            Days day = days.getDay(i);
            int time = prefGet.getInt(day.getSaveKey(),0);
            Exercise exercise = exercises.getWeightLossExercise(prefGet.getInt(Integer.toString(day.getIndex()),0));
            currentBurnedWeeklyCalories += calculator.getCaloriesBurned(exercise.getMetMultiplier(),time);
        }
        totalCalorieView = findViewById(R.id.totalWeeklyCaloriesView);
        currentCalorieView = findViewById(R.id.currentWeeklyCaloriesView);
        totalCalorieView.setText(TOTALCALORIETEXT+caloriesToLosePerWeek);
        currentCalorieView.setText(CURRENTCALORIETEXT+currentBurnedWeeklyCalories);
        Toast.makeText(this, "Estimated time to lose the extra weight\naccording to average values: "+weeksToLoseExtraWeight, Toast.LENGTH_SHORT).show();
        weekList.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int i, long id) {
                Log.d("Click",""+days.getDay(i).getName());
                Intent intent = new Intent(WeightLossWeekPlan.this,WeightLossExercises.class);
                intent.putExtra(EXTRA_WEEK_INDEX,i);
                startActivity(intent);
            }
        });
    }
    public void onDone(View view){
        SharedPreferences prefPut = getSharedPreferences(KEY,Activity.MODE_PRIVATE);
        SharedPreferences.Editor prefEditor = prefPut.edit();
        prefEditor.putInt(WEEKPLANKEY,1);
        prefEditor.putLong(WEEKLYCALORIESTOBURN,Double.doubleToLongBits(currentBurnedWeeklyCalories));
        prefEditor.putLong(CALORIESBURNEDWEEKLY,Double.doubleToLongBits(currentBurnedWeeklyCalories));
        calendar = Calendar.getInstance();
        calendar.setTime(new Date());
        prefEditor.putInt(CREATIONDAY,calendar.get(Calendar.DAY_OF_WEEK));
        prefEditor.commit();
        Log.d("Calendar",""+calendar.get(Calendar.DAY_OF_WEEK));
        Intent intent = new Intent(WeightLossWeekPlan.this,ExActivityOne.class);
        startActivity(intent);
    }
}
