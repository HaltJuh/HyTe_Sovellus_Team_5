package com.example.hyte_projekti;

import androidx.annotation.NonNull;

/**
 * A class used to store the information of different exercises.
 * @author Tommi Vainio, Juho Halttunen
 * @version 1.0
 */
public class Exercise {
    private String name;
    private String info;
    private Double metMultiplier;

    /**
     * Constructor for the Exercise object.
     * @param name
     * @param info
     * @param multiplier
     */
    public Exercise(String name, String info, Double multiplier){
        this.name = name;
        this.info = info;
        this.metMultiplier = multiplier;
    }

    /**
     * Getter method for the object name.
     * @return Returns the name of the exercise.
     */
    public String getName(){
        return this.name;
    }

    /**
     * Getter method for the info of the object.
     * @return Returns the info of the exercise.
     */
    public String getInfo() {
        return this.info;
    }

    /**
     * Getter method for the Met Multiplier of the object.
     * @return Returns the metMultiplier of the exercise.
     */
    public Double getMetMultiplier() {
        return this.metMultiplier;
    }

    /**
     * toString method of the object
     * @return Returns the name of the exercise.
     */
    @Override
    public String toString() {
        return this.name;
    }
}
