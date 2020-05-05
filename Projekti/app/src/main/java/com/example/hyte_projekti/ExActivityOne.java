package com.example.hyte_projekti;

import androidx.appcompat.app.AppCompatActivity;

import android.app.Activity;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import java.util.Calendar;

import static com.example.hyte_projekti.MainActivity.AGEKEY;
import static com.example.hyte_projekti.MainActivity.CREATIONDAY;
import static com.example.hyte_projekti.MainActivity.GENDERKEY;
import static com.example.hyte_projekti.MainActivity.HEIGHTKEY;
import static com.example.hyte_projekti.MainActivity.KEY;
import static com.example.hyte_projekti.MainActivity.WEEKLYCALORIESTOBURN;
import static com.example.hyte_projekti.MainActivity.WEEKPLANKEY;
import static com.example.hyte_projekti.MainActivity.WEIGHTKEY;
import static com.example.hyte_projekti.WeightLossWeekPlan.CALORIESBURNEDWEEKLY;

/**
 * Activity that lets the user enter their goal and let's them see their plan.
 @author Juho Halttunen
 @version 1.0
*/
public class ExActivityOne extends AppCompatActivity {

    /**
     * This static String is used as a key to store the ideal weight of the user.
     */
    public static final String IDEAL_WEIGHT = "idealWeight";
    /**
     * This static String is used as a key to store the calorie intake of the user.
     */
    public static final String CALORIE_INTAKE = "calorieIntake";
    /**
     * The value of this String is used to get the extra that holds the index of the day.
     */
    public static final String EXTRA_DAY_INDEX = "Day_Index";
    /**
     * The value of this String is used to get the extra that holds the amount of time exercised each day.
     */
    public static final String EXTRA_DAY_TIME = "Day_Time";
    /**
     * The value of this String is used to get the extra that holds the name of the day.
     */
    public static final String EXTRA_DAY_NAME = "Day_Name";

    private WeightLossList exercises;
    private EditText weightText;
    private EditText calorieText;
    private TextView caloriesToBurn;
    private TextView kilosLostPerWeek;
    private TextView dayWiew;
    private int idealWeight;
    private double dailyCalories;
    private Calculator calculator;
    private double lowHealthyBMI = 18.5;
    private SharedPreferences prefGet;
    private SharedPreferences prefPut;
    private DaysList days;
    private ListView weekPlanView;
    Calendar calendar;

