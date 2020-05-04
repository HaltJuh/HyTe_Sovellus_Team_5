package com.example.hyte_projekti;

import java.util.ArrayList;
import java.util.List;

public class ExercisesListTwo {
    private List<Exercise> exercises;
    private static final ExercisesListTwo ourInstance = new ExercisesListTwo();

    public static ExercisesListTwo getInstance(){
        return ourInstance;
    }

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

    public List<Exercise> getExercises(){
        return exercises;
    }

    public Exercise getExercise(int i){
        return exercises.get(i);
    }
}
