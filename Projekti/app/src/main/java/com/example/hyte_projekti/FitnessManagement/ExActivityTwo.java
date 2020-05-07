package com.example.hyte_projekti.FitnessManagement;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import com.example.hyte_projekti.Exercise;
import com.example.hyte_projekti.R;

/**
 * This activity shows Maintain Fitness exercise list.
 * By clicking any exercise of that list, the user can see more information about that exercise.
 *
 * @author Tommi Vainio
 * @version 1.0
 */
public class ExActivityTwo extends AppCompatActivity {
    /**
     * The key for the index of the exercise.
     */
    public static final String EXEXTRA = "EXMESSAGE";

    /**
     * Creates exercise list and sets onItemClickListener to it.
     * <p>
     * This method receives the index of the day of the week and puts it into extra.
     * This extra alongside the other extra which is the index of the exercise, is sent
     * to SelectEx activity when a user clicks one list item.
     *
     * @param savedInstanceState
     */
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_ex_two);
        Bundle b = getIntent().getExtras();
        final int k = b.getInt(DaysActivity.EXTRA, 0);
        ListView lv = findViewById(R.id.exerciseList);
        lv.setAdapter(new ArrayAdapter<Exercise>(
                this,
                R.layout.days_item_layout,
                ExercisesListTwo.getInstance().getExercises()
        ));
        lv.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                    Intent nextActivity = new Intent(ExActivityTwo.this, SelectEx.class);
                    nextActivity.putExtra(EXEXTRA, i);
                    nextActivity.putExtra(DaysActivity.EXTRA, k);
                    startActivity(nextActivity);


            }
        });
    }
}
