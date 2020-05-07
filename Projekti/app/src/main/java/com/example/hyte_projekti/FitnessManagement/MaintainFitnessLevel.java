package com.example.hyte_projekti.FitnessManagement;

import androidx.appcompat.app.AppCompatActivity;

import android.app.Activity;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import com.example.hyte_projekti.General.Calculator;
import com.example.hyte_projekti.General.MainActivity;
import com.example.hyte_projekti.R;

/**
 * In this activity a user tells a program how many kilocalories he/she eats per day approximately.
 *
 * @author Tommi Vainio
 * @version 1.0
 */
public class MaintainFitnessLevel extends AppCompatActivity {
    /**
     * The value of this key is kilocalories eaten by user per week.
     */
    public static final String CALORIESEATENKEY = "SavedCaloriesEaten";
    private int age;
    private Double height;
    private Double weight;
    private String gender;
    private Calculator calculator;
    private EditText tvCaloriesEaten;
    private int caloriesEaten;
    private int rmr;

    /**
     * Retrieves user's age, height, weight and gender from sharedPreferences.
     * Creates new Calculator instance and puts the retrieved information as parameters.
     *
     * @param savedInstanceState
     * @see Calculator
     */
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_maintain_fitness_level);
        SharedPreferences prefGet = getSharedPreferences(MainActivity.KEY, Activity.MODE_PRIVATE);
        age = prefGet.getInt(MainActivity.AGEKEY, 0);
        height = Double.longBitsToDouble(prefGet.getLong(MainActivity.HEIGHTKEY, 0));
        weight = Double.longBitsToDouble(prefGet.getLong(MainActivity.WEIGHTKEY, 0));
        gender = prefGet.getString(MainActivity.GENDERKEY, "Not found.");
        calculator = new Calculator(age, height, weight, gender);
    }

    /**
     * Retrieves the value in the text field and saves it to sharedPreferences.
     * <p>
     * This method is called when Send button is clicked. If the text field is not empty and the value user entered
     * is not lower than his resting metabolic rate, the value is saved to sharedPreferences and
     * DaysActivity is started.
     *
     * @param view Send button that is clicked to perform this method.
     */
    public void caloriesEatenButtonPressed(View view){
        tvCaloriesEaten = findViewById(R.id.caloriesEatenText);
        String text = tvCaloriesEaten.getText().toString().trim();
        if(text.isEmpty() || text.length() == 0 || text.equals("")){
            Toast.makeText(this, "You can not leave this text field empty!", Toast.LENGTH_LONG).show();
        }else {
            caloriesEaten = Integer.parseInt(tvCaloriesEaten.getText().toString());
            rmr = calculator.getRmr();
            if (caloriesEaten > rmr) {
                SharedPreferences prefPut = getSharedPreferences(MainActivity.KEY, Activity.MODE_PRIVATE);
                SharedPreferences.Editor prefEditor = prefPut.edit();
                prefEditor.putInt(CALORIESEATENKEY, caloriesEaten);
                prefEditor.commit();
                Intent nextActivity = new Intent(MaintainFitnessLevel.this, DaysActivity.class);
                startActivity(nextActivity);
            } else {
                Toast.makeText(this, "Your resting metabolic rate is " + rmr + " kcal. You do not eat enough!", Toast.LENGTH_LONG).show();
            }
        }
    }
}
