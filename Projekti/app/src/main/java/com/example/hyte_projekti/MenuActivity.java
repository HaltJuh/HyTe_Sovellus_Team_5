package com.example.hyte_projekti;

import androidx.appcompat.app.AppCompatActivity;

import android.app.Activity;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.widget.TextView;

public class MenuActivity extends AppCompatActivity {
    private TextView tvheight;
    private Double height;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_menu);
        Intent intent = getIntent();
        SharedPreferences prefGet = getSharedPreferences(MainActivity.KEY, Activity.MODE_PRIVATE);
        height = Double.longBitsToDouble(prefGet.getLong(MainActivity.HEIGHTKEY, 0));
        Log.i("What", Double.toString(height) );
        tvheight = (TextView)findViewById(R.id.heightViewMenu);
        tvheight.setText(Double.toString(height));


    }
}
