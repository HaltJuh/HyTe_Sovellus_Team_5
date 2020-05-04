package com.example.hyte_projekti;

import java.util.ArrayList;
import java.util.List;

public class DaysList {
    private List<Days> days;
    private static final DaysList ourInstance = new DaysList();

    public static DaysList getInstance(){
        return ourInstance;
    }

    private DaysList(){
        days = new ArrayList<>();
        days.add(new Days("Monday", 2,"MondayTime"));
        days.add(new Days("Tuesday", 3,"TuesdayTime"));
        days.add(new Days("Wednesday", 4,"WednesdayTime"));
        days.add(new Days("Thursday", 5,"ThursdayTime"));
        days.add(new Days("Friday", 6,"FridayTime"));
        days.add(new Days("Saturday", 7,"SaturdayTime"));
        days.add(new Days("Sunday", 1,"SundayTime"));
    }

    public List<Days> getDays(){
        return days;
    }

    public Days getDay(int i){
        return days.get(i);
    }

}
