package com.example.hyte_projekti;

import android.os.Bundle;
import android.provider.Settings;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import androidx.appcompat.app.AppCompatActivity;

public class WeightLossWeekPlan extends AppCompatActivity {

    DaysList days;
    ListView weekList;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_weight_loss_week_planner);
        Bundle b = getIntent().getExtras();
        days = DaysList.getInstance();
        weekList = findViewById(R.id.weekList);
        weekList.setAdapter(new ArrayAdapter<Days>(
                this,
                R.layout.days_item_layout,
                days.getDays())
        );
    }
}
