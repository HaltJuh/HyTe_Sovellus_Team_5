package com.example.hyte_projekti.FitnessManagement;

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
import android.widget.TextView;

import com.example.hyte_projekti.General.Calculator;
import com.example.hyte_projekti.General.Days;
import com.example.hyte_projekti.General.DaysList;
import com.example.hyte_projekti.General.MainActivity;
import com.example.hyte_projekti.General.ProgramMenu;
import com.example.hyte_projekti.General.Receiver;

/**
 * This activity shows a user the days of the week list.
 * By clicking any day of the list, the user can choose exercise for that day.
 *
 * @author Tommi Vainio
 * @version 1.0
 */
public class DaysActivity extends AppCompatActivity {
    /**
     * The key of the extra which is sent to next  activity.
     */
    public static final String EXTRA = "MESSAGE";
    /**
     * If value of this key is 0, the information retrieved in resetValues method is reset.
     */
    public static final String RESET = "RESET";
    /**
     * The value of this key tells how many calories a user still needs to burn.
     */
    public static final String CALORIESREMAINING = "CALORIESREMAINING";
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

    /**
     * Reset saved information if needed, updates textView, creates a list which includes days of the week and
     * sets onItemClickListener to that list.
     * @param savedInstanceState
     */
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_days);
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
                    Intent nextActivity = new Intent(DaysActivity.this, ExActivityTwo.class);
                    nextActivity.putExtra(EXTRA, i);
                    startActivity(nextActivity);
            }
        });
    }

    /**
     * Changes all days of the week values to 100 and time values to 0 if RESET key's value is 0.
     * <p>
     * Days' of the week keys are their indexes (0-6). Exercise time keys range from 10 t0 78 so
     * this method set all those keys' values to 0. After that CALORIESBURNED key's value is reset too.
     * Finally RESET key's value is changed to 1 so that those values are not reset every time
     * this activity is started.
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
            prefEditor.putInt(SelectEx.CALORIESBURNED, 0);
            prefEditor.putInt(RESET, 1);
            prefEditor.commit();
        }
    }

    /**
     * Updates the text view at the top of the screen.
     * <p>
     * Retrieves needed information, creates new Calculator instance, calculates how many calories
     * a user needs to burn per week and then checks how many calories activities, the user has already
     * added to the week plan, burn. Finally those values are set to the text view.
     *
     * @see Calculator
     */
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

    /**
     * Saves the amount of calories a user needs to burn in a week and starts WeekPlanActivity.
     * Changes also ISITFIRSTTIME key's value so that the right activity is started when a user
     * clicks the button of this program in the menu.
     * When this method is called an AlarmManager communicates with the BroadcastReceiver class "Receiver" to engage a timer for a weekly popup notification.
     * This notification is a reminder for the user to view and create a new weekly plan by the end of the week.
     *
     * @param view Done button that is clicked to perform this method.
     * @see Receiver for the popup notification
     */
    public void DoneButtonPressed(View view){
        Intent intentReceiver = new Intent(DaysActivity.this, Receiver.class);
        PendingIntent pendingIntent = PendingIntent.getBroadcast(DaysActivity.this, 0, intentReceiver, 0);
        AlarmManager am = (AlarmManager)getSystemService(ALARM_SERVICE);
        am.setRepeating(am.RTC_WAKEUP, System.currentTimeMillis(), am.INTERVAL_DAY*7, pendingIntent);                           // am.INTERVAL_DAY*7 for once a week!! Can use lower values for more frequent notifications!

        SharedPreferences prefPut = getSharedPreferences(MainActivity.KEY, Activity.MODE_PRIVATE);
        SharedPreferences.Editor prefEditor = prefPut.edit();
        prefEditor.putInt(CALORIESREMAINING, caloriesToBurn);
        prefEditor.putInt(ProgramMenu.ISITFIRSTTIME, 1);
        prefEditor.commit();
        Intent nextActivity = new Intent(DaysActivity.this, WeekPlanActivity.class);
        startActivity(nextActivity);
    }
}
