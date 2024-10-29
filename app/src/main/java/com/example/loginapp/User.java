package com.example.loginapp;

public class User {

    String username;
    String password;

    public User() {

        username = "";
        password = "";
    }

    public void setUsername (String name) {

        username = name;
    }

    public void setPassword (String pass) {

        password = pass;
    }

    public String getUsername () {

        return username;
    }

    public  String getPassword () {

        return password;
    }
}
