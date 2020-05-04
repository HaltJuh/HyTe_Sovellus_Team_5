package com.example.hyte_projekti;

import androidx.annotation.NonNull;

public class Days {
    private String name;
    private String exercise;
    private String saveKey;
    private int exerciseTime;
    private int index;

    public Days(String name, int index, String saveKey){
        this.name = name;
        this.index = index;
        this.saveKey = saveKey;
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

    public String getSaveKey() {return this.saveKey;}

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
