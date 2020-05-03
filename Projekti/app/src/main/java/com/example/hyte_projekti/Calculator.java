package com.example.hyte_projekti;

public class Calculator {
    private int age;
    private Double height;
    private Double weight;
    private String gender;
    private Double rmr;
    private int rmrInt;
    private int calBurned;
    private int calPerDay;

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
       rmrInt = (int) Math.round(rmr);
       return rmrInt;
    }

    public int getCaloriesBurned(double timeInHours, double metValue) {
        calBurned = (int) Math.round( this.weight * metValue * timeInHours);

        return calBurned;
    }

    /*
    public int getCaloriesPerDay() {
        calPerDay = getRmr() + getCaloriesBurned();

        return calPerDay;
    }
     */
}
