package com.example.hyte_projekti;

import java.util.ArrayList;
import java.util.List;

/**
 * @author Tommi Vainio, Juho Halttunen
 * @version 1.0
 * Singleton class used to store a list of Day objects.
 */
public class DaysList {
    private List<Days> days;
    private static final DaysList ourInstance = new DaysList();

    /**
     * @return Returns Instance of DaysList
     * Getter method for an instance of the singleton.
     */
    public static DaysList getInstance(){
        return ourInstance;
    }
    /**
     * @see {@link Days}
     *  Constructor for the Singleton.
     */
    private DaysList(){
        days = new ArrayList<>();
        days.add(new Days("Monday", 2,"MondayTime","MondayDone"));
        days.add(new Days("Tuesday", 3,"TuesdayTime","TuesdayDone"));
        days.add(new Days("Wednesday", 4,"WednesdayTime","WednesdayDone"));
        days.add(new Days("Thursday", 5,"ThursdayTime","ThursdayDone"));
        days.add(new Days("Friday", 6,"FridayTime","FridayDone"));
        days.add(new Days("Saturday", 7,"SaturdayTime","SaturdayDone"));
        days.add(new Days("Sunday", 1,"SundayTime","SundayDone"));
    }
    /**
     * @return Returns a list of Days objects.
     * Getter method for List<Days>
     */
    public List<Days> getDays(){
        return days;
    }
    /**
     *
     * @param i
     * @return Returns the Days object at position i
     */
    public Days getDay(int i){
        return days.get(i);
    }

}
