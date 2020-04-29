package com.example.hyte_projekti;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.widget.ArrayAdapter;
import android.widget.ListView;

public class DaysActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_days);
        ListView lv = findViewById(R.id.daysListView);
        lv.setAdapter(new ArrayAdapter<Days>(
                this,
                R.layout.days_item_layout,
                DaysList.getInstance().getDays()
        ));
    }
}
