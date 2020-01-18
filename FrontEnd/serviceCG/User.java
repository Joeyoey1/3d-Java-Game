package service;

public class User {
    private String name;
    private double health;

    public User(){}
    public User(String name){
        this.name = name;
        this.health = 80;
    }

    public void setName(String name){
        this.name = name;
    }
    public String getName(){
        return name;
    }

    public void setHealth(int health){
        this.health = health;
    }
    public Double getHealth(){
        return health;
    }
}
