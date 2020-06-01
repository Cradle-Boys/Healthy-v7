package com.example.healthy_v_7.model;

/**
 * Created by paolotormon on 27 May 2020
 */
public class User {

    private String user;
    private String email;
    private String bio;

    public User() {
    }

    public User(String user, String email, String bio) {
        this.user = user;
        this.email = email;
        this.bio = bio;
    }

    public void setUser(String user) {
        this.user = user;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public void setBio(String bio) {
        this.bio = bio;
    }

    public String getUser() {
        return user;
    }

    public String getEmail() {
        return email;
    }

    public String getBio() {
        return bio;
    }

}
