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

/**
 * @author Tino Kankkunen
 * @version 1.0
 * MuscleWeekPlanActivity creates the final Weekly Plan for the user as an interactable list.
 * Each week day can be clicked and will open the chosed days activitys information if there is any.
 * The chosen exercises can then be marked as "DONE" which then removes the exercise from the list.
 * The user can create a new week plan and overwrite the old one.
 */
public class MuscleWeekPlanActivity extends AppCompatActivity {

    /**
     * EXTRAWP is a value saved for the purpose of displaying the correct muscle building exercise correlated to a speficic day in the next activity
     * MuscleWpExerciseActivity.
     */
    public static final String EXTRAWP = "MESSAGEWP";
    private Button createNewButton;

    /**
     * onCreate creates an interactable ListView that displays the week days.
     * By selecting a day, it will open the saved activity of that day if there is any through "ETRAWP, i"
     * @param savedInstanceState
     * @see DaysList for methods
     * @see Days for methods
     */
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_muscle_week_plan);


        ListView lv = findViewById(R.id.muscleWeekPlan);
        lv.setAdapter(new ArrayAdapter<Days>(
                this,
                R.layout.days_item_layout,
                DaysList.getInstance().getDays()
        ));
        lv.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            /**
             * this onItemClick method creates an intent for each day and then transfers the day in the value of "i" to the next activity so it knows
             * what exercise to display in the next activity
             * This method also starts the next activity "MuscleWpExerciseActivity".
             * @param adapterView
             * @param view
             * @param i
             * @param l
             */
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                Intent nextActivity = new Intent(MuscleWeekPlanActivity.this, MuscleWpExerciseActivity.class);
                nextActivity.putExtra(EXTRAWP, i);
                startActivity(nextActivity);
            }
        });

        createNewButton = (Button) findViewById(R.id.createButton2);
        createNewButton.setOnClickListener(new View.OnClickListener() {
            /**
             * This onClick() method creates a cancelable AlertDialog that pops up on screen to
             * confirm the user about the decision of creating a new plan and overwriting the current one.
             * @param v
             */
            @Override
            public void onClick(View v) {
                AlertDialog.Builder builder = new AlertDialog.Builder(MuscleWeekPlanActivity.this);
                builder.setCancelable(true);
                builder.setTitle("Are you sure?");
                builder.setMessage("The current week plan will be deleted.");

                builder.setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
                    /**
                     * If the user Clicks "Cancel" it cancels the action of creating a new week plan
                     * @param dialog
                     * @param which
                     */
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        dialog.cancel();
                    }
                });
                builder.setPositiveButton("OK", new DialogInterface.OnClickListener() {
                    /**
                     * If the user clicks "OK" then a new plan will be created and the current one will be reset.
                     * This will divert the user back to MuscleDayList where a new plan will be created.
                     * @param dialog
                     * @param which
                     * @see ProgramMenu for MUSCLEPROGRAMRESET
                     * @see MuscleDayList for RESET
                     */
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        SharedPreferences prefPut = getSharedPreferences(MainActivity.KEY, Activity.MODE_PRIVATE);
                        SharedPreferences.Editor prefEditor = prefPut.edit();
                        prefEditor.putInt(MuscleDayList.RESET, 0);
                        prefEditor.putInt(ProgramMenu.MUSCLEPROGRAMRESET, 2);
                        prefEditor.commit();
                        Intent nextActivity = new Intent(MuscleWeekPlanActivity.this, MuscleDayList.class);
                        startActivity(nextActivity);
                    }
                });
                builder.show();
            }
        });
    }

    /**
     * backToProgramMenu is an onClick() method for a button.
     * This method will allow the user to change the activity to ProgramMenu and select a new fitness program OR change their inputted user values.
     * @param view
     */
    public void backToProgramMenu(View view){
        Intent programActivity = new Intent(MuscleWeekPlanActivity.this, ProgramMenu.class);
        startActivity(programActivity);
    }
}
