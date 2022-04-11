package com.example.pepe.data.model;
import java.util.ArrayList;

public class UserProfiles {

    private ArrayList<LoggedInUser> userProfiles = new ArrayList<LoggedInUser>();

    public UserProfiles (ArrayList<LoggedInUser> userProfiles_) {

        System.out.println(userProfiles_.get(0));
        this.userProfiles = userProfiles_;
    }

    public void setUserProfiles(ArrayList<LoggedInUser> userProfiles) {
        this.userProfiles = userProfiles;
    }

    public ArrayList<LoggedInUser> getUserProfiles() {
        return userProfiles;
    }
}
