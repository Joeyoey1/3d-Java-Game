package com.barbarian.game.models;

import java.io.Serializable;

public class Hit implements Serializable {
    private Player attacker_;
    private Player victim_;
    private int attackID;

    public Hit(Player attacker, Player victim, int attack_id)
    {
        this.attacker_ = attacker;
        this.victim_ = victim;
        this.attackID = attack_id;
    }

    public int getAttackID() {
        return attackID;
    }

    public Player getAttacker_() {
        return attacker_;
    }

    public Player getVictim_() {
        return victim_;
    }
}
