package com.example.kevin.homesafe;



public class User {
    protected String phoneNumber;
    protected Friend[] friends;

    public User (String phoneNumber) {
        this.phoneNumber = phoneNumber;
        this.friends = new Friend[4];
    }

    public String getPhoneNumber() {
        return phoneNumber;
    }


    public void setPhoneNumber(String phoneNumber) {
        this.phoneNumber = phoneNumber;
    }

}
