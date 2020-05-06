package com.example.hyte_projekti;

import androidx.appcompat.app.AppCompatActivity;

import android.app.Activity;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

/**
 * @author Tommi Vainio
 * @version 1.0
 */
public class WpExerciseActivity extends AppCompatActivity {
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

    /**
     * Retrieves the index of the exercise selected for this day and calculates the key for time
     * with help of the indexes of the exercise and the day of the week.
     * Then calls updateUI method.
     *
     * @param savedInstanceState
     */
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_wp_exercise);
        Bundle b = getIntent().getExtras();
        i =  b.getInt(WeekPlanActivity.EXTRAWP, 100);
        SharedPreferences prefGet = getSharedPreferences(MainActivity.KEY, Activity.MODE_PRIVATE);
        iEx = prefGet.getInt(Integer.toString(i), 100);
        iTime = (10 * i) + iEx + 10;
        time = prefGet.getInt(Integer.toString(iTime), 0);
        updateUI();
    }

    /**
     * Retrieves the name, the info text and the time for the exercise selected for this day.
     * If there is no exercises selected for this day, it is told.
     */
    public void updateUI(){
        dayName = findViewById(R.id.whatDay);
        exName = findViewById(R.id.whatActivity);
        exInfo = findViewById(R.id.whatInfo);
        dayName.setText(DaysList.getInstance().getDay(i).getName());

        if(iEx == 100){
            exName.setText("There is nothing for this day.");
            exInfo.setText("");
        }else{
            exName.setText(ExercisesListTwo.getInstance().getExercise(iEx).getName() + " for " + time + " minutes");
            exInfo.setText(ExercisesListTwo.getInstance().getExercise(iEx).getInfo());
        }
    }

    /**
     * If there is exercise for this day this method calculates calories burned and subtracts them
     * from the calories a user needs to burn this week. If there are less calories to burn than this
     * exercise burned, CALORIESREMAINING is set to 0. After that calls resetValue method and starts
     * WeekPlanActivity.
     *
     * @param view Done button that is clicked to perform this method.
     * @see Calculator
     * @see ExercisesListTwo
     */
    public void doneButtonClicked(View view){
        if(iEx != 100) {
            SharedPreferences prefGet = getSharedPreferences(MainActivity.KEY, Activity.MODE_PRIVATE);
            age = prefGet.getInt(MainActivity.AGEKEY, 0);
            height = Double.longBitsToDouble(prefGet.getLong(MainActivity.HEIGHTKEY, 0));
            weight = Double.longBitsToDouble(prefGet.getLong(MainActivity.WEIGHTKEY, 0));
            gender = prefGet.getString(MainActivity.GENDERKEY, "Not found.");
            calculator = new Calculator(age, height, weight, gender);
            Double multiplier = ExercisesListTwo.getInstance().getExercise(iEx).getMetMultiplier();
            int caloriesBurned = calculator.getCaloriesBurned(multiplier, time);
            int calories = prefGet.getInt(DaysActivity.CALORIESREMAINING, 0);
            SharedPreferences prefPut = getSharedPreferences(MainActivity.KEY, Activity.MODE_PRIVATE);
            SharedPreferences.Editor prefEditor = prefPut.edit();
            if (caloriesBurned < calories) {
                prefEditor.putInt(DaysActivity.CALORIESREMAINING, (calories - caloriesBurned));
                Toast.makeText(this, "Great job! You have burned about " + caloriesBurned + " kcal.", Toast.LENGTH_LONG).show();
            } else {
                prefEditor.putInt(DaysActivity.CALORIESREMAINING, 0);
                Toast.makeText(this, "Great job! You have burned about " + caloriesBurned + " kcal.", Toast.LENGTH_SHORT).show();
                Toast.makeText(this, "Excellent! You have reached your weekly goal!", Toast.LENGTH_LONG).show();
            }
            prefEditor.commit();
            resetValue();
        }else{
            Toast.makeText(this, "You have done nothing. Thanks for the information!", Toast.LENGTH_LONG).show();
        }
        Intent nextActivity = new Intent(WpExerciseActivity.this, WeekPlanActivity.class);
        startActivity(nextActivity);
    }

    /**
     * Resets this day's exercise value and this day/exercise combination's time value.
     */
    public void resetValue(){
        SharedPreferences prefPut = getSharedPreferences(MainActivity.KEY, Activity.MODE_PRIVATE);
        SharedPreferences.Editor prefEditor = prefPut.edit();
        prefEditor.putInt(Integer.toString(i), 100);
        prefEditor.putInt(Integer.toString(iTime), 0);
        prefEditor.commit();
    }
}
