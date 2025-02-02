package com.example.hyte_projekti.MucleBulding;

import androidx.appcompat.app.AppCompatActivity;

import android.app.Activity;
import android.app.AlarmManager;
import android.app.PendingIntent;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import com.example.hyte_projekti.General.Days;
import com.example.hyte_projekti.General.DaysList;
import com.example.hyte_projekti.General.MainActivity;
import com.example.hyte_projekti.General.ProgramMenu;
import com.example.hyte_projekti.R;
import com.example.hyte_projekti.General.Receiver;

/**
 * This activity creates an interactable ListView that displays the days of the week so the user can create his weekly plan. A repeating popup notification is displayed
 * weekly from the finishing of the week plan to remind the user to create a new plan.
 * @author Tino Kankkunen
 * @version 1.0
 */
public class MuscleDayList extends AppCompatActivity {

    /**
     * RESET holds the value of reset, that is used to reset the saved workouts from the week plan
     * EXTRA holds values of selected day to use it in building the week plan. With the day value saved, a specific workout can be correlated with it.
     */
    public static final String RESET = "RESET";
    public static final String EXTRA = "MESSAGE";
    private int latestActivity;
    private int caloriesPerDay; //this will hold the value of calories burned in a day
    private int caloriesToEat; //this will hold the value of calores burned + to eat more
    private int reset;


    /**
     * In the onCreate method, it first calls the resetValues() method to clear out any previously saved week plans from SharedPreferences.
     * Then it creates an interactable ListView that displays the weeks days through DaysList.getInstance().getDays().
     * @param savedInstanceState
     */
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_muscle_day_list);
        resetValues();

        SharedPreferences prefGet = getSharedPreferences(MainActivity.KEY, Activity.MODE_PRIVATE);
        latestActivity = prefGet.getInt(MainActivity.LATESTACTIVITY, 0);
        ListView lv = findViewById(R.id.muscleDayList);
        lv.setAdapter(new ArrayAdapter<Days>(
                this,
                R.layout.days_item_layout,
                DaysList.getInstance().getDays()
        ));

        lv.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            /**
             * this onItemClick method creates an intent for each day and then transfers the day in the value of "i" to the next activity so it knows
             * that if the user plans to save an exercise to the current selected day "i".
             * This method also starts the next activity "ExActivityThree".
             * @param adapterView
             * @param view
             * @param i
             * @param l
             */
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                    Intent nextActivity = new Intent(MuscleDayList.this, ExActivityThree.class);
                    nextActivity.putExtra(EXTRA, i);
                    startActivity(nextActivity);
            }
        });
    }

    /**
     * resetValues() method resets the saved values of the previously created week plan, then marks the RESET value into 1 if it was 0
     */
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
            prefEditor.putInt(RESET, 1);
            prefEditor.commit();
        }
    }

    /**
     * doneButtonPressed() method displays in the xml editor and is used as an onClick method when the user is done editing the week plan and wants to finish it by creating one.
     * When this method is called an AlarmManager communicates with the BroadcastReceiver class "Receiver" to engage a timer for a weekly popup notification.
     * This notification is a reminder for the user to view and create a new weekly plan by the end of the week.
     * This method changes the SharedPreference of ProgramMenu.MUSCLEPROGRAMRESET into "1" which means that information is now saved into the week plan so it is not empty!
     * doneButtonPressed() then opens MuscleWeekPlanActivity.
     * @param view
     * @see Receiver for the popup notification
     * @see ProgramMenu for MUSCLEPROGRAMRESET
     */
    public void doneButtonPressed(View view){
        Intent intentReceiver = new Intent(MuscleDayList.this, Receiver.class);
        PendingIntent pendingIntent = PendingIntent.getBroadcast(MuscleDayList.this, 0, intentReceiver, 0);
        AlarmManager am = (AlarmManager)getSystemService(ALARM_SERVICE);
        am.setRepeating(am.RTC_WAKEUP, System.currentTimeMillis(), am.INTERVAL_DAY*7, pendingIntent);                           // am.INTERVAL_DAY*7 for once a week!! Can use lower values for more frequent notifications!

        SharedPreferences prefPut = getSharedPreferences(MainActivity.KEY, Activity.MODE_PRIVATE);
        SharedPreferences.Editor prefEditor = prefPut.edit();
        prefEditor.putInt(ProgramMenu.MUSCLEPROGRAMRESET, 1);
        prefEditor.commit();
        Intent nextActivity = new Intent(MuscleDayList.this, MuscleWeekPlanActivity.class);
        startActivity(nextActivity);
    }
}
