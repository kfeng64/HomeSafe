package com.example.kevin.homesafe;



public class User {
    protected String name;
    protected Friend[] friends;

    public User (String name) {
        this.name = name;
        this.friends = new Friend[4];
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }




}
