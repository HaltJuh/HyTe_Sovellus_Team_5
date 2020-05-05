package com.example.hyte_projekti;

import java.util.ArrayList;
import java.util.List;

/**
 * @author Tommi Vainio
 * @version 1.0
 */
public class ExercisesListTwo {
    private List<Exercise> exercises;
    private static final ExercisesListTwo ourInstance = new ExercisesListTwo();

    /**
     * Returns the only instance you can create of this class.
     * @return ourInstance instance of this class.
     */
    public static ExercisesListTwo getInstance(){
        return ourInstance;
    }

    /**
     * Creates new ArrayList and adds nine instances of Exercise class into it.
     * These instances are used in Maintain fitness program.
     */
    private ExercisesListTwo(){
        exercises = new ArrayList<>();
        exercises.add(new Exercise("Walking", "Walk, about 5 km/h, level, moderate pace, firm surface.", 3.5));
        exercises.add(new Exercise("Ice hockey", "Play some ice hockey.", 8.0));
        exercises.add(new Exercise("Bowling", "Go bowling.", 3.0));
        exercises.add(new Exercise("Bicycling", "Cycle at a reasonable speed.", 7.5));
        exercises.add(new Exercise("Swimming", "Swim laps, freestyle, front crawl, slow, light or moderate effort.", 5.8));
        exercises.add(new Exercise("Rollerblading", "Rollerblade about 15-20 km/h, moderate pace, exercise training.", 9.8));
        exercises.add(new Exercise("Football", "Play some football.", 7.0));
        exercises.add(new Exercise("Running", "Run about 10-12 km/h.", 11.0));
        exercises.add(new Exercise("Jog/walk combination", "Jog and walk (jogging component of less than 10 minutes).", 6.0));
    }

    /**
     * Returns List created in constructor.
     * @return exercises list which includes instances of Exercise class.
     */
    public List<Exercise> getExercises(){
        return exercises;
    }

    /**
     * Returns single instance of exercise class.
     * @param i index of instance in exercises list.
     * @return single instance of Exercise class from exercises list.
     */
    public Exercise getExercise(int i){
        return exercises.get(i);
    }
}
