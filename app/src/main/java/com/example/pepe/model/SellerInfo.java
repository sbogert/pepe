package com.example.pepe.model;

import com.google.android.gms.maps.model.LatLng;

public class SellerInfo {
    private String email;
    private String storeName;
    private String password;
    private LatLng location;
    private Item[] items; // an array of Item

    public SellerInfo(String email, String storeName, String password, LatLng latLng) {
        this.email = email;
        this.storeName = storeName;
        this.password = password;
        this.location = latLng;
    }

    public Item[] getItems() {
        return items;
    }
    public void setItems(Item[] items) {
        this.items = items;
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
}
