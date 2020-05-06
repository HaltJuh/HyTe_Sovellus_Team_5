package com.example.hyte_projekti;

import android.app.Activity;
import android.app.AlarmManager;
import android.app.PendingIntent;
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

/**
 * This activity shows the user a daylist from which they can choose on which days they'd like to exercise.
 * The user also sees a estimate on how many calories they should burn a week a calculated according to a healthy average.
 * After the done button is clicked ExActivityOne() activity is loaded.
 * @author Juho Halttunen
 * @version 1.0
 */
public class WeightLossWeekPlan extends AppCompatActivity {

    /**
     * The value of this String is used to get the extra that holds the index of the week day
     */
    public static final String EXTRA_WEEK_INDEX = "weekIndex";
    /**
     * This static String is used as a key to store the weekly burned calories.
     */
    public static final String CALORIESBURNEDWEEKLY = "CaloriesBurnedWeekly";
    private DaysList days;
    private WeightLossList exercises;
    private ListView weekList;
    private TextView totalCalorieView;
    private TextView currentCalorieView;
    private SharedPreferences prefGet;
    private Calculator calculator;
    private Calendar calendar;
    private String totalCalorieText = "The amount of kilocalories you\n should burn weekly: ";
    private String currentCalorieText = "The amount of kilocalories your\n current week plan burns: ";

    private int idealWeight;
    private double dailyCalories;
    private double caloriesToLosePerWeek;
    private double currentBurnedWeeklyCalories;

    /**
     * Instantiates important values
     * Calculates how many calories currently selected exercises burn weekly
     * Setups listview for the user to choose days
     * @param savedInstanceState
     * @return void
     * @see {@link #calculator}
     */
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
        for(int i=0;i<days.getDays().size();i++){
            Days day = days.getDay(i);
            int time = prefGet.getInt(day.getSaveKey(),0);
            Exercise exercise = exercises.getWeightLossExercise(prefGet.getInt(Integer.toString(day.getIndex()),0));
            currentBurnedWeeklyCalories += calculator.getCaloriesBurned(exercise.getMetMultiplier(),time);
        }
        totalCalorieView = findViewById(R.id.totalWeeklyCaloriesView);
        currentCalorieView = findViewById(R.id.currentWeeklyCaloriesView);
        totalCalorieView.setText(totalCalorieText+caloriesToLosePerWeek);
        currentCalorieView.setText(currentCalorieText+currentBurnedWeeklyCalories);
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

    /**
     * Calculates burned calories and saves the gained value into two fields in sharedpreferences.
     * Saves a value used to check if a week plan exists and a the value of the creation day.
     * When this method is called an AlarmManager communicates with the BroadcastReceiver class "Receiver" to engage a timer for a weekly popup notification.
     * This notification is a reminder for the user to view and create a new weekly plan by the end of the week.
     * Returns to the ExActivityOne activity
     * @param view
     * @see Receiver for popup notification
     * @return void
     */
    public void onDone(View view){
        Intent intentReceiver = new Intent(WeightLossWeekPlan.this, Receiver.class);
        PendingIntent pendingIntent = PendingIntent.getBroadcast(WeightLossWeekPlan.this, 0, intentReceiver, 0);
        AlarmManager am = (AlarmManager)getSystemService(ALARM_SERVICE);
        am.setRepeating(am.RTC_WAKEUP, System.currentTimeMillis(), am.INTERVAL_DAY*7, pendingIntent);                           // am.INTERVAL_DAY*7 for once a week!! Can use lower values for more frequent notifications!


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
