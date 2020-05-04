package com.example.hyte_projekti;

import androidx.appcompat.app.AppCompatActivity;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;

public class muscleDayList extends AppCompatActivity {

    public static final String EXTRA = "MESSAGE";
    private int latestActivity;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_muscle_day_list);
        //Bundle b = getIntent().getExtras();
        //int i = b.getInt(muscleDayList.EXTRA, 0);


        SharedPreferences prefGet = getSharedPreferences(MainActivity.KEY, Activity.MODE_PRIVATE);
        latestActivity = prefGet.getInt(MainActivity.LATESTACTIVITY, 0);
        ListView lv = findViewById(R.id.muscleDayList);
        lv.setAdapter(new ArrayAdapter<Days>(
                this,
                R.layout.days_item_layout,
                DaysList.getInstance().getDays()
        ));

        lv.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                    Intent nextActivity = new Intent(muscleDayList.this, ExActivityThree.class);
                    nextActivity.putExtra(EXTRA, i);
                    startActivity(nextActivity);
            }
        });
    }
}
