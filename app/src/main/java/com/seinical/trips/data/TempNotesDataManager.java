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
                    instance.initializeNotes();
                }
            }
        }
        return instance;
    }
private  void initializeNotes() {
        notes.add(new Note("Test 1"));
    notes.add(new Note("Test 2"));
    notes.add(new Note("Test 3"));
    notes.add(new Note("Test 4"));
    notes.add(new Note("Test 5"));
    notes.add(new Note("Test 6"));
}


    public List<Note> getNotes() {
        return notes;
    }


    public void addNote(Note note) {
        notes.add(note);
    }
}
