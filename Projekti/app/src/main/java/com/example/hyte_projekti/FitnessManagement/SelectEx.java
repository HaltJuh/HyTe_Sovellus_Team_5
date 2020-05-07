package com.example.hyte_projekti.FitnessManagement;

import androidx.appcompat.app.AppCompatActivity;

import android.app.Activity;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.example.hyte_projekti.Calculator;
import com.example.hyte_projekti.FitnessManagement.DaysActivity;
import com.example.hyte_projekti.FitnessManagement.ExActivityTwo;
import com.example.hyte_projekti.FitnessManagement.ExercisesListTwo;
import com.example.hyte_projekti.MainActivity;
import com.example.hyte_projekti.R;

/**
 * In this activity a user can see information about an exercise he/she clicked.
 * The user can enter how long he/she will perform this exercise and add it to the week plan.
 *
 * @author Tommi Vainio
 * @version 1.0
 */
public class SelectEx extends AppCompatActivity {
    /**
     * The value of this key tells how many kilocalories selected exercise burn in total.
     */
    public static final String CALORIESBURNED = "SavedCaloriesBurned";
    private TextView name;
    private TextView info;
    private EditText time;
    private int i;
    private int timeInt;
    private int age;
    private Double height;
    private Double weight;
    private String gender;
    private Calculator calculator;
    private int calories;

    /**
     * Retrieves right text views and sets the name and the information of the exercise to them.
     * @param savedInstanceState
     */
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_select_ex);
        Bundle b = getIntent().getExtras();
        i =  b.getInt(ExActivityTwo.EXEXTRA, 0);
        name = findViewById(R.id.editName);
        info = findViewById(R.id.editInfo);
        name.setText(ExercisesListTwo.getInstance().getExercise(i).getName());
        info.setText(ExercisesListTwo.getInstance().getExercise(i).getInfo());
    }

    /**
     * Saves the value entered in the text field. Calculates how many kilocalories this exercise burns
     * when performed for this (entered value) amount of minutes and adds it to CALORIESBURNED.
     * <p>
     * Checks that text field is not empty and the entered value is not under 15. Also there should
     * not be any exercise already set for this day.
     *
     * @param view
     * @see Calculator
     * @see ExercisesListTwo
     */
    public void selectButtonPressed(View view){
        time = findViewById(R.id.editTime);
        String text = time.getText().toString().trim();
        if(text.isEmpty() || text.length() == 0 || text.equals("")){
            Toast.makeText(this, "Please enter the time.", Toast.LENGTH_LONG).show();
        }else {
            timeInt = Integer.parseInt(time.getText().toString());
            if (timeInt < 15) {
                Toast.makeText(this, "You need to do this activity longer!", Toast.LENGTH_LONG).show();
            }else{
                Bundle b = getIntent().getExtras();
                final int k = b.getInt(DaysActivity.EXTRA, 0);
                SharedPreferences prefGet = getSharedPreferences(MainActivity.KEY, Activity.MODE_PRIVATE);
                int isItEmpty = prefGet.getInt(Integer.toString(k), 100);
                if(isItEmpty == 100) {
                    int correctedI = (10 * k) + i + 10;
                    Double multiplier = ExercisesListTwo.getInstance().getExercise(i).getMetMultiplier();
                    calories = prefGet.getInt(CALORIESBURNED, 0);
                    age = prefGet.getInt(MainActivity.AGEKEY, 0);
                    height = Double.longBitsToDouble(prefGet.getLong(MainActivity.HEIGHTKEY, 0));
                    weight = Double.longBitsToDouble(prefGet.getLong(MainActivity.WEIGHTKEY, 0));
                    gender = prefGet.getString(MainActivity.GENDERKEY, "Not found.");
                    calculator = new Calculator(age, height, weight, gender);
                    int caloriesBurned = calculator.getCaloriesBurned(multiplier, timeInt);

                    SharedPreferences prefPut = getSharedPreferences(MainActivity.KEY, Activity.MODE_PRIVATE);
                    SharedPreferences.Editor prefEditor = prefPut.edit();
                    prefEditor.putInt(Integer.toString(k), i);
                    prefEditor.putInt(Integer.toString(correctedI), timeInt);
                    prefEditor.putInt(CALORIESBURNED, (calories + caloriesBurned));
                    prefEditor.commit();
                    Intent intent = new Intent(this, DaysActivity.class);
                    startActivity(intent);
                    Toast.makeText(this, "Information saved successfully.", Toast.LENGTH_LONG).show();
                }else{
                    Toast.makeText(this, "You have already selected this day's activity!", Toast.LENGTH_LONG).show();
                }
            }
        }
    }

}
