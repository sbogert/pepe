package com.example.pepe.model;

import androidx.annotation.NonNull;

public class MenuItem {

    //Creating properties of the class
    private String name;
    private String price;
    private String caffeine;
    private String storeID;

    public MenuItem(){

    }
    public MenuItem(String name, String price, String caffeine){
        this.name = name;
        this.price = price;
        this.caffeine = caffeine;
    }

    //Setter and Getters
    public String getStoreID() {return storeID; }
    public void setStoreID(String storeID) {this.storeID = storeID; }
    public String getPrice() {
        return price;
    }
    public void setPrice(String price) {
        this.price = price;
    }
    public String getName() {
        return name;
    }
    public void setName(String name) {
        this.name = name;
    }
    public String getCaffeine() {
        return caffeine;
    }
    public void setCaffeine(String caffeine) {
        this.caffeine = caffeine;
    }

    @NonNull
    @Override
    public String toString() {
        return "[ Name = " + name + ",  Price = " + price + ", Caffeine = " + caffeine + "]";
    }
}
