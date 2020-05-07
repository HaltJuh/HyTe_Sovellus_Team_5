package com.example.hyte_projekti.MucleBulding;

import androidx.appcompat.app.AppCompatActivity;

import android.app.Activity;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import com.example.hyte_projekti.General.Days;
import com.example.hyte_projekti.General.DaysList;
import com.example.hyte_projekti.General.Exercise;
import com.example.hyte_projekti.General.MainActivity;
import com.example.hyte_projekti.R;

/**
 * MuscleWpExerciseActivity class creates and fills the new activity with information based on SharedPreference values that were saved from MuscleDayList
 * and MuscleBuildingExerciseInfo. These values hold the information about the selected day and what activity was chosen for that day.
 * The user can now select the workout to be "DONE" or completed by a button and then move back to the current Week plan.
 * @author Tino Kankkunen
 * @version 1.0
 */
public class MuscleWpExerciseActivity extends AppCompatActivity {

    private int i;
    private int iEx;
    private int iTime;
    private int time;
    private TextView dayName;
    private TextView exName;
    private TextView exInfo;

    /**
     * onCreate() method takes the SharedPreferences from MuscleWeekPlanActivity "EXTRAWP" updates the UI accordingly by calling the updateUI() method.
     * @param savedInstanceState
     * @see MuscleWeekPlanActivity for EXTRAWP
     */
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_muscle_wp_exercise);
        Bundle b = getIntent().getExtras();
        i =  b.getInt(MuscleWeekPlanActivity.EXTRAWP, 100);
        SharedPreferences prefGet = getSharedPreferences(MainActivity.KEY, Activity.MODE_PRIVATE);
        iEx = prefGet.getInt(Integer.toString(i), 100);
        iTime = (10 * i) + iEx + 10;
        time = prefGet.getInt(Integer.toString(iTime), 0);
        updateUI();
    }

    /**
     * updateUI() method selects the components in the .xml file and sets the information for those components throught the given variables.
     * It displays the name of the day, the exercise saved on that day, the amount of minutes user inputted to do that exercise and the information
     * about that chosed exercise.
     * @see GymExerciseList for getThisInstance and getGymExercise
     * @see DaysList for methods
     * @see Days for methods
     * @see Exercise for getName and getInfo
     */
    public void updateUI(){
        dayName = findViewById(R.id.whatDay);
        exName = findViewById(R.id.whatActivity);
        exInfo = findViewById(R.id.whatInfo);
        dayName.setText(DaysList.getInstance().getDay(i).getName());

        if(iEx == 100){
            exName.setText("    There is nothing for this day.");
            exInfo.setText("");
        }else{
            exName.setText(GymExerciseList.getThisInstance().getGymExercise(iEx).getName() + " for " + time + " minutes");
            exInfo.setText(GymExerciseList.getThisInstance().getGymExercise(iEx).getInfo());
        }
    }

    /**
     * doneButtonClicked() method is an onClick method for a button.
     * If there is NO exercise for the day, the button will take the user back to the week plan.
     * If there IS an exercise for the day, the button will display a toast for doing the activity and call the resetValue() method,
     * that will reset the saved values for the day. This means that next time the user checks that day for activities, there will be none as they have been completed.
     * @param view
     */
    public void doneButtonClicked(View view){
        if(iEx != 100) {
            resetValue();
            Toast.makeText(this, "Great job! Workout complete!", Toast.LENGTH_LONG).show();
        }
        Intent nextActivity = new Intent(MuscleWpExerciseActivity.this, MuscleWeekPlanActivity.class);
        startActivity(nextActivity);
    }

    /**
     * resetValue() method finds the selected days saved exercise and the time to be done from SharedPreferences and then resets the values back to default.
     * This will remove that workout from the week plan as it is completed.
     */
    public void resetValue(){
        SharedPreferences prefPut = getSharedPreferences(MainActivity.KEY, Activity.MODE_PRIVATE);
        SharedPreferences.Editor prefEditor = prefPut.edit();
        prefEditor.putInt(Integer.toString(i), 100);
        prefEditor.putInt(Integer.toString(iTime), 0);
        prefEditor.commit();
    }
}
