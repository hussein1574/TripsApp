package com.seinical.trips.data;

import java.util.ArrayList;
import java.util.List;

public class TempNotesDataManager {
    private static volatile TempNotesDataManager instance = null;
    private final List<Note> notes = new ArrayList<>();


    private TempNotesDataManager(){}

    public static TempNotesDataManager getInstance() {
        if (instance == null) {
            synchronized (TempNotesDataManager.class) {
                if (instance == null) {
                    instance = new TempNotesDataManager();
                }
            }
        }
        return instance;
    }



    public List<Note> getNotes() {
        return notes;
    }


    public void addNote(Note note) {
        notes.add(note);
    }
}
