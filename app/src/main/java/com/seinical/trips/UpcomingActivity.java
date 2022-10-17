package com.seinical.trips;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;

import com.seinical.trips.data.TempTripsDataManager;
import com.seinical.trips.data.Trip;
import com.seinical.trips.data.TripsRecyclerAdapter;

import java.util.List;

public class UpcomingActivity extends AppCompatActivity {

    private List<Trip> mUpcomingTrips;
    private List<Trip> mHistroyTrips;
    RecyclerView mTripsRecyclerView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_upcoming);

        initComponents();
        LinearLayoutManager tripsLayoutManager = new LinearLayoutManager(this);
        mTripsRecyclerView.setLayoutManager(tripsLayoutManager);
        TripsRecyclerAdapter tripsRecyclerAdapter = new TripsRecyclerAdapter(this, mUpcomingTrips);
        mTripsRecyclerView.setAdapter(tripsRecyclerAdapter);
    }

    private void initComponents() {
        mUpcomingTrips = TempTripsDataManager.getInstance().getUpcoming();
        mHistroyTrips = TempTripsDataManager.getInstance().getHistory();
        mTripsRecyclerView = findViewById(R.id.upcoming_recyclerview);
    }
}