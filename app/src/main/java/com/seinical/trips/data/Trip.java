package com.seinical.trips.data;




import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

public class Trip {
    private  String name;
    private  String date;
    private  String time;
    private  String status;
    private  String source;
    private  String destination;

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


    public void setName(String name) {
        this.name = name;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public void setTime(String time) {
        this.time = time;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public void setSource(String source) {
        this.source = source;
    }

    public void setDestination(String destination) {
        this.destination = destination;
    }
}
