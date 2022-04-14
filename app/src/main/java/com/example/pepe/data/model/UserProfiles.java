package com.example.pepe.data.model;
import java.util.ArrayList;

public class UserProfiles {

    private ArrayList<LoggedInUser> userProfiles = new ArrayList<LoggedInUser>();

    public UserProfiles(ArrayList<LoggedInUser> userProfiles_) {

        System.out.println(userProfiles_.get(0));
        this.userProfiles = userProfiles_;
    }

    public void setUserProfiles(ArrayList<LoggedInUser> userProfiles) {
        this.userProfiles = userProfiles;
    }

    public ArrayList<LoggedInUser> getUserProfiles() {
        return userProfiles;
    }


    /** find a user by searching through the arraylist until the usernames match*/
    public LoggedInUser findUser(String username, String password) {
        for (LoggedInUser userI : userProfiles) {
            if (userI.getUsername() == username) {
                if (userI.getPassword() == password) {
                    return userI;
                }
            }
        }

        return null;
    }
}
