package com.example.hyte_projekti.FitnessManagement;

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

import com.example.hyte_projekti.Days;
import com.example.hyte_projekti.DaysList;
import com.example.hyte_projekti.MainActivity;
import com.example.hyte_projekti.ProgramMenu;
import com.example.hyte_projekti.R;

/**
 * This activity shows the week plan a user created.
 * By clicking the days of the week the user can see if he/she has any activity for that day.
 * The user can also see how many calories he/she still has to burn.
 *
 * @author Tommi Vainio
 * @version 1.0
 */
public class WeekPlanActivity extends AppCompatActivity {
    /**
     * The index of the day.
     */
    public static final String EXTRAWP = "MESSAGEWP";
    private TextView caloriesView;
    private Button createButton;

    /**
     * Creates a list which includes days of the week and sets onItemClickListener to that list.
     * Sets onClickListener and alertDialog to Create new week plan button.
     *
     * @param savedInstanceState
     */
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

    /**
     * Updates text view at the top of the screen based on how many calories a user still needs to burn.
     */
    public void updateUI(){
        caloriesView = findViewById(R.id.caloriesRemaining);
        SharedPreferences prefGet = getSharedPreferences(MainActivity.KEY, Activity.MODE_PRIVATE);
        int calories = prefGet.getInt(DaysActivity.CALORIESREMAINING, 0);
        caloriesView.setText("You have " + calories + " kcal left to burn this week");
    }

    /**
     * Starts ProgramMenu activity.
     * @param view Back to menu button that is clicked to perform this method.
     */
    public void backToMenuButton(View view){
        Intent nextActivity = new Intent(WeekPlanActivity.this, ProgramMenu.class);
        startActivity(nextActivity);
    }
}
