package com.example.hyte_projekti;

import android.service.autofill.FieldClassification;

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
    private double caloriesToLose;
    private double extraCalories;
    private int weeklyCaloriestoBurn;
    private Double caloriesBurned;
    private int caloriesBurnedInt;
    private int caloriesBurnedAtGym;

    public Calculator(int age, Double height, Double weight, String gender){
        this.age = age;
        this.height = height;
        this.weight = weight;
        this.gender = gender;
    }

    public int getRmr(){
       if(gender.equals("Male")){
           rmr = (88.362 + (13.397 * weight) + (4.799 * height) - (5.677 * age)) * 1.5;
       }else{
           rmr = (447.593 + (9.247 * weight) + (3.098 * height) - (4.330 * age)) * 1.5;
       }
       rmrInt = (int) (Math.round(rmr));
       return rmrInt;
    }

    public int getCaloriesBurned(double timeInHours, double metValue) {
        calBurned = (int) Math.round( this.weight * metValue * timeInHours);
        return calBurned;
    }
    public double getCaloriesToBurnPerWeek(double dailyCalories){
        extraCalories = dailyCalories - getRmr();
        caloriesToLose = (KILOSTOLOSEPERWEEK*KALORIESTOLOSEKGMULTIPLIER)+(extraCalories*7);
        return caloriesToLose;
    }
    public int getWeeksToLoseAllExtraWeight(int idealWeight,double dailyCalories){
        double caloriesToBurnPerWeek = getCaloriesToBurnPerWeek(dailyCalories);
        double totalCaloriesToBurn = getTotalCaloriesToBurn(idealWeight,weight);
        int weeks = (int)Math.round(totalCaloriesToBurn/caloriesToBurnPerWeek);
        return weeks;
    }
    public double getTotalCaloriesToBurn(int targetWeight,double currentWeight){
        double kilosToLose = currentWeight-targetWeight;
        return kilosToLose*KALORIESTOLOSEKGMULTIPLIER;
    }
    public int getWeeklyCaloriesToBurn(int caloriesEaten){
        weeklyCaloriestoBurn = (7 * caloriesEaten) - (7 * getRmr());
        return  weeklyCaloriestoBurn;
    }

    public int getCaloriesBurned(Double multiplier, int time){
        Double timeDouble = Double.valueOf(time);
        caloriesBurned = multiplier * this.weight * (timeDouble/60);
        caloriesBurnedInt = (int) Math.round(caloriesBurned);
        return caloriesBurnedInt;
    }

    public int getCalPerDay(int time) {
        Double timeDouble = Double.valueOf(time);
        caloriesBurnedAtGym = (int) Math.round(5.0 * this.weight * (timeDouble/60));
        calPerDay = caloriesBurnedAtGym;
        return calPerDay;
    }
    /*
    public int getCaloriesPerDay() {
        calPerDay = getRmr() + getCaloriesBurned();

        return calPerDay;
    }
     */
}
