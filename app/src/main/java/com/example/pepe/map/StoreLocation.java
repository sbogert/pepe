package com.example.pepe.map;

/* class for different store locations to be shown on map */
public class StoreLocation {
    private String name;
    private double latitude;
    private double longitude;

    public StoreLocation(String name_, double latitude_, double longitude_) {
        this.name = name_;
        this.latitude = latitude_;
        this.longitude = longitude_;
    }


    // getters and setters for each variable
    public String getName() {
        return name;
    }
    public void setName(String name) {
        this.name = name;
    }

    public double getLatitude() {
        return latitude;
    }
    public void setLatitude(double latitude) {
        this.latitude = latitude;
    }

    public double getLongitude() {
        return longitude;
    }
    public void setLongitude(double longitude) {
        this.longitude = longitude;
    }
}
