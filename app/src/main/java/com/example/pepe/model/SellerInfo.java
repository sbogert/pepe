package com.example.pepe.model;

import com.google.firebase.firestore.GeoPoint;

public class SellerInfo {
    private String email;
    private String storeName;
    private String password;
    private GeoPoint location;

    public SellerInfo(String email, String storeName, String password, GeoPoint geoPoint) {
        this.email = email;
        this.storeName = storeName;
        this.password = password;
        this.location = geoPoint;
    }


    public String getEmail() {
        return email;
    }
    public void setEmail(String email) {
        this.email = email;
    }
    public String getStoreName() {
        return storeName;
    }
    public void setStoreName(String storeName) {
        this.storeName = storeName;
    }
    public String getPassword() {
        return password;
    }
    public void setPassword(String password) {
        this.password = password;
    }

    public GeoPoint getLocation() {
        return location;
    }

    public void setLocation(GeoPoint location) {
        this.location = location;
    }
}
