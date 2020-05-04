package com.example.hyte_projekti;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;

public class ExActivityTwo extends AppCompatActivity {
    public static final String EXEXTRA = "EXMESSAGE";
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
