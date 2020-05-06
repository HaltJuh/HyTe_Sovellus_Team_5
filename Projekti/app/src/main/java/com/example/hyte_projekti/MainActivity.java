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
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Toast;

/**
 * This activity controls which activity is opened when app is started. If app is started for the first time,
 * This activity asks user's information.
 *
 * @author Tommi Vainio, Juho Halttunen, Tino Kankkunen
 * @version 1.0
 */
public class MainActivity extends AppCompatActivity {
    /**
     * All this app's sharedPreference's kay-value pairs' key.
     */
    public static final String KEY = "SavedData";
    /**
     * User's age is saved with this key.
     */
    public static final String AGEKEY = "SavedAge";
    /**
     * User's height is saved with this key.
     */
    public static final String HEIGHTKEY = "SavedHeight";
    /**
     * User's weight is saved with this key.
     */
    public static final String WEIGHTKEY = "SavedWeight";
    /**
     * User's gender is saved with this key.
     */
    public static final String GENDERKEY = "SavedGender";
    /**
     * The value of this key controls which activity is opened when a user starts this app.
     */
    public static final String TARGETACTIVITY = "SavedActivity";
    /**
     * The value of this key tells in which program a user was last time when this app was running.
     */
    public static final String LATESTACTIVITY = "SavedActivityLatest";
    /**
     * If the value of this key is something else than 1, the week plan of the weight loss program is reset.
     */
    public static final String WEEKPLANKEY = "weekPlan";
    /**
     * This String is used as a key to save calories needed to burn weekly.
     */
    public static final String WEEKLYCALORIESTOBURN = "CaloriesToBurn";
    /**
     * This String is used as a key to save the day of creation for the weight loss plan.
     */
    public static final String CREATIONDAY = "CreationDay";

    private int targetActivity;
    private EditText ageView;
    private EditText heightView;
    private EditText weightView;
    private RadioButton maleButton;
    private RadioButton femaleButton;
    private Boolean gender;
    private Boolean clicked;
    private String age;
    private String weight;
    private String height;

    /**
     * Calls latestActivity method.
     * @param savedInstanceState
     */
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        latestActivity();
    }

    /**
     * Checks the value of TARGETACTICITY key. If it is 0, stay in this activity and retrieve all text fields and buttons.
     * If it is 1, go to Program menu.
     */
    public void latestActivity(){
        SharedPreferences prefGet = getSharedPreferences(KEY, Activity.MODE_PRIVATE);
        targetActivity = prefGet.getInt(TARGETACTIVITY, 0);
        if(targetActivity == 0){
            setContentView(R.layout.activity_main);
            ageView = (EditText)findViewById(R.id.ageView);
            heightView = (EditText)findViewById(R.id.heightView);
            weightView = (EditText)findViewById(R.id.weightView);
            maleButton = (RadioButton)findViewById(R.id.maleButton);
            femaleButton = (RadioButton)findViewById(R.id.femaleButton);
            clicked = false;
        }else if(targetActivity == 1){
            SharedPreferences.Editor prefEditor = prefGet.edit();
            prefEditor.putInt(WEEKPLANKEY,0);
            prefEditor.commit();
            Log.i("Activity", "Menu");
            Intent intent = new Intent(this, ProgramMenu.class);
            startActivity(intent);
        }
    }

    /**
     * Checks which radio button is clicked. Also changes clicked to true so that the program knows
     * that either one is clicked.
     *
     * @param view Radio button which is clicked. Either male button or female button.
     */
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

    /**
     * Checks if any of the text fields are empty or have value 0.
     * If so, this method returns false. If not this method returns true.
     *
     * @return true or false
     */
    public Boolean checkIfOk(){
        age = ageView.getText().toString();
        height = heightView.getText().toString();
        weight = weightView.getText().toString();
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

    /**
     * If checkIfOk method returns true, the values of the text fields are saved and
     * ProgramMenu activity is started.
     *
     * @param view Send button that is clicked to perform this method.
     */
    public void buttonPressed(View view){
        if(checkIfOk()){
            Log.i("Mode", "OK");
            SharedPreferences prefPut = getSharedPreferences(KEY, Activity.MODE_PRIVATE);
            SharedPreferences.Editor prefEditor = prefPut.edit();
            prefEditor.putInt(AGEKEY, Integer.parseInt(age));
            prefEditor.putLong(HEIGHTKEY, Double.doubleToRawLongBits(Double.parseDouble(height)));
            prefEditor.putLong(WEIGHTKEY, Double.doubleToRawLongBits(Double.parseDouble(weight)));
            if (gender){
                prefEditor.putString(GENDERKEY, "Female");
            }else{
                prefEditor.putString(GENDERKEY, "Male");
            }
            prefEditor.commit();

            Intent intent = new Intent(this, ProgramMenu.class);
            startActivity(intent);
        }

    }
}
