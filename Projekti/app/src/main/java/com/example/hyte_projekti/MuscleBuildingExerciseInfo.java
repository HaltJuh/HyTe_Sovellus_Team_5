package com.example.hyte_projekti;

import androidx.appcompat.app.AppCompatActivity;

import android.app.Activity;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

public class MuscleBuildingExerciseInfo extends AppCompatActivity {

    private int i;
    private EditText time;
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
        setContentView(R.layout.activity_muscle_building_exercise_info);
        Bundle b = getIntent().getExtras();
        i =  b.getInt(ExActivityThree.EXEXTRA, 0);


        ((TextView)findViewById(R.id.infoName)).setText(GymExerciseList.getThisInstance().getGymExercises().get(i).getName());

        ((TextView)findViewById(R.id.infoMuscleOptions)).setText("1. Maximum muscle building\t\t4-5 repetitions/set\n" +
                "2. Standard mucle building plan\t\t6-8 repetitions/set\n" +
                "3. Lean muscle building & toning\t10-12 repetitions/set\n\n" +
                "Do 3 sets of each desired exercise.\n" +
                "Remember to warm up and train with a suitable weight for your needs!");

        ((TextView)findViewById(R.id.infoExercise)).setText(GymExerciseList.getThisInstance().getGymExercises().get(i).getInfo());

    }

    // SelectEx:
    // SelectExerciseButtonPressed method to select the time of exercise done and then saves exercise into week list!

    public void selectButtonPressed(View view){
        time = findViewById(R.id.editTime2);
        String text = time.getText().toString().trim();
        if(text.isEmpty() || text.length() == 0 || text.equals("")){
            Toast.makeText(this, "Please enter the time.", Toast.LENGTH_LONG).show();
        }else {
            timeInt = Integer.parseInt(time.getText().toString());
            if (timeInt < 15) {
                Toast.makeText(this, "Don't be so weak, let's do a little more!", Toast.LENGTH_LONG).show();
            }else{
                Bundle b = getIntent().getExtras();
                final int k = b.getInt(MuscleDayList.EXTRA, 0);
                SharedPreferences prefGet = getSharedPreferences(MainActivity.KEY, Activity.MODE_PRIVATE);
                int isItEmpty = prefGet.getInt(Integer.toString(k), 100);
                if(isItEmpty == 100) {
                    int correctedI = (10 * k) + i + 10;
                    age = prefGet.getInt(MainActivity.AGEKEY, 0);
                    height = Double.longBitsToDouble(prefGet.getLong(MainActivity.HEIGHTKEY, 0));
                    weight = Double.longBitsToDouble(prefGet.getLong(MainActivity.WEIGHTKEY, 0));
                    gender = prefGet.getString(MainActivity.GENDERKEY, "Not found.");
                    calculator = new Calculator(age, height, weight, gender);
                    int caloriesToEat = calculator.getCalPerDay(timeInt);

                    SharedPreferences prefPut = getSharedPreferences(MainActivity.KEY, Activity.MODE_PRIVATE);
                    SharedPreferences.Editor prefEditor = prefPut.edit();
                    prefEditor.putInt(Integer.toString(k), i);
                    prefEditor.putInt(Integer.toString(correctedI), timeInt);
                    prefEditor.commit();
                    Intent intent = new Intent(this, MuscleDayList.class);
                    startActivity(intent);
                    Toast.makeText(this, "Information saved successfully.", Toast.LENGTH_LONG).show();
                }else{
                    Toast.makeText(this, "You have already selected this day's activity!", Toast.LENGTH_LONG).show();
                }
            }
        }
    }

}
