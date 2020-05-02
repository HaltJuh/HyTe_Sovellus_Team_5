package com.example.hyte_projekti;

import android.service.autofill.FieldClassification;

public class Calculator {
    private int age;
    private Double height;
    private Double weight;
    private String gender;
    private Double rmr;
    private int rmrInt;
    private int weeklyCaloriestoBurn;
    private Double caloriesBurned;
    private int caloriesBurnedInt;

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
}
