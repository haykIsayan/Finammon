package com.hisayan.fbase.objects;

import com.google.firebase.auth.FirebaseUser;

/**
 * Created by hisayan on 10/29/17.
 */

public class User {

    //fields
    private String id;
    private String displayName;
    private String image;
    private boolean online;


    //contructors
    public User(){

        this.image = "";

    }

    public User(String id, String displayName, String image, boolean online) {
        this.id = id;
        this.displayName = displayName;
        this.image = image;
        this.online = online;
    }


    //getters & setters
    public String getId() {
        return id;
    }


    public void setId(String id) {
        this.id = id;
    }


    public String getDisplayName() {
        return displayName;
    }


    public void setDisplayName(String displayName) {
        this.displayName = displayName;
    }


    public String getImage() {
        return image;
    }


    public void setImage(String image) {
        this.image = image;
    }


    public boolean isOnline() {
        return online;
    }

    public void setOnline(boolean online) {
        this.online = online;
    }
}
