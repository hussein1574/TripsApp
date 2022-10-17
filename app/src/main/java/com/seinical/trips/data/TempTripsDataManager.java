package com.seinical.trips.data;

import java.util.ArrayList;
import java.util.List;

public class TempTripsDataManager {
    private static TempTripsDataManager instance = null;
    private final List<Trip> upcoming = new ArrayList<>();
    private final List<Trip> history = new ArrayList<>();

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
        upcoming.add(new Trip("Trip1","17/10/2022","17:00","Upcoming","Cairo","Cairo"));
        upcoming.add(new Trip("Trip1","17/10/2022","17:00","Upcoming","Cairo","Cairo"));
        upcoming.add(new Trip("Trip1","17/10/2022","17:00","Upcoming","Cairo","Cairo"));
        upcoming.add(new Trip("Trip1","17/10/2022","17:00","Upcoming","Cairo","Cairo"));
        upcoming.add(new Trip("Trip1","17/10/2022","17:00","Upcoming","Cairo","Cairo"));
        upcoming.add(new Trip("Trip1","17/10/2022","17:00","Upcoming","Cairo","Cairo"));
        upcoming.add(new Trip("Trip1","17/10/2022","17:00","Upcoming","Cairo","Cairo"));
        upcoming.add(new Trip("Trip1","17/10/2022","17:00","Upcoming","Cairo","Cairo"));
        upcoming.add(new Trip("Trip1","17/10/2022","17:00","Upcoming","Cairo","Cairo"));
        upcoming.add(new Trip("Trip1","17/10/2022","17:00","Upcoming","Cairo","Cairo"));
        history.add(new Trip("Trip1","17/10/2022","17:00","Completed","Cairo","Cairo"));
        history.add(new Trip("Trip1","17/10/2022","17:00","Completed","Cairo","Cairo"));
        history.add(new Trip("Trip1","17/10/2022","17:00","Completed","Cairo","Cairo"));
        history.add(new Trip("Trip1","17/10/2022","17:00","Completed","Cairo","Cairo"));
        history.add(new Trip("Trip1","17/10/2022","17:00","Completed","Cairo","Cairo"));
        history.add(new Trip("Trip1","17/10/2022","17:00","Completed","Cairo","Cairo"));
        history.add(new Trip("Trip1","17/10/2022","17:00","Completed","Cairo","Cairo"));
        history.add(new Trip("Trip1","17/10/2022","17:00","Cancelled","Cairo","Cairo"));
        history.add(new Trip("Trip1","17/10/2022","17:00","Cancelled","Cairo","Cairo"));
        history.add(new Trip("Trip1","17/10/2022","17:00","Cancelled","Cairo","Cairo"));
        history.add(new Trip("Trip1","17/10/2022","17:00","Cancelled","Cairo","Cairo"));
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
