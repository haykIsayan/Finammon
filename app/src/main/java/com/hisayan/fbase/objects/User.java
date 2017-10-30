package com.hisayan.fbase.objects;

/**
 * Created by hisayan on 10/29/17.
 */

public class User {

    //fields
    private String id;
    private String displayName;
    private String image;


    //contructors
    public User(){}

    public User(String id, String displayName, String image) {
        this.id = id;
        this.displayName = displayName;
        this.image = image;
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

}
