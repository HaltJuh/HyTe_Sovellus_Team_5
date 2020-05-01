package com.example.hyte_projekti;

import androidx.appcompat.app.AppCompatActivity;

import android.app.Activity;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import static com.example.hyte_projekti.MainActivity.AGEKEY;
import static com.example.hyte_projekti.MainActivity.GENDERKEY;
import static com.example.hyte_projekti.MainActivity.HEIGHTKEY;
import static com.example.hyte_projekti.MainActivity.KEY;
import static com.example.hyte_projekti.MainActivity.WEIGHTKEY;

public class ExActivityOne extends AppCompatActivity {

    private EditText weightText;
    private EditText calorieText;
    private int idealWeight;
    private int dailyCalories;
    private Calculator calculator;
    private double lowHealthyBMI = 18.5;
    private SharedPreferences prefGet;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_ex_one);
        Bundle b = getIntent().getExtras();
        //int i = b.getInt(DaysActivity.EXTRA, 0);
        prefGet = getSharedPreferences(KEY, Activity.MODE_PRIVATE);
        weightText = findViewById(R.id.weightView);
        calorieText = findViewById(R.id.calorieView);
        calculator = new Calculator(
                prefGet.getInt(AGEKEY,0),
                Double.longBitsToDouble(prefGet.getLong(HEIGHTKEY,0)),
                Double.longBitsToDouble(prefGet.getLong(WEIGHTKEY,0)),
                prefGet.getString(GENDERKEY,"Male"));
    }

    private boolean checkIfOk(){
        String weightString = weightText.getText().toString();
        String calorieString = calorieText.getText().toString();
        if(TextUtils.isEmpty(weightString)||TextUtils.isEmpty(calorieString)){
            return false;
        }
        double lowestHealthyWeight = lowHealthyBMI * Math.pow((Double.longBitsToDouble(prefGet.getLong(HEIGHTKEY,0)))/100,2);
        idealWeight = Integer.parseInt(weightString);
        dailyCalories = Integer.parseInt(calorieString);
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
            Intent intent = new Intent();
        }
    }
}
