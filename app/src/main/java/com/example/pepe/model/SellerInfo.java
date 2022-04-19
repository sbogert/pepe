package com.example.pepe.model;

public class SellerInfo {
    private String email;
    private String storeName;
    private String password;
    private String location;
    private Item[] items; // an array of Item

    public SellerInfo(String email, String storeName, String password) {
        this.email = email;
        this.storeName = storeName;
        this.password = password;
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
