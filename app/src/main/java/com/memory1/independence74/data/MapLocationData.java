package com.memory1.independence74.data;

public class MapLocationData {
    double lat;
    double lon;
    String name;
    String location;

    public MapLocationData(double lat, double lon, String name, String location) {
        this.lat = lat;
        this.lon = lon;
        this.name = name;
        this.location = location;
    }

    public double getLat() {
        return lat;
    }

    public void setLat(double lat) {
        this.lat = lat;
    }

    public double getLon() {
        return lon;
    }

    public void setLon(double lon) {
        this.lon = lon;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getLocation() {
        return location;
    }

    public void setLocation(String location) {
        this.location = location;
    }
}
