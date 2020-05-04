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
import android.widget.Button;
import android.widget.ListView;
import android.widget.TextView;

public class WeekPlanActivity extends AppCompatActivity {
    public static final String EXTRAWP = "MESSAGEWP";
    private TextView caloriesView;
    private Button createButton;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_week_plan);
        updateUI();
        ListView lv = findViewById(R.id.weekPlanView);
        lv.setAdapter(new ArrayAdapter<Days>(
                this,
                R.layout.days_item_layout,
                DaysList.getInstance().getDays()
        ));
        lv.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                    Intent nextActivity = new Intent(WeekPlanActivity.this, WpExerciseActivity.class);
                    nextActivity.putExtra(EXTRAWP, i);
                    startActivity(nextActivity);
            }
        });

        createButton = (Button)findViewById(R.id.createButton);
        createButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                AlertDialog.Builder builder = new AlertDialog.Builder(WeekPlanActivity.this);
                builder.setCancelable(true);
                builder.setTitle("Are you sure?");
                builder.setMessage("The current week plan will be deleted.");

                builder.setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        dialog.cancel();
                    }
                });
                builder.setPositiveButton("OK", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        SharedPreferences prefPut = getSharedPreferences(MainActivity.KEY, Activity.MODE_PRIVATE);
                        SharedPreferences.Editor prefEditor = prefPut.edit();
                        prefEditor.putInt(DaysActivity.RESET, 0);
                        prefEditor.putInt(ProgramMenu.ISITFIRSTTIME, 2);
                        prefEditor.commit();
                        Intent nextActivity = new Intent(WeekPlanActivity.this, DaysActivity.class);
                        startActivity(nextActivity);
                    }
                });
                builder.show();
            }
        });
    }

    public void updateUI(){
        caloriesView = findViewById(R.id.caloriesRemaining);
        SharedPreferences prefGet = getSharedPreferences(MainActivity.KEY, Activity.MODE_PRIVATE);
        int calories = prefGet.getInt(DaysActivity.CALORIESREMAINING, 0);
        caloriesView.setText("You have " + calories + " kcal left to burn this week");
    }
}
