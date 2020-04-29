package com.example.hyte_projekti;

import androidx.appcompat.app.AppCompatActivity;

import android.app.Activity;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;

public class DaysActivity extends AppCompatActivity {
    public static final String EXTRA = "MESSAGE";
    private int latestActivity;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_days);
        SharedPreferences prefGet = getSharedPreferences(MainActivity.KEY, Activity.MODE_PRIVATE);
        latestActivity = prefGet.getInt(MainActivity.LATESTACTIVITY, 0);
        ListView lv = findViewById(R.id.daysListView);
        lv.setAdapter(new ArrayAdapter<Days>(
                this,
                R.layout.days_item_layout,
                DaysList.getInstance().getDays()
        ));

        lv.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                if(latestActivity == 1) {
                    Intent nextActivity = new Intent(DaysActivity.this, ExActivityOne.class);
                    nextActivity.putExtra(EXTRA, i);
                    startActivity(nextActivity);
                }
                if (latestActivity == 2){
                    Intent nextActivity = new Intent(DaysActivity.this, ExActivityTwo.class);
                    nextActivity.putExtra(EXTRA, i);
                    startActivity(nextActivity);
                }
                if (latestActivity == 3){
                    Intent nextActivity = new Intent(DaysActivity.this, ExActivityThree.class);
                    nextActivity.putExtra(EXTRA, i);
                    startActivity(nextActivity);
                }
            }
        });
    }
}
