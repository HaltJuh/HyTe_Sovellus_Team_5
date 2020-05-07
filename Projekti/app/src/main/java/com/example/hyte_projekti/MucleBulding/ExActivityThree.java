package com.example.hyte_projekti.MucleBulding;

import androidx.appcompat.app.AppCompatActivity;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import com.example.hyte_projekti.General.Exercise;
import com.example.hyte_projekti.R;

/**
 * ExActivityThree creates an interactable ListView based on the GymExerciseList Singleton and displays the names of each exercise.
 * This class also directs the user into MuscleBuildingExerciseInfo to display more information on the selected workout.
 * @author Tino Kankkunen
 * @version 1.0
 */
public class ExActivityThree extends AppCompatActivity {

    /**
     * EXEXTRA holds the values of all of the muscle building exercises.
     * Final int "k" holds the value of the selected day from MuscleDayList.EXTRA
     * this is used to save the selected workout into the currently selected day
     */
    public static final String EXEXTRA = "EXMESSAGE";

    /**
     * onCreate() method creates an interactable listView that holds the names of all of the different muscle building exercises to choose from.
     * it also transfers the choses exercise and day to the next activity MuscleBuildingExerciseInfo.
     * Calls the showDialog() function if it hasn't been called off (read below).
     * @param savedInstanceState
     * @see MuscleDayList for EXTRA value
     */
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_ex_three);

        SharedPreferences preferences = getSharedPreferences("PREFS", 0);
        boolean ifShowDialog = preferences.getBoolean("showDialog", true);
        if(ifShowDialog) {
            showDialog();
        }
        Bundle b = getIntent().getExtras();
        final int k = b.getInt(MuscleDayList.EXTRA, 0);
        ListView lv = findViewById(R.id.muscleExerciseList);

        lv.setAdapter(new ArrayAdapter<Exercise>(
                this,
                android.R.layout.simple_list_item_1,
                GymExerciseList.getThisInstance().getGymExercises()
        ));
        lv.setOnItemClickListener((adapterView, view, i, l) -> {
            Intent nextActivity = new Intent(ExActivityThree.this, MuscleBuildingExerciseInfo.class);
            nextActivity.putExtra(EXEXTRA, i);
            nextActivity.putExtra(MuscleDayList.EXTRA, k);
            startActivity(nextActivity);
        });
    }

    /**
     * showDialog() method is purely for giving information to the user about the muscle building program itself and how to interpret it in every day life.
     * This method creates an AlertDialog that pops up for the user. It also uses SharedPreferences for an option to never show it again if the user
     * decides to click on the "Don't show me this again" -button.
     */
    private void showDialog() {
        AlertDialog.Builder builder = new AlertDialog.Builder(ExActivityThree.this);
        builder.setTitle("Muscle Building Info");
        builder.setMessage("We suggest a combination of 3-5 gym activities per week!\n" +
                "The key to muscle building is also rest and proper diet,\n" +
                "so remember to eat well and enough according to your goals!");
        builder.setCancelable(false);
        builder.setNeutralButton("Don't show me this again", new DialogInterface.OnClickListener() {
            /**
             * the onClick of the AlertDialogs NeutralButton will dismiss the dialog and save a value to SharedPreferences
             * for the dialog to never be shown again to the user.
             * @param dialogInterface
             * @param i
             */
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
            /**
             * the onClick of AlertDialogs PositiveButton will just dismiss the dialog and continute the program.
             * @param dialogInterface
             * @param i
             */
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
