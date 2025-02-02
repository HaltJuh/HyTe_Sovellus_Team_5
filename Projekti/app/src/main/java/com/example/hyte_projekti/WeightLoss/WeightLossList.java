package com.example.hyte_projekti.WeightLoss;

import com.example.hyte_projekti.General.Exercise;

import java.util.ArrayList;
import java.util.List;

/**
 * A singleton class meant to store data of weight loss exercises
 * @author Juho Halttunen
 * @version 1.0
 */
public class WeightLossList {

    private List<Exercise> exercises;
    private static final WeightLossList ourInstance = new WeightLossList();

    /**
     * Constructs a list of weight loss exercises
     * @see {@link Exercise}
     */
    private WeightLossList(){
        exercises = new ArrayList<>();
        exercises.add(new Exercise("Walking","Walking at your at your own pace",3.5));
        exercises.add(new Exercise("Jogging","Walking at a moderately faster pace than your normal walking pace",7.0));
        exercises.add(new Exercise("Running","Running at your own running pace",9.8));
        exercises.add(new Exercise("Cycling","Cycling at your own pace",7.5));
        exercises.add(new Exercise("Swimming","Swimming at your own pace.",7.0));
        exercises.add(new Exercise("Power Yoga","Fitness based apprtoach to vinyasa-style yoga",4.0));
        exercises.add(new Exercise("Hatha Yoga","General style yoga",2.5));
        exercises.add(new Exercise("Rope Jumping","Rope Jumping with around 100 skips per minute.",11.8));
        exercises.add(new Exercise("Pilates","",3.0));
    }

    /**
     * Getter method for an instance of the Singleton
     * @return Instance of WeightLossList
     */
    public static WeightLossList getInstance(){
        return ourInstance;
    }

    /**
     * Getter method for List<Exercise>
     * @return List<Exercise>
     */
    public List<Exercise> getWeightLossList(){
        return exercises;
    }

    /**
     * Getter method for an Exercise at the position of index
     * @param index
     * @return Exercise
     */
    public Exercise getWeightLossExercise(int index){
        return exercises.get(index);
    }
}
