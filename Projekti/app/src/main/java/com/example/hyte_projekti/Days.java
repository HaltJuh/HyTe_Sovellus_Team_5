package com.example.hyte_projekti;

import androidx.annotation.NonNull;

public class Days {
    private String name;
    private String exercise;
    private int exerciseTime;
    private int index;

    public Days(String name, int index){
        this.name = name;
        this.index = index;
    }

    public String getName() {
        return this.name;
    }

    public String getExercise() {
        return this.exercise;
    }

    public int getExerciseTime() {
        return this.exerciseTime;
    }

    public int getIndex() {
        return this.index;
    }

    @Override
    public String toString() {
        return this.name;
    }

    public void setExercise(String exercise) {
        this.exercise = exercise;
    }

    public void setExerciseTime(int exerciseTime) {
        this.exerciseTime = exerciseTime;
    }
}
