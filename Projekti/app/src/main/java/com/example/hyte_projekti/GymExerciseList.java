package com.example.hyte_projekti;

import java.util.List;
import java.util.ArrayList;

public class GymExerciseList {

    private List<Exercise> gymExercises;

    private static final GymExerciseList thisInstance = new GymExerciseList();

    private GymExerciseList() {
        gymExercises = new ArrayList<Exercise>();
        gymExercises.add(new Exercise("Gym: Chest & Triceps", "- Warmup for 5-10 minutes!\n" +
                "- Flat bench press\n" +
                "- Barbell bench press\n" +
                "- Triceps pushdowns\n" +
                "- Barbell kickbacks\n" +
                "- Barbell flys\n" +
                "- Side-arm dumbbell rows\n" +
                "- Cable flys AND/OR Dips", 5.0 ));
        gymExercises.add(new Exercise("Gym: Back & Biceps", "- Warmup for 5-10 minutes!\n" +
                "- Machine rows\n" +
                "- Pulldowns (Wide AND/OR Close grip)\n" +
                "- Dumbbell curls\n" +
                "- Hammer curls\n" +
                "- Lower back machine OR Deadlifts\n" +
                "- Cable rows\n" +
                "- Preacher curls\n" +
                "- Hyper extensions", 5.0 ));
        gymExercises.add(new Exercise("Gym: Legs & Abdominal", "- Warmup for 5-10 minutes!\n" +
                "- Walking lunges\n" +
                "- Seated leg press\n" +
                "- Leg extensions\n" +
                "- Leg curls\n" +
                "- Claf raises (seated OR standing)\n" +
                "- Abdominal Machine\n" +
                "- Decline crunches\n" +
                "- Dumbbell side bends\n" +
                "- Sitting twists (weighted)", 5.0 ));
        gymExercises.add(new Exercise("Gym: Chest", "- Warmup for 5 minutes!\n" +
                "- Machine bench press (Wide Or Close grip)\n" +
                "- Flat bench press\n" +
                "- Incline bench press\n" +
                "- Dumbbell flyes\n" +
                "- Cable flys\n" +
                "- Dips\n", 5.0 ));
        gymExercises.add(new Exercise("Gym: Back", "- Warmup for 5 minutes!\n" +
                "- Machine row\n" +
                "- Pulldowns (Wide OR Close grip)\n" +
                "- Cable rows\n" +
                "- Deadlifts OR machine with lower back training\n" +
                "- Hyper extensions\n" +
                "- Pull ups", 5.0 ));
        gymExercises.add(new Exercise("Gym: Legs", "- Warmup for 5 minutes!\n" +
                "- Walking lunges\n" +
                "- Seated leg press\n" +
                "- Leg extensions\n" +
                "- Leg curls\n" +
                "- Claf raises (seated OR standing)\n" +
                "- Barbell Squats\n", 5.0 ));
        gymExercises.add(new Exercise("Gym: Abdominal", "- Warmup for 5 minutes!\n" +
                "- Crunches OR Decline crunches\n" +
                "- Dumbbell side bends\n" +
                "- Hanging leg raises\n" +
                "- Abdominal machine\n" +
                "- Sitting twists (with a proper weight)", 5.0 ));
        gymExercises.add(new Exercise("Gym: Triceps", "-  Warmup for 5 minutes!\n" +
                "- Side-arm dumbbell rows\n" +
                "- Rope push-downs\n" +
                "- Triceps pushowns\n" +
                "- Dips\n" +
                "- Seated barbell/dumbbell extensions\n" +
                "- Dumbbell kickbacks", 5.0 ));
        gymExercises.add(new Exercise("Gym: Biceps", "- Warmup for 5 minutes!\n" +
                "- Dumbbell curls\n" +
                "- Curl machine OR Barbell preacher-bench curls\n" +
                "- Hammer curls\n" +
                "- Cable curls\n" +
                "- Concentration curls", 5.0 ));
        gymExercises.add(new Exercise("Gym: Shoulders", "- Warmup for 5 minutes!\n" +
                "- Dumbbell lateral raises\n" +
                "- Shoulder press\n" +
                "- Arnold press\n" +
                "- Reverse flys\n" +
                "- Machine shoulder press\n" +
                "- Frontal raises (with disc or dumbbells)\n", 5.0 ));
    }

    public static GymExerciseList getThisInstance() {
        return thisInstance;
    }

    public List<Exercise> getGymExercises() {
        return gymExercises;
    }
}
