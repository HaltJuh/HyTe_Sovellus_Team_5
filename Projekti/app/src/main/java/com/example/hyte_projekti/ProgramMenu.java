package com.example.hyte_projekti;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

public class ProgramMenu extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_program_menu);
    }

    public void goToMuscle(View view) {
        Intent intentMuscle = new Intent(this, BuildMuscle.class);
        startActivity(intentMuscle);
    }

    public void goToLoseWeight(View view) {
        Intent intentWeight = new Intent(this, Weightloss.class);
        startActivity(intentWeight);
    }

    public void goToMaintain(View view) {
        Intent intentMaintain = new Intent(this, MaintainFitnessLevel.class);
        startActivity(intentMaintain);
    }

}
