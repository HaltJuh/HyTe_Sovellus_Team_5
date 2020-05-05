package com.example.hyte_projekti;

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
     * @param name
     * @param index
     * @param saveKey
     * @param doneKey
     * Constructor for the day object.
     */
    public Days(String name, int index, String saveKey, String doneKey){
        this.name = name;
        this.index = index;
        this.saveKey = saveKey;
        this.doneKey = doneKey;
    }

    /**
     * @return Returns the name of the object.
     * Getter method for the object name.
     */
    public String getName() {return this.name; }

    /**
     * @return Returns the given index of the object.
     * Getter method for the objects index.
     */
    public int getIndex() {return this.index; }

    /**
     * @return Returns the objects savekey.
     * Getter method for the objects savekey.
     */
    public String getSaveKey() {return this.saveKey; }

    /**
     * @return Returns the objects donekey.
     * Getter method for the objects donekey.
     */
    public String getDoneKey() {return  this.doneKey; }

    /**
     * @return Returns the name of the object.
     * To string method.
     */
    @Override
    public String toString() {return this.name; }
}
