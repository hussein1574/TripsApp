package com.seinical.trips.data;

public class Trip {
    private final String name;
    private final String date;
    private final String time;
    private final String status;
    private final String source;
    private final String destination;

    public Trip(String name, String date, String time, String status, String source, String destination) {
        this.name = name;
        this.date = date;
        this.time = time;
        this.status = status;
        this.source = source;
        this.destination = destination;
    }

    public String getName() {
        return name;
    }



    public String getDate() {
        return date;
    }



    public String getTime() {
        return time;
    }



    public String getStatus() {
        return status;
    }



    public String getSource() {
        return source;
    }



    public String getDestination() {
        return destination;
    }

   }
