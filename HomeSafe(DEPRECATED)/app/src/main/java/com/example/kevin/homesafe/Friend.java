package com.example.kevin.homesafe;

public class Friend {
    protected String name, phoneNumber;

    public Friend () {
        this.name = "";
        this.phoneNumber = "";
    }

    public String getName() {
        return name;
    }

    public String getPhoneNumber() {
        return phoneNumber;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setPhoneNumber(String phoneNumber) {
        this.phoneNumber = phoneNumber;
    }
}
