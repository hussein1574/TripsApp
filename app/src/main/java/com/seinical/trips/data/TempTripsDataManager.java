package com.seinical.trips.data;

import java.util.ArrayList;
import java.util.List;

public class TempTripsDataManager {
    private static TempTripsDataManager instance = null;
    private final List<Trip> trips = new ArrayList<>();

    private TempTripsDataManager(){}

    public static TempTripsDataManager getInstance() {
        if (instance == null) {
            synchronized (TempTripsDataManager.class) {
                if (instance == null) {
                    instance = new TempTripsDataManager();
                    instance.initializeTrips();
                }
            }
        }
        return instance;
    }

    private void initializeTrips() {
        trips.add(new Trip("Trip1","17/10/2022","17:00","Upcoming","Cairo","Cairo"));
        trips.add(new Trip("Trip1","17/10/2022","17:00","Upcoming","Cairo","Cairo"));
        trips.add(new Trip("Trip1","17/10/2022","17:00","Upcoming","Cairo","Cairo"));
        trips.add(new Trip("Trip1","17/10/2022","17:00","Upcoming","Cairo","Cairo"));
        trips.add(new Trip("Trip1","17/10/2022","17:00","Upcoming","Cairo","Cairo"));
        trips.add(new Trip("Trip1","17/10/2022","17:00","Upcoming","Cairo","Cairo"));
        trips.add(new Trip("Trip1","17/10/2022","17:00","Upcoming","Cairo","Cairo"));
        trips.add(new Trip("Trip1","17/10/2022","17:00","Upcoming","Cairo","Cairo"));
        trips.add(new Trip("Trip1","17/10/2022","17:00","Upcoming","Cairo","Cairo"));
        trips.add(new Trip("Trip1","17/10/2022","17:00","Upcoming","Cairo","Cairo"));
        trips.add(new Trip("Trip1","17/10/2022","17:00","Upcoming","Cairo","Cairo"));
    }

    public List<Trip> getTrips() {
        return trips;
    }
}
