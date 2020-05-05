package com.example.hyte_projekti;

import androidx.annotation.NonNull;

/**
 * @author Tommi Vainio, Juho Halttunen
 * @version 1.0
 * A class used to store the information of different exercises.
 */
public class Exercise {
    private String name;
    private String info;
    private Double metMultiplier;

    /**
     * @param name
     * @param info
     * @param multiplier
     * Constructor for the Exercise object
     */
    public Exercise(String name, String info, Double multiplier){
        this.name = name;
        this.info = info;
        this.metMultiplier = multiplier;
    }

    /**
     * @return Returns the name of the exercise.
     * Getter method for the object name.
     */
    public String getName(){
        return this.name;
    }

    /**
     * @return Returns the info of the exercise.
     * Getter method for the info of the object.
     */
    public String getInfo() {
        return this.info;
    }

    /**
     * @return Returns the metMultiplier of the exercise.
     * Getter method for the Met Multiplier of the object.
     */
    public Double getMetMultiplier() {
        return this.metMultiplier;
    }

    /**
     * @return Returns the name of the exercise.
     * toString method of the object
     */
    @Override
    public String toString() {
        return this.name;
    }
}
