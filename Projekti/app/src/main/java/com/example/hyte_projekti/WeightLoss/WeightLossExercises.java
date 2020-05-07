package com.example.hyte_projekti.WeightLoss;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import androidx.appcompat.app.AppCompatActivity;

import com.example.hyte_projekti.Exercise;
import com.example.hyte_projekti.R;

import static com.example.hyte_projekti.WeightLoss.WeightLossWeekPlan.EXTRA_WEEK_INDEX;

/**
 * Creates a list of available weightloss exercises.
 * @author Juho Halttunen
 * @version 1.0
 */
public class WeightLossExercises extends AppCompatActivity {
    /**
     * The value of this String is used to get the extra that holds the index of the exercise.
     */
    public static final String EXTRA_EXERCISE_INDEX = "exerciseIndex";

    private ListView exerciseView;
    private WeightLossList weightLossList;
    private int dayIndex;
    @Override

    /**
     * Instantiates needed fields and setups listview to open WeightLossExercise class
     */
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_weight_loss_exercises);
        Bundle extras = getIntent().getExtras();
        dayIndex = extras.getInt(EXTRA_WEEK_INDEX);
        weightLossList = WeightLossList.getInstance();
        exerciseView = findViewById(R.id.weightLossExercises);
        exerciseView.setAdapter(new ArrayAdapter<Exercise>(
                this,
                R.layout.days_item_layout,
                weightLossList.getWeightLossList()
        ));
        exerciseView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int i, long id) {
                Intent intent = new Intent(WeightLossExercises.this, WeightLossExercise.class);
                intent.putExtra(EXTRA_EXERCISE_INDEX,i);
                intent.putExtra(EXTRA_WEEK_INDEX,dayIndex);
                startActivity(intent);
            }
        });
    }
}