    /**
     * OnCreate instantiates all needed fields and checks which layout to display.
     * Setups layout elements depending on which layout was loaded.
     * @param savedInstanceState
     * @return void
     */
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        Log.d("Create","Created");
        super.onCreate(savedInstanceState);
        Bundle b = getIntent().getExtras();
        prefGet = getSharedPreferences(KEY, Activity.MODE_PRIVATE);
        Log.d("Created",""+prefGet.getInt(WEEKPLANKEY,0));
        days = DaysList.getInstance();
        calculator = new Calculator(
                prefGet.getInt(AGEKEY,0),
                Double.longBitsToDouble(prefGet.getLong(HEIGHTKEY,0)),
                Double.longBitsToDouble(prefGet.getLong(WEIGHTKEY,0)),
                prefGet.getString(GENDERKEY,"Male"));
        if(prefGet.getInt(WEEKPLANKEY,0)==1){
            setUpPlanLayout();
        }else{
            setContentView(R.layout.activity_ex_one);
            weightText = findViewById(R.id.weightView);
            calorieText = findViewById(R.id.calorieView);
        }
    }

    /**
     * Setups layout for Weekplan listview.
     * @return void
     */
    private void setUpPlanLayout(){
        setContentView(R.layout.activity_weightloss_week_plan);
        caloriesToBurn = findViewById(R.id.caloriesToBurn);
        caloriesToBurn.setText("You have "+Double.longBitsToDouble(prefGet.getLong(WEEKLYCALORIESTOBURN,0))+" kcal left to burn this week");
        kilosLostPerWeek = findViewById(R.id.kilosLostPerWeek);
        double kilosLost = Math.floor(calculator.getKilosLostPerWeek(Double.longBitsToDouble(prefGet.getLong(CALORIESBURNEDWEEKLY,0)))*100)/100;
        kilosLostPerWeek.setText("Kilos lost per week "+kilosLost+"kg");
        exercises = WeightLossList.getInstance();
        weekPlanView = findViewById(R.id.weightLossPlan);
        weekPlanView.setAdapter(new ArrayAdapter<Days>(
                this,
                R.layout.days_item_layout,
                days.getDays()
        ));
        weekPlanView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int i, long id) {
                Intent intent = new Intent(ExActivityOne.this,DayActivity.class);
                Days day = days.getDay(i);
                int time = prefGet.getInt(day.getSaveKey(),0);
                intent.putExtra(EXTRA_DAY_INDEX,i);
                intent.putExtra(EXTRA_DAY_TIME,time);
                intent.putExtra(EXTRA_DAY_NAME,day.getName());
                startActivity(intent);
            }
        });
        dayWiew = findViewById(R.id.creationDay);
        switch (prefGet.getInt(CREATIONDAY,0)){
            case 1:
                dayWiew.setText("Reset every Sunday");
                break;
            case 2:
                dayWiew.setText("Reset every Monday");
                break;
            case 3:
                dayWiew.setText("Reset every Tuesday");
                break;
            case 4:
                dayWiew.setText("Reset every Wednesday");
                break;
            case 5:
                dayWiew.setText("Reset every Thursday");
                break;
            case 6:
                dayWiew.setText("Reset every Friday");
                break;
            case 7:
                dayWiew.setText("Reset every Saturday");
                break;
            default:
                dayWiew.setText("I am Error.");
                break;
        }
    }

    /**
     * Checks if values given are correct.
     * @return true if given values are correct.
     */
    private boolean checkIfOk(){
        String weightString = weightText.getText().toString();
        String calorieString = calorieText.getText().toString();
        if(TextUtils.isEmpty(weightString)){
            return false;
        }
        else{
            idealWeight = Integer.parseInt(weightString);
        }
        if(TextUtils.isEmpty(calorieString)){
            dailyCalories = calculator.getRmr();
        }
        else{
            dailyCalories = Double.parseDouble(calorieString);
        }
        double lowestHealthyWeight = lowHealthyBMI * Math.pow((Double.longBitsToDouble(prefGet.getLong(HEIGHTKEY,0)))/100,2);
        Log.d("weighLoss", "onContinue: "+idealWeight+" "+lowestHealthyWeight+" "+Double.longBitsToDouble(prefGet.getLong(HEIGHTKEY,0)));
        if(idealWeight < lowestHealthyWeight){
            Toast.makeText(this, "This ideal weight is too low for your height", Toast.LENGTH_SHORT).show();
            return false;
        }
        if(dailyCalories < calculator.getRmr()){
            Toast.makeText(this, "Your minimum daily calorie intake should be "+calculator.getRmr()+". You should eat more.", Toast.LENGTH_SHORT).show();
            return false;
        }
        return true;
    }

    /**
     * Calls the reset method and starts the WeightLossWeekPlan activity if CheckIfOk returns true
     * @param view
     */
    public void onContinue(View view){
        if(checkIfOk()){
            reset();
            Intent intent = new Intent(this,WeightLossWeekPlan.class);
            startActivity(intent);
        }
    }

    /**
     * Resets weekly calories burned this week and sets up new reset day.
     * @param view
     */
    public void onResetButton(View view){
        SharedPreferences.Editor prefEditor = prefGet.edit();
        for(int i=0;i<days.getDays().size();i++) {
            Days day = days.getDay(i);
            prefEditor.putInt(day.getDoneKey(),0);
        }
        double weeklyCalories = Double.longBitsToDouble(prefGet.getLong(CALORIESBURNEDWEEKLY,0));
        prefEditor.putLong(WEEKLYCALORIESTOBURN,Double.doubleToLongBits(weeklyCalories));
        prefEditor.putInt(CREATIONDAY,calendar.DAY_OF_WEEK);
        prefEditor.commit();
        Intent intent = getIntent();
        finish();
        startActivity(intent);
    }

    /**
     * Returns to ProgramMenu activity
     * @param view
     */
    public void backToMenu(View view){
        Intent intent = new Intent(ExActivityOne.this,ProgramMenu.class);
        startActivity(intent);
    }

    /**
     * Resets existing plan.
     */
    private void reset(){
        prefPut = getSharedPreferences(KEY,Activity.MODE_PRIVATE);
        SharedPreferences.Editor prefEditor = prefPut.edit();
        prefEditor.putInt(IDEAL_WEIGHT,idealWeight);
        prefEditor.putLong(CALORIE_INTAKE,Double.doubleToRawLongBits(dailyCalories));
        for(int i=0;i<days.getDays().size();i++) {
            Days day = days.getDay(i);
            prefEditor.putInt(day.getSaveKey(), 0);
            prefEditor.putInt(Integer.toString(day.getIndex()), 0);
            prefEditor.putInt(day.getDoneKey(),0);
        }
        prefEditor.commit();
    }
}
