package com.example.hyte_projekti;

import androidx.appcompat.app.AppCompatActivity;

import android.app.Activity;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.os.Handler;
import android.util.Log;

/**
 * @author Tino Kankkunen
 * @version 1.0
 * IconScreen activity is the main displayable screen when opening Fit Summit -application. This is purely for a visually enhanced and more pleasing look of the app.
 * This activity has the logo of the application with a fitting theme background and the name of the creators "Team 5"
 */
public class IconScreen extends AppCompatActivity {

    private int targetLastActivity;

    /**
     * onCreate calls the isItLatestActivity() method onCreate()
     * @param savedInstanceState
     */
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

    /**
     * isItLatestActivity() Method is purely for navigation purposes. On launching the app it jumps to IconScreen after which this method is called.
     * This method checks what was the latest activity through a SharedPreference in MainActivity and then according to its value either displays MainActivity OR
     * ProgramMenu after a delay of 2000-3000 millis.
     */
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
