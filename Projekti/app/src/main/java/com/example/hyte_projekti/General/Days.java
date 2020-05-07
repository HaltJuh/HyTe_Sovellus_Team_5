package com.example.hyte_projekti.General;

import androidx.annotation.NonNull;

/**
 * A class used to store weekdays and data related to them.
 * @author Tommi Vainio, Juho Halttunen
 * @version 1.0
 */
public class Days {
    private String name;
    private String saveKey;
    private String doneKey;
    private int index;

    /**
     * Constructor for the day object.
     * @param name
     * @param index
     * @param saveKey
     * @param doneKey
     */
    public Days(String name, int index, String saveKey, String doneKey){
        this.name = name;
        this.index = index;
        this.saveKey = saveKey;
        this.doneKey = doneKey;
    }

    /**
     * Getter method for the object name.
     * @return Returns the name of the object.
     */
    public String getName() {return this.name; }

    /**
     * Getter method for the objects index.
     * @return Returns the given index of the object.
     */
    public int getIndex() {return this.index; }

    /**
     * Getter method for the objects savekey.
     * @return Returns the objects savekey.
     */
    public String getSaveKey() {return this.saveKey; }

    /**
     * Getter method for the objects donekey.
     * @return Returns the objects donekey.
     */
    public String getDoneKey() {return  this.doneKey; }

    /**
     * To string method.
     * @return Returns the name of the object.
     */
    @Override
    public String toString() {return this.name; }
}
