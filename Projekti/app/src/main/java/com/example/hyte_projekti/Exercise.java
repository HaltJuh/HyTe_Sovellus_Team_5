package com.example.hyte_projekti;

import androidx.annotation.NonNull;

public class Exercise {
    private String name;
    private String info;
    private Double metMultiplier;

    public Exercise(String name, String info, Double multiplier){
        this.name = name;
        this.info = info;
        this.metMultiplier = multiplier;
    }

    public String getName(){
        return this.name;
    }

    public String getInfo() {
        return this.info;
    }

    public Double getMetMultiplier() {
        return this.metMultiplier;
    }

    @Override
    public String toString() {
        return this.name;
    }
}
