package com.seinical.trips.data;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.PopupMenu;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.RecyclerView;
import androidx.transition.AutoTransition;
import androidx.transition.Transition;
import androidx.transition.TransitionManager;

import com.seinical.trips.R;

import java.util.List;

public class NotesRecyclerAdapter extends RecyclerView.Adapter<NotesRecyclerAdapter.NotesViewHolder> {
    Context context;
    List<Note> mNotes;
    LayoutInflater layoutInflater;

    public NotesRecyclerAdapter(Context context, List<Note> notes) {
        this.context = context;
        this.mNotes = notes;
        layoutInflater = LayoutInflater.from(context);
    }

    @NonNull
    @Override
    public NotesRecyclerAdapter.NotesViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View viewItem = layoutInflater.inflate(R.layout.note_item,parent,false);
        return new NotesRecyclerAdapter.NotesViewHolder(viewItem);
    }

    @Override
    public void onBindViewHolder(@NonNull NotesRecyclerAdapter.NotesViewHolder holder, int position) {
        Note note = mNotes.get(position);
        holder.details.setText(note.getDetails());


        holder.deleteIcon.setOnClickListener(view ->{
            Toast.makeText(context, "Note deleted Successfully", Toast.LENGTH_SHORT).show();
        });

    }

    @Override
    public int getItemCount() {
        return mNotes.size();
    }

    public class NotesViewHolder extends RecyclerView.ViewHolder {
        int visibility;
        CardView note;
        ImageButton deleteIcon;
        TextView details;
        androidx.constraintlayout.widget.ConstraintLayout data;

        public NotesViewHolder(@NonNull View itemView) {
            super(itemView);
            initComponents(itemView);
        }

        private void initComponents(View itemView) {
            note = itemView.findViewById(R.id.note_card);
            details = itemView.findViewById(R.id.note_item_note);
            deleteIcon = itemView.findViewById(R.id.note_item_delete);
        }
    }
}
