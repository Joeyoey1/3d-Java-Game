package com.jschirm.playerservice.model;

import com.fasterxml.jackson.annotation.JsonProperty;
import org.hibernate.annotations.Type;

import javax.persistence.*;
import javax.validation.constraints.NotBlank;

@Entity
@Table(name = "User")
public class User {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @Column
    @NotBlank
    private String password;


    @NotBlank
    @Column
    private String username;

    @ManyToOne(cascade = CascadeType.ALL, targetEntity = Player.class)
    @JoinColumn(name = "player_id")
    private Player player;

    /**
     * @param password
     * @param username
     * create new user with given password and username
     */
    public User(@JsonProperty("password") @NotBlank String password, @JsonProperty("username") @NotBlank String username) {
        this.password = password;
        this.username = username;
    }

    /**
     * create default user
     */
    public User() {
        this.password = null;
        this.username = null;
        player = new Player();
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
     * @return username if user
     */
    public String getUsername() {
        return username;
    }

    /**
     * @param username
     */
    public void setName(String username) {
        this.username = username;
    }

    /**
     * @return player object of user
     */
    public Player getPlayer() {
        return player != null ? player : new Player();
    }

    /**
     * @param player
     * player object of user
     */
    public void setPlayer(Player player) {
        this.player = player;
    }

}
