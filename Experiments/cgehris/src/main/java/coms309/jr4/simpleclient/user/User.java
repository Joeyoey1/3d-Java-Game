package coms309.jr4.simpleclient.user;

public class User {
    private String username;
    private String password;
    private double health;

    public User(){}
    public User(String name, String pass){
        this.username = name;
        this.password = pass;
        this.health = 80;
    }

    public void setUsername(String name){
        this.username = name;
    }
    public String getUsername(){
        return username;
    }

    public void setPassword(String pass){
        this.password = pass;
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
