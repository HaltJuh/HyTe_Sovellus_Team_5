package com.example.hyte_projekti;

import java.util.ArrayList;
import java.util.List;

public class weightLossList {

    private List<Exercise> exercises;
    private static final weightLossList ourInstance = new weightLossList();

    private weightLossList(){
        exercises = new ArrayList<>();
        exercises.add(new Exercise("Walking","Walking at your at your own pace",3.5));
        exercises.add(new Exercise("Jogging","Walking at a moderately faster pace than your normal walking pace",7.0));
        exercises.add(new Exercise("Running","Running at your own running pace",9.8));
        exercises.add(new Exercise("Cycling","Cycling at your own pace",7.5));
        exercises.add(new Exercise("Swimming","Swimming at your own pace.",7.0));
        exercises.add(new Exercise("Power Yoga","",4.0));
        exercises.add(new Exercise("Hatha Yoga","",2.5));
        exercises.add(new Exercise("Rope Jumping","Rope Jumping with around 100 skips per minute.",11.8));
        exercises.add(new Exercise("Pilates","",3.0));
    }
    public List<Exercise> getWeightLossList(){
        return exercises;
    }
    public Exercise getWeightLossExercise(int index){
        return exercises.get(index);
    }
}
