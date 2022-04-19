package com.example.pepe.model;

/** class to get userID when connecting to socket */
public class UserInfo {

        private Boolean isDrinker;
        private Integer id;

    public UserInfo() {}

    // getters and setters
    public Boolean getDrinker() {
        return isDrinker;
    }
    public void setDrinker(Boolean drinker) {
        isDrinker = drinker;
    }

    public Integer getId() {
        return id;
    }
    public void setId(Integer id) {
        this.id = id;
    }
}
