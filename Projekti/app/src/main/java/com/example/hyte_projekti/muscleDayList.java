package com.example.hyte_projekti;

import androidx.appcompat.app.AppCompatActivity;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;

public class muscleDayList extends AppCompatActivity {

    public static final String EXTRA = "MESSAGE";
    private int latestActivity;
    private int caloriesPerDay; //this will hold the value of calories burned in a day
    private int caloriesToEat; //this will hold the value of calores burned + to eat more
    private int reset;
    public static final String RESET = "RESET";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_muscle_day_list);
        resetValues();

        //Bundle b = getIntent().getExtras();
        //int i = b.getInt(muscleDayList.EXTRA, 0);


        SharedPreferences prefGet = getSharedPreferences(MainActivity.KEY, Activity.MODE_PRIVATE);
        latestActivity = prefGet.getInt(MainActivity.LATESTACTIVITY, 0);
        ListView lv = findViewById(R.id.muscleDayList);
        lv.setAdapter(new ArrayAdapter<Days>(
                this,
                R.layout.days_item_layout,
                DaysList.getInstance().getDays()
        ));

        lv.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                    Intent nextActivity = new Intent(muscleDayList.this, ExActivityThree.class);
                    nextActivity.putExtra(EXTRA, i);
                    startActivity(nextActivity);
            }
        });
    }
    // DaysActivity:
    // UpdateUI method with calculations to caloriesPerDay AND caloriesToEat!!
    // resetValues method to reset saved values of weekly plan!
    // "Done" button method and button for XML view that takes user to MuscleWeekPlanActivity"

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
            for (int i = 10; i < 78; i++){
                SharedPreferences prefPut = getSharedPreferences(MainActivity.KEY, Activity.MODE_PRIVATE);
                SharedPreferences.Editor prefEditor = prefPut.edit();
                prefEditor.putInt(Integer.toString(i), 0);
                prefEditor.commit();
            }
            SharedPreferences prefPut = getSharedPreferences(MainActivity.KEY, Activity.MODE_PRIVATE);
            SharedPreferences.Editor prefEditor = prefPut.edit();
            prefEditor.putInt(SelectEx.CALORIESBURNED, 0);
            prefEditor.putInt(RESET, 1);
            prefEditor.commit();
        }
    }

    public void DoneButtonPressed(View view){
        SharedPreferences prefPut = getSharedPreferences(MainActivity.KEY, Activity.MODE_PRIVATE);
        SharedPreferences.Editor prefEditor = prefPut.edit();
        //prefEditor.putInt(CALORIESREMAINING, caloriesToBurn);
        prefEditor.putInt(ProgramMenu.ISITFIRSTTIME, 1);
        prefEditor.commit();
        Intent nextActivity = new Intent(muscleDayList.this, MuscleWeekPlanActivity.class);
        startActivity(nextActivity);
    }
}
