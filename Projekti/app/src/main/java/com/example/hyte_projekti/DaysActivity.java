package com.example.hyte_projekti;

import androidx.appcompat.app.AppCompatActivity;

import android.app.Activity;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.TextView;

public class DaysActivity extends AppCompatActivity {
    public static final String EXTRA = "MESSAGE";
    public static final String RESET = "RESET";
    private int latestActivity;
    private int reset;
    private int age;
    private Double height;
    private Double weight;
    private String gender;
    private Calculator calculator;
    private int caloriesEaten;
    private int caloriesToBurn;
    private int caloriesBurned;
    private TextView caloriesAdded;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_days);
        /*SharedPreferences prefPut = getSharedPreferences(MainActivity.KEY, Activity.MODE_PRIVATE);
        SharedPreferences.Editor prefEditor = prefPut.edit();
        prefEditor.putInt(RESET, 0);
        prefEditor.commit();*/
        resetValues();
        updateUI();
        SharedPreferences prefGet = getSharedPreferences(MainActivity.KEY, Activity.MODE_PRIVATE);
        latestActivity = prefGet.getInt(MainActivity.LATESTACTIVITY, 0);
        ListView lv = findViewById(R.id.daysListView);
        lv.setAdapter(new ArrayAdapter<Days>(
                this,
                R.layout.days_item_layout,
                DaysList.getInstance().getDays()
        ));

        lv.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                if(latestActivity == 1) {
                    Intent nextActivity = new Intent(DaysActivity.this, ExActivityOne.class);
                    nextActivity.putExtra(EXTRA, i);
                    startActivity(nextActivity);
                }
                if (latestActivity == 2){
                    Intent nextActivity = new Intent(DaysActivity.this, ExActivityTwo.class);
                    nextActivity.putExtra(EXTRA, i);
                    startActivity(nextActivity);
                }
                if (latestActivity == 3){
                    Intent nextActivity = new Intent(DaysActivity.this, ExActivityThree.class);
                    nextActivity.putExtra(EXTRA, i);
                    startActivity(nextActivity);
                }
            }
        });
    }

    public void resetValues(){
        SharedPreferences prefGet = getSharedPreferences(MainActivity.KEY, Activity.MODE_PRIVATE);
        reset = prefGet.getInt(RESET, 0);

        if(reset == 0){
            for(int i = 0; i < 7; i++){
                SharedPreferences prefPut = getSharedPreferences(MainActivity.KEY, Activity.MODE_PRIVATE);
                SharedPreferences.Editor prefEditor = prefPut.edit();
                prefEditor.putInt(Integer.toString(i), 100);
                prefEditor.commit();
            }
            for (int i = 0; i < 9; i++){
                int corrected = i + 10;
                SharedPreferences prefPut = getSharedPreferences(MainActivity.KEY, Activity.MODE_PRIVATE);
                SharedPreferences.Editor prefEditor = prefPut.edit();
                prefEditor.putInt(Integer.toString(corrected), 0);
                prefEditor.commit();
            }
            SharedPreferences prefPut = getSharedPreferences(MainActivity.KEY, Activity.MODE_PRIVATE);
            SharedPreferences.Editor prefEditor = prefPut.edit();
            prefEditor.putInt(SelectEx.CALORIESBURNED, 0);
            prefEditor.putInt(RESET, 1);
            prefEditor.commit();
        }
    }

    public void updateUI(){
        SharedPreferences prefGet = getSharedPreferences(MainActivity.KEY, Activity.MODE_PRIVATE);
        age = prefGet.getInt(MainActivity.AGEKEY, 0);
        height = Double.longBitsToDouble(prefGet.getLong(MainActivity.HEIGHTKEY, 0));
        weight = Double.longBitsToDouble(prefGet.getLong(MainActivity.WEIGHTKEY, 0));
        gender = prefGet.getString(MainActivity.GENDERKEY, "Not found.");
        calculator = new Calculator(age, height, weight, gender);
        caloriesEaten = prefGet.getInt(MaintainFitnessLevel.CALORIESEATENKEY, 0);
        caloriesToBurn = calculator.getWeeklyCaloriesToBurn(caloriesEaten);
        caloriesBurned = prefGet.getInt(SelectEx.CALORIESBURNED, 0);

        caloriesAdded = findViewById(R.id.caloriesAdded);
        caloriesAdded.setText(Integer.toString(caloriesBurned) + "/" + Integer.toString(caloriesToBurn) + " kcal");
    }
}
