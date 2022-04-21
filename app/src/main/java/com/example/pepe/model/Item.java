package com.example.pepe.model;

import androidx.annotation.NonNull;

public class Item{

    //Creating properties of the class
    private String name;
    private double price;
    private int caffeine;

    public Item(){

    }
    public Item(String name, double price, int caffeine){
        this.name = name;
        this.price = price;
        this.caffeine = caffeine;
    }

    //Setter and Getters
    public double getPrice() {
        return price;
    }
    public void setPrice(double price) {
        this.price = price;
    }
    public String getName() {
        return name;
    }
    public void setName(String name) {
        this.name = name;
    }
    public int getCaffeine() {
        return caffeine;
    }
    public void setCaffeine(int caffeine) {
        this.caffeine = caffeine;
    }

    @NonNull
    @Override
    public String toString() {
        return "[ Name = " + name + ",  Price = " + price + ", Caffeine = " + caffeine + "]";
    }
}
