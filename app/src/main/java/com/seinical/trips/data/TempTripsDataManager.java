package com.seinical.trips.data;

import android.os.Build;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

public class TempTripsDataManager {
    private final DatabaseReference mDatabase= FirebaseDatabase.getInstance().getReference();
    private final FirebaseUser mUser = FirebaseAuth.getInstance().getCurrentUser();
    private static volatile TempTripsDataManager instance = null;
    private final List<Trip> upcoming = new ArrayList<>();
    private final List<Trip> history = new ArrayList<>();

    private TempTripsDataManager(){}

    public static TempTripsDataManager getInstance() {
        if (instance == null) {
            synchronized (TempTripsDataManager.class) {
                if (instance == null) {
                    instance = new TempTripsDataManager();
                }
            }
        }
        return instance;
    }

    public List<Trip> getUpcoming() {
        return upcoming;
    }

    public List<Trip> getHistory() {
        return history;
    }

    public void addTrip(Trip trip) {
        if("Upcoming".equals(trip.getStatus()))
            upcoming.add(trip);
        else
            history.add(trip);
    }
}
