package com.seinical.trips;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageButton;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.seinical.trips.data.Note;
import com.seinical.trips.data.NotesRecyclerAdapter;
import com.seinical.trips.data.TempNotesDataManager;

public class MyNotes extends AppCompatActivity {

    private final FirebaseAuth mAuth = FirebaseAuth.getInstance();
    private DatabaseReference mDatabaseReference = FirebaseDatabase.getInstance().getReference(mAuth.getCurrentUser().getUid());
    private NotesRecyclerAdapter mNotesAdapter;
    private ImageButton mAddBtn;
    private EditText mNoteText;
    private String mTripName;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_my_notes);
        setupView();
        mAddBtn = findViewById(R.id.send_note);
        mNoteText = findViewById(R.id.note_text);
        Bundle extras = getIntent().getExtras();
        mTripName = "";
        if (extras != null)
             mTripName = extras.getString("trip_name");

        mDatabaseReference = mDatabaseReference.child("Trips").child(mTripName).child("Notes");

        gettingNotes();
        addNote();


    }

    private void addNote() {
        mAddBtn.setOnClickListener(view -> {
            mDatabaseReference.child(mNoteText.getText().toString().trim()).child("details").setValue(mNoteText.getText().toString().trim());
        });
    }

    private void gettingNotes() {
        mDatabaseReference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                if(snapshot != null)
                    TempNotesDataManager.getInstance().getNotes().clear();
                for (DataSnapshot dataSnapshot : snapshot.getChildren()) {

                    Note note = new Note(dataSnapshot.child("details").getValue(String.class),mTripName);
                    TempNotesDataManager.getInstance().addNote(note);
                }
                mNotesAdapter.notifyDataSetChanged();
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
    }

    private void setupView() {
        RecyclerView recyclerView = findViewById(R.id.notes_view);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        mNotesAdapter = new NotesRecyclerAdapter(MyNotes.this, TempNotesDataManager.getInstance().getNotes());
        recyclerView.setAdapter(mNotesAdapter);
    }
}