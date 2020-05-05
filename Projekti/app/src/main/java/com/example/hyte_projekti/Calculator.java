package com.example.hyte_projekti;

import android.service.autofill.FieldClassification;

/**
 * @author Tino Kankkunen, Juho Halttunen, Tommi Vainio
 * @version 1.0
 * Calculator class is as its name suggests, a class for the sole purpose of calculations with methods used all around the different parts of the application.
 */
public class Calculator {
    /**
     * KALORIESTOLOSEKGMULTIPLIER is in the Calculator in methods getCaloriesToBurnPerWeek(), getKilosLostPerWeek() and getTotalCaloriesToBurn.
     * Holds the amount of calories needed to burn to lose one kilo of weight.
     * KILOSTOLOSEPERWEEK is used in the Calculator in the method getCaloriesToBurnPerWeek() to calculate the amount of calores that must be burned per week to lose desired amout of weight.
     *
     */
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

    /**
     * The Calculator constructor has age, height, weight and gender parameters that are derived from the users input at the start of the program.
     * @param age
     * @param height
     * @param weight
     * @param gender
     */
    public Calculator(int age, Double height, Double weight, String gender){
        this.age = age;
        this.height = height;
        this.weight = weight;
        this.gender = gender;
    }

    /**
     * getRmR() is a method that counts the Resting Metabolic Rate of the user.
     * The methods outcome depends on the users gender input
     * @return integer that is the value of the users Resting Metabolic Rate
     */
    public int getRmr(){
       if(gender.equals("Male")){
           rmr = (88.362 + (13.397 * weight) + (4.799 * height) - (5.677 * age)) * 1.5;
       }else{
           rmr = (447.593 + (9.247 * weight) + (3.098 * height) - (4.330 * age)) * 1.5;
       }
       rmrInt = (int) (Math.round(rmr));
       return rmrInt;
    }

    /**
     * Used in getWeeksToLoseAllExtraWeight() method AND in the WeightLossWeekPlan class's onCreate method.
     * @see WeightLossWeekPlan for usage
     * @param dailyCalories
     * @return returns the total amount of calories to burn per week
     */
    public double getCaloriesToBurnPerWeek(double dailyCalories){
        extraCalories = dailyCalories - getRmr();
        caloriesToLose = (KILOSTOLOSEPERWEEK*KALORIESTOLOSEKGMULTIPLIER)+(extraCalories*7);
        return caloriesToLose;
    }

    /**
     * getKilosLostPerWeek() method returns the amount of how many kilos the user loses per week with the current week plan.
     * it uses the static KALORIESTOLOSEKGMULTIPLIER defined in the top of the Calculator class
     * @see Calculator variables
     * @see ExActivityOne for usage
     * @param caloriesBurnedPerWeek
     * @return the users kilos lost with the current week plan per week.
     */
    public double getKilosLostPerWeek(double caloriesBurnedPerWeek){
        return caloriesBurnedPerWeek/KALORIESTOLOSEKGMULTIPLIER;
    }

    /**
     * This method calculates from the users input of ideal weight goal and the daily calories intake a time to lose all of the desires weight in weeks.
     * @param idealWeight
     * @param dailyCalories
     * @return the time to lose all extra weight in weeks.
     */
    public int getWeeksToLoseAllExtraWeight(int idealWeight,double dailyCalories){
        double caloriesToBurnPerWeek = getCaloriesToBurnPerWeek(dailyCalories);
        double totalCaloriesToBurn = getTotalCaloriesToBurn(idealWeight,weight);
        int weeks = (int)Math.round(totalCaloriesToBurn/caloriesToBurnPerWeek);
        return weeks;
    }

    /**
     * getTotalCaloriesToBurn() method takes the input of users target of desired weight and the current weight,
     * then calcuclates the total calories to burn from the kilos to lose * KALORIESTOLOSEKGMULTIPLIER equation.
     * @see Calculator getWeeksToLoseAllExtraWeight() for usage of the method
     * @param targetWeight
     * @param currentWeight
     * @return the double value of total calories to burn for target weight.
     */
    public double getTotalCaloriesToBurn(int targetWeight,double currentWeight){
        double kilosToLose = currentWeight-targetWeight;
        return kilosToLose*KALORIESTOLOSEKGMULTIPLIER;
    }

    /**
     * getWeeklyCaloriesToBurn() method calculates the amount of calories to burn per week to maintain weight.
     * The calculation is done from the amount of the users calories eaten multiplied by 7 days, and then substracted the users RMR which is multiplied by 7 days.
     * @see Calculator getRmr() method for the origin of the getRmr()
     * @see DaysActivity for usage of this method.
     * @param caloriesEaten
     * @return the amount of calories to burn this week.
     */
    public int getWeeklyCaloriesToBurn(int caloriesEaten){
        weeklyCaloriestoBurn = (7 * caloriesEaten) - (7 * getRmr());
        return  weeklyCaloriestoBurn;
    }

    /**
     * getCaloriesBurned() method takes the multiplier of the chosen sport activity and the value of time done in minutes,
     * then it calculates the calories burned during this exercise based on users weight and time done.
     * @see DayActivity for usage
     * @see SelectEx for usage
     * @see WeightLossWeekPlan for usage
     * @see WpExerciseActivity for usage
     * @param multiplier
     * @param time
     * @return the amount of calories burned during the exercise
     */
    public int getCaloriesBurned(Double multiplier, int time){
        Double timeDouble = Double.valueOf(time);
        caloriesBurned = multiplier * this.weight * (timeDouble/60);
        caloriesBurnedInt = (int) Math.round(caloriesBurned);
        return caloriesBurnedInt;
    }

    /**
     * getCalPerDay counts the amount of calories burned at the gym based on the activitys multiplier which is 5.0, users weight and time done in minutes.
     * @param time
     * @return calories burned during the exercise
     */
    public int getCalPerDay(int time) {
        Double timeDouble = Double.valueOf(time);
        caloriesBurnedAtGym = (int) Math.round(5.0 * this.weight * (timeDouble/60));
        calPerDay = caloriesBurnedAtGym;
        return calPerDay;
    }
}
