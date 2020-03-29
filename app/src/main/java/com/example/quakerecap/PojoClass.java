package com.example.quakerecap;

public class PojoClass {
    private double magnitude;
    private String place;
    private long date_millisec;
    private String url;


    public PojoClass(double magnitude, String place, long date_millisec, String url) {
        this.magnitude = magnitude;
        this.place = place;
        this.date_millisec = date_millisec;
        this.url=url;
    }

    public double getMagnitude() {
        return magnitude;
    }

    public String getPlace() {
        return place;
    }


    public long getDate_millisec() {
        return date_millisec;
    }

    public String getUrl() {
        return url;
    }
}
