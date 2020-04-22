package com.example.hyte_projekti;

import androidx.appcompat.app.AppCompatActivity;

import android.app.Activity;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity {
    public static final String KEY = "SavedData";
    private int targetActivity;
    private EditText ageView;
    private EditText heightView;
    private EditText weightView;
    private RadioButton maleButton;
    private RadioButton femaleButton;
    private Boolean gender;
    private Boolean clicked;
    private String errorMessage;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        latestActivity();
        ageView = (EditText)findViewById(R.id.ageView);
        heightView = (EditText)findViewById(R.id.heightView);
        weightView = (EditText)findViewById(R.id.weightView);
        maleButton = (RadioButton)findViewById(R.id.maleButton);
        femaleButton = (RadioButton)findViewById(R.id.femaleButton);
        clicked = false;
    }

    public void latestActivity(){
        SharedPreferences prefGet = getSharedPreferences(KEY, Activity.MODE_PRIVATE);
        targetActivity = prefGet.getInt("targetActivity", 0);

        if(targetActivity == 1){
            Log.i("Activity", "Menu");
        }
    }

    public void radioButtonClicked(View view){
        clicked = true;
        RadioGroup radioGroup = (RadioGroup)findViewById(R.id.radioGroupGender);
        int selectedId = radioGroup.getCheckedRadioButtonId();
        RadioButton selectedButton = (RadioButton)findViewById(selectedId);

        if(selectedButton == maleButton){
            gender = false;
        }
        if(selectedButton == femaleButton){
            gender = true;
        }
    }

    public Boolean checkIfOk(){
        String age = ageView.getText().toString();
        String height = heightView.getText().toString();
        String weight = weightView.getText().toString();

        if(TextUtils.isEmpty(age)){
            Toast.makeText(this, "Please enter your age.", Toast.LENGTH_SHORT).show();
            return false;
        }
        if(TextUtils.isEmpty(height)){
            Toast.makeText(this, "Please enter your height.", Toast.LENGTH_SHORT).show();
            return false;
        }
        if(TextUtils.isEmpty(weight)){
            Toast.makeText(this, "Please enter your weight.", Toast.LENGTH_SHORT).show();
            return false;
        }
        if(age.equals("0") || height.equals("0") || weight.equals("0")){
            Toast.makeText(this, "Invalid answer!", Toast.LENGTH_SHORT).show();
            return false;
        }
        if(!clicked){
            Toast.makeText(this, "Please choose your gender.", Toast.LENGTH_SHORT).show();
            return false;
        }
        return true;
    }

    public void buttonPressed(View view){
        if(checkIfOk()){
            Log.i("Mode", "OK");
        }

    }
}
