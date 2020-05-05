package com.example.hyte_projekti;

import androidx.appcompat.app.AppCompatActivity;

import android.app.Activity;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.os.Handler;
import android.util.Log;

public class IconScreen extends AppCompatActivity {

    private int targetLastActivity;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_icon_screen);

        /*
        // THIS CODE RESETS THE SHARED PREFS!!!
        SharedPreferences preferences =getSharedPreferences(MainActivity.KEY,Activity.MODE_PRIVATE);
        SharedPreferences.Editor editor = preferences.edit();
        editor.clear();
        editor.apply();
        */

        isItLatestActivity();

    }

    public void isItLatestActivity(){
        SharedPreferences prefGet = getSharedPreferences(MainActivity.KEY, Activity.MODE_PRIVATE);
        targetLastActivity = prefGet.getInt(MainActivity.TARGETACTIVITY, 0);
        if(targetLastActivity == 0){
            Log.i("Activity", "Main");
            Intent intent = new Intent(this, MainActivity.class);
            Handler handler = new Handler();
            handler.postDelayed(new Runnable() {
                public void run() {
                    startActivity(intent);
                }
            }, 3000);
        }else if(targetLastActivity == 1){
            SharedPreferences.Editor prefEditor = prefGet.edit();
            prefEditor.putInt(MainActivity.WEEKPLANKEY,0);
            prefEditor.commit();
            Log.i("Activity", "Menu");
            Intent intent2 = new Intent(this, ProgramMenu.class);
            Handler handler = new Handler();
            handler.postDelayed(new Runnable() {
                public void run() {
                    startActivity(intent2);
                }
            }, 2000);
        }
    }
}
