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

import static com.example.hyte_projekti.MainActivity.AGEKEY;
import static com.example.hyte_projekti.MainActivity.GENDERKEY;
import static com.example.hyte_projekti.MainActivity.HEIGHTKEY;
import static com.example.hyte_projekti.MainActivity.KEY;
import static com.example.hyte_projekti.MainActivity.WEEKLYCALORIESTOBURN;
import static com.example.hyte_projekti.MainActivity.WEEKPLANKEY;
import static com.example.hyte_projekti.MainActivity.WEIGHTKEY;

public class ExActivityOne extends AppCompatActivity {

    public static final String IDEAL_WEIGHT = "idealWeight";
    public static final String CALORIE_INTAKE = "calorieIntake";
    public static final String EXTRA_DAY_INDEX = "Day_Index";
    public static final String EXTRA_DAY_TIME = "Day_Time";
    public static final String EXTRA_DAY_NAME = "Day_Name";

    private WeightLossList exercises;
    private EditText weightText;
    private EditText calorieText;
    private TextView caloriesToBurn;
    private int idealWeight;
    private double dailyCalories;
    private Calculator calculator;
    private double lowHealthyBMI = 18.5;
    private SharedPreferences prefGet;
    private SharedPreferences prefPut;
    private DaysList days;
    private ListView weekPlanView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        Log.d("Create","Created");
        super.onCreate(savedInstanceState);
        Bundle b = getIntent().getExtras();
        prefGet = getSharedPreferences(KEY, Activity.MODE_PRIVATE);
        Log.d("Created",""+prefGet.getInt(WEEKPLANKEY,0));
        days = DaysList.getInstance();
        if(prefGet.getInt(WEEKPLANKEY,0)==1){
            setContentView(R.layout.activity_weightloss_week_plan);
            caloriesToBurn = findViewById(R.id.caloriesToBurn);
            caloriesToBurn.setText("You have "+Double.longBitsToDouble(prefGet.getLong(WEEKLYCALORIESTOBURN,0))+" kcal left to burn this week");
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
                    intent.putExtra(EXTRA_DAY_INDEX,day.getIndex());
                    intent.putExtra(EXTRA_DAY_TIME,time);
                    intent.putExtra(EXTRA_DAY_NAME,day.getName());
                    startActivity(intent);
                }
            });
        }else{
            setContentView(R.layout.activity_ex_one);
            weightText = findViewById(R.id.weightView);
            calorieText = findViewById(R.id.calorieView);
        }
        calculator = new Calculator(
                prefGet.getInt(AGEKEY,0),
                Double.longBitsToDouble(prefGet.getLong(HEIGHTKEY,0)),
                Double.longBitsToDouble(prefGet.getLong(WEIGHTKEY,0)),
                prefGet.getString(GENDERKEY,"Male"));

    }

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
    public void onContinue(View view){
        if(checkIfOk()){
            Reset();
            Intent intent = new Intent(this,WeightLossWeekPlan.class);
            startActivity(intent);
        }
    }
    private void Reset(){
        prefPut = getSharedPreferences(KEY,Activity.MODE_PRIVATE);
        SharedPreferences.Editor prefEditor = prefPut.edit();
        prefEditor.putInt(IDEAL_WEIGHT,idealWeight);
        prefEditor.putLong(CALORIE_INTAKE,Double.doubleToRawLongBits(dailyCalories));
        for(int i=0;i<days.getDays().size();i++) {
            Days day = days.getDay(i);
            prefEditor.putInt(day.getSaveKey(), 0);
            prefEditor.putInt(Integer.toString(day.getIndex()), 0);
        }
        prefEditor.commit();
    }
}
