package com.example.pepe;

import android.app.Application;

public class MyApplication extends Application {

    private String currentUser;
    private String currentSeller;

    public String getUser() {
        return currentUser;
    }

    public void setUser(String user) {
        this.currentUser = user;
    }
}

// set
//((MyApplication) this.getApplication()).setUser("foo");

// get
//String s = ((MyApplication) this.getApplication()).getUser();

