package com.example.hyte_projekti;

import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import static com.example.hyte_projekti.WeightLossExercises.EXTRA_EXERCISE_INDEX;
import static com.example.hyte_projekti.WeightLossWeekPlan.EXTRA_WEEK_INDEX;

public class WeightLossExercise extends AppCompatActivity {
    private int exerciseIndex;
    private int dayIndex;
    private TextView dayText;
    private TextView exerciseText;
    private TextView infoText;
    private EditText timeText;
    private DaysList days;
    private Exercise exercise;
    private int time;
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_weight_loss_exercise);
        Bundle extras = getIntent().getExtras();
        exerciseIndex = extras.getInt(EXTRA_EXERCISE_INDEX);
        dayIndex = extras.getInt(EXTRA_WEEK_INDEX);
        days = DaysList.getInstance();
        exercise = WeightLossList.getInstance().getWeightLossExercise(exerciseIndex);
        dayText = findViewById(R.id.day);
        exerciseText = findViewById(R.id.exercise);
        infoText = findViewById(R.id.exerciseInfo);
        timeText = findViewById(R.id.timeView);
        dayText.setText(days.getDay(dayIndex).getName());
        exerciseText.setText(exercise.getName());
        infoText.setText(exercise.getInfo());

    }
    private boolean checkIfOk(){
        String timeString = timeText.getText().toString();
        try {
            time = Integer.parseInt(timeString);
        }catch (NumberFormatException nfe){
            Log.d("Click","False");
            return false;
        }
        return true;
    }
    public void onClick(View view){
        Log.d("Click","Click");
        Toast.makeText(this,"Toast",Toast.LENGTH_SHORT);
        if(checkIfOk()){
            Log.d("Click",""+time);
            if(time >= 720){
                Toast.makeText(this,"12 hours? Seriously",Toast.LENGTH_LONG).show();
            }
        }
    }
}
