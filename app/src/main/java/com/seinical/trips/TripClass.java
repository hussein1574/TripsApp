package com.seinical.trips;

public class TripClass {
    String origin;
    String destination;
    String title;
    String day;
    String time;
    String trip_type;
    String roundDay;
    String roundTime;

    public TripClass(){

    }

    public TripClass(String origin, String destination, String title, String day, String time) {
        this.origin = origin;
        this.destination = destination;
        this.title = title;
        this.day = day;
        this.time = time;
    }

    public TripClass(String origin, String destination, String title, String day, String time, String trip_type) {
        this.origin = origin;
        this.destination = destination;
        this.title = title;
        this.day = day;
        this.time = time;
        this.trip_type = trip_type;
    }

    public TripClass(String origin, String destination, String title, String day, String time, String trip_type, String roundDay, String roundTime) {
        this.origin = origin;
        this.destination = destination;
        this.title = title;
        this.day = day;
        this.time = time;
        this.trip_type = trip_type;
        this.roundDay = roundDay;
        this.roundTime = roundTime;
    }

    public void setOrigin(String origin) {
        this.origin = origin;
    }

    public void setDestination(String destination) {
        this.destination = destination;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public void setDay(String day) {
        this.day = day;
    }

    public void setTime(String time) {
        this.time = time;
    }

    public void setTrip_type(String trip_type) {
        this.trip_type = trip_type;
    }

    public String getOrigin() {
        return origin;
    }

    public String getDestination() {
        return destination;
    }

    public String getTitle() {
        return title;
    }

    public String getDay() {
        return day;
    }

    public String getTime() {
        return time;
    }

    public String getTrip_type() {
        return trip_type;
    }
}
