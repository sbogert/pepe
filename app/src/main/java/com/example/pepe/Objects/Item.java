package com.example.pepe.Objects;

public class Item{
    public Item(String name, int price, int caffeine){
    }

    //Creating properties of the class
    private String name;
    private int price;
    private int caffeine;

    //Setter and Getters
    public int getPrice() {
        return price;
    }
    public void setPrice(int price) {
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

    @Override
    public String toString() {
        return "[ Name = " + name + ",  Price = " + price + ", Caffeine = " + caffeine + "]";
    }

}
