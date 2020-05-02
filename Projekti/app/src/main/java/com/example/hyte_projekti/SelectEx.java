package com.example.hyte_projekti;

import androidx.appcompat.app.AppCompatActivity;

import android.app.Activity;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

public class SelectEx extends AppCompatActivity {
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
                    int correctedI = i + 10;
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
