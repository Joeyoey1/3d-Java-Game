package com.clientservice.demo;

public class User {
    private String name;
    private double health;
    private String password;

    public User(){}
    public User(String name){
        this.name = name;
        this.health = 80;
        this.password = "pass";
    }

    public void setName(String name){
        this.name = name;
    }
    public String getName(){
        return name;
    }

    public void setPassword(String pass){
        this.name = pass;
    }
    public String getPassword(){
        return password;
    }

    public void setHealth(int health){
        this.health = health;
    }
    public Double getHealth(){
        return health;
    }
}
