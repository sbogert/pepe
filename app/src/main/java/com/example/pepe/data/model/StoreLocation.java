package com.example.pepe.data.model;

/* class for different store locations to be shown on map */
public class StoreLocation {
    private Integer id;
    private String username;
    private double latitude;
    private double longitude;

    public StoreLocation(Integer id_, String name_, double latitude_, double longitude_) {
        this.id = id_;
        this.username = name_;
        this.latitude = latitude_;
        this.longitude = longitude_;
    }


    // getters and setters for each variable
    public String getName() {
        return username;
    }
    public void setName(String name) {
        this.username = name;
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

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }
}
