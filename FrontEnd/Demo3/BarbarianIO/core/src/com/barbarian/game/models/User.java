package com.barbarian.game.models;

import javax.validation.constraints.NotBlank;

public class User {

    private Integer id;

    @NotBlank
    private String password;


    @NotBlank
    private String username;

    //private Integer permissionLevel;

    private Player player;

    public User(Integer id, @NotBlank String password, @NotBlank String username, Integer permissionLevel, Player player) {
        //this.id = id;
        this.password = password;
        this.username = username;
        //this.permissionLevel = permissionLevel;
        //this.player = player;
    }

    public User() {
        this.password = null;
        this.username = null;
        player = new Player();
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getUsername() {
        return username;
    }

    public void setName(String username) {
        this.username = username;
    }

    public Player getPlayer() {
        return player != null ? player : new Player();
    }

    public void setPlayer(Player player) {
        this.player = player;
    }

    //public Integer getPermissionLevel() { return permissionLevel;}

    //public void setPermissionLevel(Integer permissionLevel) {this.permissionLevel = permissionLevel;}
}
