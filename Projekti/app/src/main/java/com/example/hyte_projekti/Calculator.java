package com.example.hyte_projekti;

import android.util.Log;

public class Calculator {
    public static final int KALORIESTOLOSEKGMULTIPLIER = 9000;
    public static final double KILOSTOLOSEPERWEEK = 0.75;

    private int age;
    private Double height;
    private Double weight;
    private String gender;
    private Double rmr;
    private int rmrInt;
    private int calBurned;
    private int calPerDay;
    private double kilosToLose;
    private double caloriesToLose;
    private double extraCalories;

    public Calculator(int age, Double height, Double weight, String gender){
        this.age = age;
        this.height = height;
        this.weight = weight;
        this.gender = gender;
    }

    public int getRmr(){
       if(gender.equals("Male")){
           rmr = (88.362 + (13.397 * weight) + (4.799 * height) - (5.677 * age)) * 1.3;
       }else{
           rmr = (447.593 + (9.247 * weight) + (3.098 * height) - (4.330 * age)) * 1.3;
       }
       rmrInt = (int) Math.round(rmr);
       return rmrInt;
    }

    public int getCaloriesBurned(double timeInHours, double metValue) {
        calBurned = (int) Math.round( this.weight * metValue * timeInHours);

        return calBurned;
    }
    public double getCaloriesToBurnPerWeek(double dailyCalories){
        extraCalories = dailyCalories - getRmr();
        caloriesToLose = (KILOSTOLOSEPERWEEK*KALORIESTOLOSEKGMULTIPLIER)+(extraCalories*7);
        Log.d("calories", "getCaloriesToBurnPerWeek: "+dailyCalories);
        return caloriesToLose;
    }
    public int getWeeksToLoseAllExtraWeight(int idealWeight,double dailyCalories){
        double caloriesToBurnPerWeek = getCaloriesToBurnPerWeek(dailyCalories);
        kilosToLose = weight-idealWeight;
        double totalCaloriesToBurn = kilosToLose * KALORIESTOLOSEKGMULTIPLIER;
        int weeks = (int)Math.round(totalCaloriesToBurn/caloriesToBurnPerWeek);
        return weeks;
    }

    /*
    public int getCaloriesPerDay() {
        calPerDay = getRmr() + getCaloriesBurned();

        return calPerDay;
    }
     */
}
