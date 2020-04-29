package com.example.hyte_projekti;

import androidx.appcompat.app.AppCompatActivity;

import android.app.Activity;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

public class MaintainFitnessLevel extends AppCompatActivity {
    public static final String CALORIESEATENKEY = "SavedCaloriesEaten";
    private int age;
    private Double height;
    private Double weight;
    private String gender;
    private Calculator calculator;
    private TextView tvCaloriesEaten;
    private int caloriesEaten;
    private int rmr;
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

    public void caloriesEatenButtonPressed(View view){
        tvCaloriesEaten = findViewById(R.id.caloriesEatenText);
        caloriesEaten = Integer.parseInt(tvCaloriesEaten.getText().toString());
        rmr = calculator.getRmr();
        if(caloriesEaten > rmr){
            SharedPreferences prefPut = getSharedPreferences(MainActivity.KEY, Activity.MODE_PRIVATE);
            SharedPreferences.Editor prefEditor = prefPut.edit();
            prefEditor.putInt(CALORIESEATENKEY, caloriesEaten);
            prefEditor.commit();
            Intent nextActivity = new Intent(MaintainFitnessLevel.this, DaysActivity.class);
            startActivity(nextActivity);
        }else{
            Toast.makeText(this, "Your resting metabolic rate is " + rmr +" kcal. You do not eat enough!", Toast.LENGTH_LONG).show();
        }

    }
}
