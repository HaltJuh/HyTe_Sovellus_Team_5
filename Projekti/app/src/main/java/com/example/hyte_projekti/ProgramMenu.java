package com.example.hyte_projekti;

import androidx.appcompat.app.AppCompatActivity;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.Gravity;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import com.example.hyte_projekti.WeightLoss.ExActivityOne;

/**
 * In this activity a user can choose to which program he/she wants to go. There is buttons for all three
 * programs. A user can also change his/her information by clicking the button at the bottom of the screen.
 *
 * @author Tommi Vainio, Tino Kankkunen, Juho Halttunen
 * @version 1.0
 */
public class ProgramMenu extends AppCompatActivity {
    /**
     * The value of this key controls which activity is opened when a user clicks the button of
     * Maintain fitness program.
     */
    public static final String ISITFIRSTTIME = "FirstTimeOrNot";
    /**
     * The value of this key controls which activity is opened when a user clicks the button of
     * Muscle building program.
     */
    public static final String MUSCLEPROGRAMRESET = "MuscleProgram";
    private Boolean clicked;
    private int whichProgram;
    private int isItFirstTime;
    private int muscleReset;
    private int weightLoss;

    /**
     * Sets clicked to false and retrieves values that are needed to check if there are a week plan
     * created in any of the three programs.
     * @param savedInstanceState
     */
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_program_menu);
        SharedPreferences prefPut = getSharedPreferences(MainActivity.KEY, Activity.MODE_PRIVATE);
        SharedPreferences.Editor prefEditor = prefPut.edit();
        prefEditor.putInt(MainActivity.TARGETACTIVITY, 1);
        prefEditor.commit();
        clicked = false;
        SharedPreferences prefGet = getSharedPreferences(MainActivity.KEY, Activity.MODE_PRIVATE);
        isItFirstTime = prefGet.getInt(ISITFIRSTTIME, 0);
        muscleReset = prefGet.getInt(MUSCLEPROGRAMRESET, 0);
        weightLoss = prefGet.getInt(MainActivity.WEEKPLANKEY, 0);
    }
    /**
     * goToMuscle() method is an onClick() method initiated by a button in the activity.
     * This method checks the value of MUSCLEPROGRAMRESET SharedPreference for an existing week plan. Depending on the value this method
     * will direct the user to a different activity from BuildMuscle the introduction, MuscleWeekPlanActivity for an existing weekly plan or
     * MuscleDayList to create a new plan.
     * This method also checks for other existing plans from the saved values of isItFirstTime, muscleReset and weightLoss.
     * If there is no week plans in any of the programs it triggers the muscleFunction() method.
     * If there IS an existing week plan in a program, an alert is triggered by the alertOneButton() which will ask the user if he/she wants to
     * overwrite the existing plan.
     * @see ProgramMenu for muscleFunction() method
     * @see ProgramMenu for alertOneButton() method
     */
    public void goToMuscle(View view) {
        if (isItFirstTime == 0 && muscleReset == 0 && weightLoss == 0) {
            muscleFunction();
        } else if (!clicked && muscleReset == 0) {
            whichProgram = 3;
            alertOneButton();
        } else {
            muscleFunction();
        }
    }
    /**
     * goToLoseWeight() method is an onClick() method initiated by a button in the activity.
     * This method checks for existing week plans in all of the programs through the saved variables of
     * isItFirstTime, muscleReset and weightLoss. If there is no existing plan, the weightLossFunction() method is called and the user is directed to the next activity.
     * If there IS an existing plan, the user will be shown an Alert Dialog by the alertOneButton() method to confirm if he/she wants to overwrite the current existing plan
     * and continue or cancel the process.
     * @see ProgramMenu for weightLossFunction() method
     * @see ProgramMenu for alertOneButton() method
     */
    public void goToLoseWeight(View view) {
        if(isItFirstTime == 0 && muscleReset == 0 && weightLoss == 0) {
            weightLossFunction();
        }else if(!clicked && weightLoss == 0){
            whichProgram = 1;
            alertOneButton();
        }
        else {
            weightLossFunction();
        }
    }
    /**
     * goToMaintain() method is an onClick() method initiated by a button in the activity.
     * This method checks for existing week plans in all of the programs through the saved variables of
     * isItFirstTime, muscleReset and weightLoss. If there is no existing plan, the maintainFunction() method is called and the user is directed to the next activity.
     * If there IS an existing plan, the user will be shown an Alert Dialog by the alertOneButton() method to confirm if he/she wants to overwrite the current existing plan
     * and continue or cancel the process.
     * @see ProgramMenu for maintainFunction() method
     * @see ProgramMenu for alertOneButton() method
     */
    public void goToMaintain(View view) {
        if(isItFirstTime == 0 && muscleReset == 0 && weightLoss == 0) {
            maintainFunction();
        }else if(!clicked && isItFirstTime == 0){
            whichProgram = 2;
            alertOneButton();
        }
        else {
            maintainFunction();
        }
    }

    /**
     * Changes the value of LATESTACTIVITY key depending on to which program's button a user cliks.
     * @param i 1-3 depending on program.
     */
    public void saveLatestActivity(int i){
        SharedPreferences prefPut = getSharedPreferences(MainActivity.KEY, Activity.MODE_PRIVATE);
        SharedPreferences.Editor prefEditor = prefPut.edit();
        prefEditor.putInt(MainActivity.LATESTACTIVITY, i);
        prefEditor.commit();
    }

    /**
     * Changes the value of TARGETACTIVITY Key to 0 and starts MainActivity so that
     * a user can change his/her information.
     *
     * @param view Change your information button that is clicked to perform this method.
     */
    public void changeInfoClicked(View view){
        Intent intentMaintain = new Intent(this, MainActivity.class);

        AlertDialog.Builder builder = new AlertDialog.Builder(ProgramMenu.this);
        builder.setCancelable(true);
        builder.setTitle("Are you sure?");
        builder.setMessage("You will have to enter user information again.");

        builder.setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                dialog.cancel();
            }
        });
        builder.setPositiveButton("OK", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                SharedPreferences prefPut = getSharedPreferences(MainActivity.KEY, Activity.MODE_PRIVATE);
                SharedPreferences.Editor prefEditor = prefPut.edit();
                prefEditor.putInt(MainActivity.TARGETACTIVITY, 0);
                prefEditor.commit();
                startActivity(intentMaintain);
            }
        }).show();
    }

    /**
     * Creates Alert Dialog with OK button and Cancel button and if a user clicks OK button, this method calls method which
     * called this method so that the method called will not call this method again.
     * If a user clicks Cancel button this method does nothing.
     */
    public void alertOneButton() {
        AlertDialog.Builder builder = new AlertDialog.Builder(ProgramMenu.this);
        builder.setCancelable(true);
        builder.setTitle("Are you sure?");
        builder.setMessage("Your week plan in other program will be deleted.");

        builder.setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                dialog.cancel();
            }
        });
        builder.setPositiveButton("OK", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                if(whichProgram == 1){
                    View button = findViewById(R.id.button4);
                    clicked = true;
                    goToLoseWeight(button);
                }
                if(whichProgram == 2) {
                    View button = findViewById(R.id.button5);
                    clicked = true;
                    goToMaintain(button);
                }
                if(whichProgram == 3){
                    View button = findViewById(R.id.button6);
                    clicked = true;
                    goToMuscle(button);
                }
            }
        }).show();
    }

    /**
     * This method checks if Maintain Fitness program is latest program a user has used.
     * If it is, this method starts the activity where a user was last time he/she used Maintain Fitness program.
     * If it is not, this method resets other programs and and starts MaintainFitnessLevel activity.
     */
    public void maintainFunction(){
        SharedPreferences prefGet = getSharedPreferences(MainActivity.KEY, Activity.MODE_PRIVATE);
        int firstTime = prefGet.getInt(ISITFIRSTTIME, 0);
        if (firstTime == 0) {
            SharedPreferences prefPut = getSharedPreferences(MainActivity.KEY, Activity.MODE_PRIVATE);
            SharedPreferences.Editor prefEditor = prefPut.edit();
            prefEditor.putInt(MUSCLEPROGRAMRESET, 0);
            prefEditor.putInt(MuscleDayList.RESET, 0);
            prefEditor.putInt(MainActivity.WEEKPLANKEY, 0);
            prefEditor.commit();
            saveLatestActivity(2);
            Intent intentMaintain = new Intent(this, MaintainFitnessLevel.class);
            startActivity(intentMaintain);
            Toast maintainToast = Toast.makeText(ProgramMenu.this, "Fantastic choice! \n\nNext pick an activity from the list to include in your weekly program.", Toast.LENGTH_LONG);
            TextView toastMaintainText = (TextView) maintainToast.getView().findViewById(android.R.id.message);
            if (toastMaintainText != null) toastMaintainText.setGravity(Gravity.CENTER);
            maintainToast.setGravity(Gravity.CENTER_VERTICAL | Gravity.CENTER_HORIZONTAL, 0, 0);
            maintainToast.show();
        } else if (firstTime == 1) {
            Intent intentMaintain = new Intent(this, WeekPlanActivity.class);
            startActivity(intentMaintain);
        } else {
            Intent intentMaintain = new Intent(this, DaysActivity.class);
            startActivity(intentMaintain);
        }
    }
    /**
     * This method checks if Muscle Building program is latest program a user has used.
     * If it is, this method starts the activity where a user was last time he/she used Muscle Building program.
     * If it is not, this method resets other programs and and starts BuildMuscle activity.
     */
    public void muscleFunction(){
        SharedPreferences prefGet = getSharedPreferences(MainActivity.KEY, Activity.MODE_PRIVATE);
        int muscleProgram = prefGet.getInt(MUSCLEPROGRAMRESET, 0);
        if(muscleProgram == 0) {
            SharedPreferences prefPut = getSharedPreferences(MainActivity.KEY, Activity.MODE_PRIVATE);
            SharedPreferences.Editor prefEditor = prefPut.edit();
            prefEditor.putInt(ISITFIRSTTIME, 0);
            prefEditor.putInt(DaysActivity.RESET, 0);
            prefEditor.putInt(MainActivity.WEEKPLANKEY, 0);
            prefEditor.commit();
            saveLatestActivity(3);
            Intent intentMuscle = new Intent(this, BuildMuscle.class);
            startActivity(intentMuscle);
        }else if(muscleProgram == 1){
            Intent intentMuscle = new Intent(this, MuscleWeekPlanActivity.class);
            startActivity(intentMuscle);
        }else {
            Intent intentMuscle = new Intent(this, MuscleDayList.class);
            startActivity(intentMuscle);
        }
    }

    /**
     * This method resets Maintain Fitness Level and Build Muscle Mass programs and starts ExActivityOne activity.
     */
    public void weightLossFunction(){
        SharedPreferences prefPut = getSharedPreferences(MainActivity.KEY, Activity.MODE_PRIVATE);
        SharedPreferences.Editor prefEditor = prefPut.edit();
        prefEditor.putInt(MUSCLEPROGRAMRESET, 0);
        prefEditor.putInt(MuscleDayList.RESET, 0);
        prefEditor.putInt(ISITFIRSTTIME, 0);
        prefEditor.putInt(DaysActivity.RESET, 0);
        prefEditor.commit();
        saveLatestActivity(1);
        Intent intentWeight = new Intent(this, ExActivityOne.class);
        startActivity(intentWeight);
        Toast weightToast = Toast.makeText(ProgramMenu.this, "You chose weight loss, great! \n\nNext Enter your ideal weight and how many calories you approximately eat in a day.", Toast.LENGTH_LONG);
        TextView toastWeightText = (TextView) weightToast.getView().findViewById(android.R.id.message);
        if(toastWeightText != null) toastWeightText.setGravity(Gravity.CENTER);
        weightToast.setGravity(Gravity.CENTER_VERTICAL | Gravity.CENTER_HORIZONTAL, 0,0);
        weightToast.show();
    }

}
