package com.example.pepe.model;

import com.google.android.gms.maps.model.LatLng;

public class UserInfo {

        private String email = "";
        private String name = "";
        private String password = "";
        private LatLng location = null;
        private Integer caffeineLimit = 0;

    public UserInfo() {
    }

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
    public String getName() {return name;}
    public void setName(String name) {this.name = name;}
    public String getPassword() {
        return password;
    }
    public void setPassword(String password) {
        this.password = password;
    }
    public LatLng getLocation() {
        return location;
    }
    public void setLocation(LatLng location) {
        this.location = location;
    }
    public Integer getCaffeineLimit() {
        return caffeineLimit;
    }
    public void setCaffeineLimit(Integer cf) {
        this.caffeineLimit = cf;
    }
}
