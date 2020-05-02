package com.example.hyte_projekti;

import androidx.appcompat.app.AppCompatActivity;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.widget.ArrayAdapter;
import android.widget.ListView;

public class ExActivityThree extends AppCompatActivity {

    public static final String EXTRA = "MESSAGE";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_ex_three);

        SharedPreferences preferences = getSharedPreferences("PREFS", 0);
        boolean ifShowDialog = preferences.getBoolean("showDialog", true);
        if(ifShowDialog) {
            showDialog();
        }

        ListView lv = findViewById(R.id.muscleExerciseList);

        lv.setAdapter(new ArrayAdapter<Exercise>(
                this,
                android.R.layout.simple_list_item_1,
                GymExerciseList.getThisInstance().getGymExercises()
        ));
        lv.setOnItemClickListener((adapterView, view, i, l) -> {
            Intent nextActivity = new Intent(ExActivityThree.this, MuscleBuildingExerciseInfo.class);
            nextActivity.putExtra(EXTRA, i);
            startActivity(nextActivity);
        });
    }
    private void showDialog() {
        AlertDialog.Builder builder = new AlertDialog.Builder(ExActivityThree.this);
        builder.setTitle("Muscle Building Info");
        builder.setMessage("We suggest a combination of 3-5 gym activities per week!\n" +
                "The key to muscle building is also rest and proper diet,\n" +
                "so remember to eat well and enough according to your goals!");
        builder.setCancelable(false);
        builder.setNeutralButton("Don't show me this again", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i){
                dialogInterface.dismiss();

                SharedPreferences preferences = getSharedPreferences("PREFS", 0);
                SharedPreferences.Editor editor = preferences.edit();
                editor.putBoolean("showDialog", false);
                editor.apply();
            }
        });
        builder.setPositiveButton("OK", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i){
                dialogInterface.dismiss();
            }
        });
        AlertDialog alertDialog = builder.create();
        alertDialog.setCanceledOnTouchOutside(false);
        alertDialog.show();
    }
}
