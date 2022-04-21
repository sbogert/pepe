package com.example.pepe.model;

import com.google.firebase.firestore.GeoPoint;

public class SellerInfo {
    private String email;
    private String storeName;
    private String password;
    private GeoPoint location;
    private String address;

    public SellerInfo(String email, String storeName, String password, GeoPoint geoPoint, String address) {
        this.email = email;
        this.storeName = storeName;
        this.password = password;
        this.location = geoPoint;
        this.address = address;
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

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }
}
