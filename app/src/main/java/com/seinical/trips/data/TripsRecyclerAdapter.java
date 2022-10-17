package com.seinical.trips.data;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.MenuItem;
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

public class TripsRecyclerAdapter extends RecyclerView.Adapter<TripsRecyclerAdapter.TripsViewHolder>{

    Context context;
    List<Trip> trips;
    LayoutInflater layoutInflater;

    public TripsRecyclerAdapter(Context context, List<Trip> trips) {
        this.context = context;
        this.trips = trips;
        layoutInflater = LayoutInflater.from(context);
    }

    @NonNull
    @Override
    public TripsViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View viewItem = layoutInflater.inflate(R.layout.trip_item,parent,false);
        return new TripsViewHolder(viewItem);
    }

    @Override
    public void onBindViewHolder(@NonNull TripsViewHolder holder, int position) {
        Trip trip = trips.get(position);
        holder.name.setText(trip.getName());
        holder.date.setText(trip.getDate());
        holder.time.setText(trip.getTime());
        holder.status.setText(trip.getStatus());
        holder.source.setText(trip.getSource());
        holder.destination.setText(trip.getDestination());

        holder.menuIcon.setOnClickListener(view ->{
            PopupMenu tripMenu = new PopupMenu(context,holder.menuIcon);
            tripMenu.inflate(R.menu.trip_item_menu);
            tripMenu.show();
            tripMenu.setOnMenuItemClickListener(item -> {
                int id = item.getItemId();
                if (id == R.id.trip_menu_note) {
                    Toast.makeText(context, "Add Note", Toast.LENGTH_SHORT).show();
                } else if (id == R.id.trip_menu_cancel) {
                    Toast.makeText(context, "Trip Cancelled Successfully", Toast.LENGTH_SHORT).show();
                }
                return false;
            });
        });

    }

    @Override
    public int getItemCount() {
        return trips.size();
    }

    public class TripsViewHolder extends RecyclerView.ViewHolder {
        int visibility;
        TextView name;
        TextView date;
        TextView time;
        TextView status;
        TextView source;
        TextView destination;
        Button start;
        CardView trip;
        ImageButton menuIcon;
        androidx.constraintlayout.widget.ConstraintLayout data;

        public TripsViewHolder(@NonNull View itemView) {
            super(itemView);
            initComponents(itemView);

            itemView.setOnClickListener(view ->{
                visibility = data.getVisibility();
                Transition transition = new AutoTransition();
                if (visibility == View.VISIBLE) {
                    transition.setDuration(100);
                    TransitionManager.beginDelayedTransition(trip,transition);
                    visibility = View.GONE;
                } else if (visibility == View.GONE) {
                    transition.setDuration(300);
                    TransitionManager.beginDelayedTransition(trip,transition);
                    visibility = View.VISIBLE;
                }
                data.setVisibility(visibility);
            });
        }

        private void initComponents(View itemView) {
            name = itemView.findViewById(R.id.trip_name_tv);
            date = itemView.findViewById(R.id.date_tv);
            time = itemView.findViewById(R.id.time_tv);
            status = itemView.findViewById(R.id.status_tv);
            source = itemView.findViewById(R.id.source_tv);
            destination = itemView.findViewById(R.id.dest_tv);
            data = itemView.findViewById(R.id.data_layout);
            start = itemView.findViewById(R.id.start_btn);
            trip = itemView.findViewById(R.id.trip_card);
            menuIcon = itemView.findViewById(R.id.trip_menu_btn);
        }
    }
}
