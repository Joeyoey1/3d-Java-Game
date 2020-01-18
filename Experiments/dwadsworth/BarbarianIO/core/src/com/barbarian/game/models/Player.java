package com.barbarian.game.models;

import java.util.Random;

public class Player {

  /*  @Column
    public Location location;
*/

/*    //DO add params for health and stuff. Allow getting of said variables.
    public Player(@JsonProperty("id") Integer id,
                  @JsonProperty("name") String name,
                  @JsonProperty("health") double health){
                  //@JsonProperty("location") Location location) {
        this.id = id;
        this.name = name;
        this.health = health;
       // this.location = location;
    }*/

    private Integer id;

    private double health;

    private int color;

    private Location location;

    private int characterState;

    private double attackDamage;

    private double attackRange;

    private double  manaPool;

    /**
     * creates default player
     */
    public Player() {
        this.characterState = 0;
        this.location = new Location();
        this.health = 100;
        this.attackDamage = 5;
        this.attackRange = 6;
        this.manaPool = 0;
        Random random = new Random();
        this.id = random.nextInt(3000);
        this.color = 0;
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
     */
    public void setLocation(Location location) { this.location = location; }

    /**
     * @return Location object of player
     */
    public Location getLocation() { return this.location != null ? this.location : new Location(); }


    /**
     * @return state of player for animation
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
     * @return attack damage value of player
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

    /**
     * @param id
     */
    public void setId(Integer id) {
        this.id = id;
    }
}
