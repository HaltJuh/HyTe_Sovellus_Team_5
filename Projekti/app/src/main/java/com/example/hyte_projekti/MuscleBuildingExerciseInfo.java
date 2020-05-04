package com.example.hyte_projekti;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.widget.TextView;

public class MuscleBuildingExerciseInfo extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_muscle_building_exercise_info);
        Bundle b = getIntent().getExtras();
        int i = b.getInt(ExActivityThree.EXTRA, 0);


        ((TextView)findViewById(R.id.infoName)).setText(GymExerciseList.getThisInstance().getGymExercises().get(i).getName());

        ((TextView)findViewById(R.id.infoMuscleOptions)).setText("1. Maximum muscle building\t\t4-5 repetitions/set\n" +
                "2. Standard mucle building plan\t\t6-8 repetitions/set\n" +
                "3. Lean muscle building & toning\t10-12 repetitions/set\n\n" +
                "Do 3 sets of each desired exercise.\n" +
                "Remember to warm up and train with a suitable weight for your needs!");

        ((TextView)findViewById(R.id.infoExercise)).setText(GymExerciseList.getThisInstance().getGymExercises().get(i).getInfo());

    }
}
