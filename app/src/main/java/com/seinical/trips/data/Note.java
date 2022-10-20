package com.seinical.trips.data;

public class Note {
    String details;
    String tripName;

    public Note(String details, String tripName) {
        this.details = details;
        this.tripName = tripName;
    }

    public String getDetails() {
        return details;
    }
    public String getTripName() {
        return tripName;
    }
}
