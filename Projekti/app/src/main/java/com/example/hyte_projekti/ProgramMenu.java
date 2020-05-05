package com.example.hyte_projekti;

import androidx.appcompat.app.AppCompatActivity;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.Gravity;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

public class ProgramMenu extends AppCompatActivity {
    public static final String ISITFIRSTTIME = "FirstTimeOrNot";
    public static final String MUSCLEPROGRAMRESET = "MuscleProgram";
    private Boolean clicked;
    private int whichProgram;
    private int isItFirstTime;
    private int muscleReset;
    private int weightLoss;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_program_menu);
        SharedPreferences prefPut = getSharedPreferences(MainActivity.KEY, Activity.MODE_PRIVATE);
        SharedPreferences.Editor prefEditor = prefPut.edit();
        prefEditor.putInt(MainActivity.TARGETACTIVITY, 1);
        prefEditor.commit();
        clicked = false;
        SharedPreferences prefGet = getSharedPreferences(MainActivity.KEY, Activity.MODE_PRIVATE);
        isItFirstTime = prefGet.getInt(ISITFIRSTTIME, 0);
        muscleReset = prefGet.getInt(MUSCLEPROGRAMRESET, 0);
        weightLoss = prefGet.getInt(MainActivity.WEEKPLANKEY, 0);
    }

    public void goToMuscle(View view) {

        SharedPreferences prefGet = getSharedPreferences(MainActivity.KEY, Activity.MODE_PRIVATE);
        int muscleProgram = prefGet.getInt(MUSCLEPROGRAMRESET, 0);
        if(muscleProgram == 0) {
            saveLatestActivity(3);
            Intent intentMuscle = new Intent(this, BuildMuscle.class);
            startActivity(intentMuscle);
        }else if(muscleProgram == 1){
            Intent intentMuscle = new Intent(this, MuscleWeekPlanActivity.class);
            startActivity(intentMuscle);
        }else {
            Intent intentMuscle = new Intent(this, MuscleDayList.class);
            startActivity(intentMuscle);

        if(isItFirstTime == 0 && muscleReset == 0 && weightLoss == 0) {
            muscleFunction();
        }else if(!clicked && muscleReset == 0){
            whichProgram = 3;
            alertOneButton();
        }
        else {
            muscleFunction();

            }
        }
    }

    public void goToLoseWeight(View view) {
        if(isItFirstTime == 0 && muscleReset == 0 && weightLoss == 0) {
            weightLossFunction();
        }else if(!clicked && weightLoss == 0){
            whichProgram = 1;
            alertOneButton();
        }
        else {
            weightLossFunction();
        }
    }

    public void goToMaintain(View view) {
        if(isItFirstTime == 0 && muscleReset == 0 && weightLoss == 0) {
            maintainFunction();
        }else if(!clicked && isItFirstTime == 0){
            whichProgram = 2;
            alertOneButton();
        }
        else {
            maintainFunction();
        }
    }

    public void saveLatestActivity(int i){
        SharedPreferences prefPut = getSharedPreferences(MainActivity.KEY, Activity.MODE_PRIVATE);
        SharedPreferences.Editor prefEditor = prefPut.edit();
        prefEditor.putInt(MainActivity.LATESTACTIVITY, i);
        prefEditor.commit();
    }

    public void changeInfoClicked(View view){
        SharedPreferences prefPut = getSharedPreferences(MainActivity.KEY, Activity.MODE_PRIVATE);
        SharedPreferences.Editor prefEditor = prefPut.edit();
        prefEditor.putInt(MainActivity.TARGETACTIVITY, 0);
        prefEditor.commit();
        Intent intentMaintain = new Intent(this, MainActivity.class);
        startActivity(intentMaintain);
    }

    public void alertOneButton() {
        AlertDialog.Builder builder = new AlertDialog.Builder(ProgramMenu.this);
        builder.setCancelable(true);
        builder.setTitle("Are you sure?");
        builder.setMessage("Your week plan in other program will be deleted.");

        builder.setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                dialog.cancel();
            }
        });
        builder.setPositiveButton("OK", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                if(whichProgram == 1){
                    View button = findViewById(R.id.button4);
                    clicked = true;
                    goToLoseWeight(button);
                }
                if(whichProgram == 2) {
                    View button = findViewById(R.id.button5);
                    clicked = true;
                    goToMaintain(button);
                }
                if(whichProgram == 3){
                    View button = findViewById(R.id.button6);
                    clicked = true;
                    goToMuscle(button);
                }
            }
        }).show();
    }

    public void maintainFunction(){
        SharedPreferences prefGet = getSharedPreferences(MainActivity.KEY, Activity.MODE_PRIVATE);
        int firstTime = prefGet.getInt(ISITFIRSTTIME, 0);
        if (firstTime == 0) {
            SharedPreferences prefPut = getSharedPreferences(MainActivity.KEY, Activity.MODE_PRIVATE);
            SharedPreferences.Editor prefEditor = prefPut.edit();
            prefEditor.putInt(MUSCLEPROGRAMRESET, 0);
            prefEditor.putInt(MuscleDayList.RESET, 0);
            prefEditor.putInt(MainActivity.WEEKPLANKEY, 0);
            prefEditor.commit();
            saveLatestActivity(2);
            Intent intentMaintain = new Intent(this, MaintainFitnessLevel.class);
            startActivity(intentMaintain);
            Toast maintainToast = Toast.makeText(ProgramMenu.this, "Fantastic choice! \n\nNext pick an activity from the list to include in your weekly program.", Toast.LENGTH_LONG);
            TextView toastMaintainText = (TextView) maintainToast.getView().findViewById(android.R.id.message);
            if (toastMaintainText != null) toastMaintainText.setGravity(Gravity.CENTER);
            maintainToast.setGravity(Gravity.CENTER_VERTICAL | Gravity.CENTER_HORIZONTAL, 0, 0);
            maintainToast.show();
        } else if (firstTime == 1) {
            Intent intentMaintain = new Intent(this, WeekPlanActivity.class);
            startActivity(intentMaintain);
        } else {
            Intent intentMaintain = new Intent(this, DaysActivity.class);
            startActivity(intentMaintain);
        }
    }

    public void muscleFunction(){
        SharedPreferences prefGet = getSharedPreferences(MainActivity.KEY, Activity.MODE_PRIVATE);
        int muscleProgram = prefGet.getInt(MUSCLEPROGRAMRESET, 0);
        if(muscleProgram == 0) {
            SharedPreferences prefPut = getSharedPreferences(MainActivity.KEY, Activity.MODE_PRIVATE);
            SharedPreferences.Editor prefEditor = prefPut.edit();
            prefEditor.putInt(ISITFIRSTTIME, 0);
            prefEditor.putInt(DaysActivity.RESET, 0);
            prefEditor.putInt(MainActivity.WEEKPLANKEY, 0);
            prefEditor.commit();
            saveLatestActivity(3);
            Intent intentMuscle = new Intent(this, BuildMuscle.class);
            startActivity(intentMuscle);
        }else if(muscleProgram == 1){
            Intent intentMuscle = new Intent(this, MuscleWeekPlanActivity.class);
            startActivity(intentMuscle);
        }else {
            Intent intentMuscle = new Intent(this, MuscleDayList.class);
            startActivity(intentMuscle);
        }
    }

    public void weightLossFunction(){
        SharedPreferences prefPut = getSharedPreferences(MainActivity.KEY, Activity.MODE_PRIVATE);
        SharedPreferences.Editor prefEditor = prefPut.edit();
        prefEditor.putInt(MUSCLEPROGRAMRESET, 0);
        prefEditor.putInt(MuscleDayList.RESET, 0);
        prefEditor.putInt(ISITFIRSTTIME, 0);
        prefEditor.putInt(DaysActivity.RESET, 0);
        prefEditor.commit();
        saveLatestActivity(1);
        Intent intentWeight = new Intent(this, ExActivityOne.class);
        startActivity(intentWeight);
        Toast weightToast = Toast.makeText(ProgramMenu.this, "You chose weight loss, great! \n\nNext Enter your ideal weight and how many calories you approximately eat in a day.", Toast.LENGTH_LONG);
        TextView toastWeightText = (TextView) weightToast.getView().findViewById(android.R.id.message);
        if(toastWeightText != null) toastWeightText.setGravity(Gravity.CENTER);
        weightToast.setGravity(Gravity.CENTER_VERTICAL | Gravity.CENTER_HORIZONTAL, 0,0);
        weightToast.show();
    }

}
