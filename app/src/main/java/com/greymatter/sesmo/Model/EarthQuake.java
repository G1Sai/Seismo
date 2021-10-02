package com.greymatter.sesmo.Model;

public class EarthQuake {
    private String rating,time,radius,location,type;

    public EarthQuake() {
    }

    public EarthQuake(String rating, String time, String radius, String location, String type) {
        this.rating = rating;
        this.time = time;
        this.radius = radius;
        this.location = location;
        this.type = type;
    }

    public String getRating() {
        return rating;
    }

    public void setRating(String rating) {
        this.rating = rating;
    }

    public String getTime() {
        return time;
    }

    public void setTime(String time) {
        this.time = time;
    }

    public String getRadius() {
        return radius;
    }

    public void setRadius(String radius) {
        this.radius = radius;
    }

    public String getLocation() {
        return location;
    }

    public void setLocation(String location) {
        this.location = location;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }
}
