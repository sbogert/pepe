package com.example.pepe.model;


public class UserInfo {

        private String email;
        private String name;
        private String password;
        private Integer location;
        private OrderHistory orderHistory;

    public UserInfo(){}

    public UserInfo(String email, String name, String password) {
        this.email = email;
        this.name = name;
        this.password = password;
    }



    // getters and setters
    public String getEmail() {
        return email;
    }
    public void setEmail(String email) {
        this.email = email;
    }
    public String getPassword() {
        return password;
    }
    public void setPassword(String password) {
        this.password = password;
    }
    public Integer getLocation() {
        return location;
    }
    public void setLocation(Integer location) {
        this.location = location;
    }
    public OrderHistory getOrderHistory() {
        return orderHistory;
    }
    public void setOrderHistory(OrderHistory orderHistory) {
        this.orderHistory = orderHistory;
    }
    public String getName() {return name;}
    public void setName(String name) {this.name = name;}
}
