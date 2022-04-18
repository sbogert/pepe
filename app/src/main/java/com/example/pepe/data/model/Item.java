package com.example.pepe.data.model;

public class Item{

    //Creating properties of the class
    private Integer id;
    private String name;
    private int price;
    private int caffeine;

    public Item(String name, int price, int caffeine){
        this.name = name;
        this.price = price;
        this.caffeine = caffeine;
    }

    //Setter and Getters
    public Integer getId() {
        return id;
    }
    public void setId(Integer id) {
        this.id = id;
    }
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
