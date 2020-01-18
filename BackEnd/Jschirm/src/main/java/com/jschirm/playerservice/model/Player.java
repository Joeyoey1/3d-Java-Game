package com.jschirm.playerservice.model;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.jschirm.playerservice.utils.Quaternion;
//import org.springframework.data.annotation.Id;

import javax.persistence.*;
import javax.validation.constraints.NotBlank;
import java.io.Serializable;
import java.util.UUID;

@Entity
public class Player implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @Column
    private double health;

    @Column
    private int color;

    @ManyToOne(cascade = CascadeType.ALL, targetEntity = Location.class)
    @JoinColumn(name = "location_id")
    private Location location;

    @Column
    private int characterState;

    @Column
    private double attackDamage;

    @Column
    private double attackRange;

    @Transient
    private Quaternion rotation;

    /**
     * @return character state for animation
     */
    public int getCharacterState() {
        return characterState;
    }

    /**
     * @param characterState
     */
    public void setCharacterState(int characterState) {
        this.characterState = characterState;
    }

    /**
     * @return attack damage of player
     */
    public double getAttackDamage() {
        return attackDamage;
    }

    /**
     * @param attackDamage
     */
    public void setAttackDamage(double attackDamage) {
        this.attackDamage = attackDamage;
    }

    /**
     * @return attack range of player
     */
    public double getAttackRange() {
        return attackRange;
    }

    /**
     * @param attackRange
     */
    public void setAttackRange(double attackRange) {
        this.attackRange = attackRange;
    }

    /**
     * @return mana pool of player
     */
    public double getManaPool() {
        return manaPool;
    }

    /**
     * @param manaPool
     */
    public void setManaPool(double manaPool) {
        this.manaPool = manaPool;
    }

    @Column
    private double manaPool;

  /*  @Column
    public Location location;
*/

/*    //TODO add params for health and stuff. Allow getting of said variables.
    public Player(@JsonProperty("id") Integer id,
                  @JsonProperty("name") String name,
                  @JsonProperty("health") double health){
                  //@JsonProperty("location") Location location) {
        this.id = id;
        this.name = name;
        this.health = health;
       // this.location = location;
    }*/

    /**
     * default player creation
     */
    public Player() {
        this.characterState = 0;
        this.location = new Location();
        this.health = 100;
        this.attackDamage = 5;
        this.attackRange = 6;
        this.rotation = (new Quaternion()).setEulerAnglesRad(0,0,0);
    }

    public Player(Integer id, double health, int color, Location location, int characterState, double attackDamage, double attackRange, Quaternion rotation, double manaPool) {
        this.id = id;
        this.health = health;
        this.color = color;
        this.location = location;
        this.characterState = characterState;
        this.attackDamage = attackDamage;
        this.attackRange = attackRange;
        this.rotation = rotation;
        this.manaPool = manaPool;
    }

    /**
     * @return id of player
     */
    public Integer getId() {
        return id;
    }

    /**
     * @return health of player
     */
    public double getHealth() {
        return health;
    }

    /**
     * @param health
     */
    public void setHealth(double health) { this.health = health; }

    /**
     * @return color of player
     */
    public int getColor() { return this.color; }

    /**
     * @param color
     */
    public void setColor(int color) {
        this.color = color;
    }

    /**
     * @param location
     * location of player
     */
    public void setLocation(Location location) { this.location = location; }

    /**
     * @return
     */
    public Location getLocation() { return this.location != null ? this.location : new Location(); }

    /**
     * @param id
     * set id of player if not generated
     */
    public void setId(Integer id) {
        this.id = id;
    }

    public Quaternion getRotation() {
        return this.rotation;
    }

    public void setRotation(Quaternion rotation) {
        this.rotation = rotation;
    }

}
