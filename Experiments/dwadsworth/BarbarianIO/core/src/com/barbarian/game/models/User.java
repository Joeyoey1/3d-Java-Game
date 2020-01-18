package com.barbarian.game.models;

import javax.validation.constraints.NotBlank;

public class User {

    private Integer id;

    @NotBlank
    private String password;


    @NotBlank
    private String username;

    private Integer permissionLevel;

    private Player player;

    /**
     * @param id
     * @param password
     * @param username
     * @param permissionLevel
     * @param player
     * creates user with given params
     */
    public User(Integer id, @NotBlank String password, @NotBlank String username, Integer permissionLevel, Player player) {
        this.id = id;
        this.password = password;
        this.username = username;
        this.permissionLevel = permissionLevel;
        this.player = player;
    }

    /**
     * creates blank user
     */
    public User() {

    }

    /**
     * @return id of user
     */
    public Integer getId() {
        return id;
    }

    /**
     * @param id
     */
    public void setId(Integer id) {
        this.id = id;
    }

    /**
     * @return password of user
     */
    public String getPassword() {
        return password;
    }

    /**
     * @param password
     */
    public void setPassword(String password) {
        this.password = password;
    }

    /**
     * @return usernmae of user
     */
    public String getUsername() {
        return username;
    }

    /**
     * @param username
     * sets username
     */
    public void setName(String username) {
        this.username = username;
    }

    /**
     * @return gets player object of user
     */
    public Player getPlayer() {
        return player != null ? player : new Player();
    }

    /**
     * @param player
     */
    public void setPlayer(Player player) {
        this.player = player;
    }

    /**
     * @return permission level of user
     */
    public Integer getPermissionLevel() {
        return permissionLevel;
    }

    /**
     * @param permissionLevel
     */
    public void setPermissionLevel(Integer permissionLevel) {
        this.permissionLevel = permissionLevel;
    }
}
