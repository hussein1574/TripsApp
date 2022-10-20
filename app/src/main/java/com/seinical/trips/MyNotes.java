package com.seinical.trips;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;

import com.seinical.trips.data.Note;
import com.seinical.trips.data.NotesRecyclerAdapter;
import com.seinical.trips.data.TempNotesDataManager;

import java.util.List;

public class MyNotes extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_my_notes);

        RecyclerView recyclerView = findViewById(R.id.notes_view);
        List<Note> notesData = TempNotesDataManager.getInstance().getNotes();
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        NotesRecyclerAdapter notesAdapter = new NotesRecyclerAdapter(MyNotes.this, notesData);
        recyclerView.setAdapter(notesAdapter);

    }
}