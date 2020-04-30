package com.example.hyte_projekti;

import androidx.appcompat.app.AppCompatActivity;

import android.app.Activity;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.Gravity;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

public class ProgramMenu extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_program_menu);
        SharedPreferences prefPut = getSharedPreferences(MainActivity.KEY, Activity.MODE_PRIVATE);
        SharedPreferences.Editor prefEditor = prefPut.edit();
        prefEditor.putInt(MainActivity.TARGETACTIVITY, 1);
        prefEditor.commit();
    }

    public void goToMuscle(View view) {
        saveLatestActivity(3);
        Intent intentMuscle = new Intent(this, BuildMuscle.class);
        startActivity(intentMuscle);
        Toast muscleToast = Toast.makeText(ProgramMenu.this, "You chose building muscle, awesome! \n\nPick an activity from the list to include in your weekly program.", Toast.LENGTH_LONG);
        TextView toastMuscleText = (TextView) muscleToast.getView().findViewById(android.R.id.message);
        if(toastMuscleText != null) toastMuscleText.setGravity(Gravity.CENTER);
        muscleToast.setGravity(Gravity.CENTER_VERTICAL | Gravity.CENTER_HORIZONTAL, 0,0);
        muscleToast.show();
    }

    public void goToLoseWeight(View view) {
        saveLatestActivity(1);
        Intent intentWeight = new Intent(this, ExActivityOne.class);
        startActivity(intentWeight);
        Toast weightToast = Toast.makeText(ProgramMenu.this, "You chose weight loss, great! \n\nNext Enter your ideal weight and how many calories you approximately eat in a day.", Toast.LENGTH_LONG);
        TextView toastWeightText = (TextView) weightToast.getView().findViewById(android.R.id.message);
        if(toastWeightText != null) toastWeightText.setGravity(Gravity.CENTER);
        weightToast.setGravity(Gravity.CENTER_VERTICAL | Gravity.CENTER_HORIZONTAL, 0,0);
        weightToast.show();

    }

    public void goToMaintain(View view) {
        saveLatestActivity(2);
        Intent intentMaintain = new Intent(this, MaintainFitnessLevel.class);
        startActivity(intentMaintain);
        Toast maintainToast = Toast.makeText(ProgramMenu.this, "Fantastic choice! \n\nNext pick an activity from the list to include in your weekly program.", Toast.LENGTH_LONG);
        TextView toastMaintainText = (TextView) maintainToast.getView().findViewById(android.R.id.message);
        if(toastMaintainText != null) toastMaintainText.setGravity(Gravity.CENTER);
        maintainToast.setGravity(Gravity.CENTER_VERTICAL | Gravity.CENTER_HORIZONTAL, 0,0);
        maintainToast.show();
    }

    public void saveLatestActivity(int i){
        SharedPreferences prefPut = getSharedPreferences(MainActivity.KEY, Activity.MODE_PRIVATE);
        SharedPreferences.Editor prefEditor = prefPut.edit();
        prefEditor.putInt(MainActivity.LATESTACTIVITY, i);
        prefEditor.commit();
    }

}
