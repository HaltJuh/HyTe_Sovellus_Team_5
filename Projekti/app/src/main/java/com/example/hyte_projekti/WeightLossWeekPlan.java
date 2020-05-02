package com.example.hyte_projekti;

import android.app.Activity;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.provider.Settings;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import static com.example.hyte_projekti.ExActivityOne.EXTRA_CALORIE_INTAKE;
import static com.example.hyte_projekti.ExActivityOne.EXTRA_IDEAL_WEIGHT;
import static com.example.hyte_projekti.MainActivity.AGEKEY;
import static com.example.hyte_projekti.MainActivity.GENDERKEY;
import static com.example.hyte_projekti.MainActivity.HEIGHTKEY;
import static com.example.hyte_projekti.MainActivity.KEY;
import static com.example.hyte_projekti.MainActivity.WEEKLYCALORIEKEY;
import static com.example.hyte_projekti.MainActivity.WEIGHTKEY;

public class WeightLossWeekPlan extends AppCompatActivity {

    public static final String TOTALCALORIETEXT= "The amount of kilocalories you\n should burn weekly: ";
    public static final String CURRENTCALORIETEXT = "The amount of kilocalories your\n current week plan burns: ";
    public static final String EXTRA_WEEK_INDEX = "weekIndex";
    private DaysList days;
    private ListView weekList;
    private TextView totalCalorieView;
    private TextView currentCalorieView;
    private SharedPreferences prefGet;
    private Calculator calculator;

    private int idealWeight;
    private double dailyCalories;
    private double caloriesToLosePerWeek;
    private double currentBurnedWeeklyCalories;
    private int weeksToLoseExtraWeight;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_weight_loss_week_planner);
        Bundle extras = getIntent().getExtras();
        days = DaysList.getInstance();
        weekList = findViewById(R.id.weekList);
        weekList.setAdapter(new ArrayAdapter<Days>(
                this,
                R.layout.days_item_layout,
                days.getDays())
        );
        prefGet = getSharedPreferences(KEY, Activity.MODE_PRIVATE);
        calculator = new Calculator(
                prefGet.getInt(AGEKEY,0),
                Double.longBitsToDouble(prefGet.getLong(HEIGHTKEY,0)),
                Double.longBitsToDouble(prefGet.getLong(WEIGHTKEY,0)),
                prefGet.getString(GENDERKEY,"Male")
        );
        idealWeight = extras.getInt(EXTRA_IDEAL_WEIGHT);
        dailyCalories = extras.getDouble(EXTRA_CALORIE_INTAKE);
        Log.d("calories", ""+dailyCalories);
        caloriesToLosePerWeek = calculator.getCaloriesToBurnPerWeek(dailyCalories);
        weeksToLoseExtraWeight = calculator.getWeeksToLoseAllExtraWeight(idealWeight,dailyCalories);
        currentBurnedWeeklyCalories = Double.longBitsToDouble(prefGet.getLong(WEEKLYCALORIEKEY,0));

        totalCalorieView = findViewById(R.id.totalWeeklyCaloriesView);
        currentCalorieView = findViewById(R.id.currentWeeklyCaloriesView);
        totalCalorieView.setText(TOTALCALORIETEXT+caloriesToLosePerWeek);
        currentCalorieView.setText(CURRENTCALORIETEXT+currentBurnedWeeklyCalories);
        Toast.makeText(this, "Estimated time to lose the extra weight\naccording to average values: "+weeksToLoseExtraWeight, Toast.LENGTH_SHORT).show();
        weekList.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int i, long id) {

                int index = i++;
                if(i==7){
                    i=0;
                }

                Log.d("Click",""+days.getDay(index).getName());
                Intent intent = new Intent(WeightLossWeekPlan.this,WeightLossExercises.class);
                intent.putExtra(EXTRA_WEEK_INDEX,index);
                startActivity(intent);
            }
        });
    }
}
