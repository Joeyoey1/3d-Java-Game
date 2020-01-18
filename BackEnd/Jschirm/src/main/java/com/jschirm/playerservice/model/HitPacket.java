package com.jschirm.playerservice.model;

public class HitPacket {

    private int attacker_;
    private int victim_;
    private Location centerPoint;
    private int attackID;

    public HitPacket(int attacker_, int victim_, Location centerPoint, int attackID) {
        this.attacker_ = attacker_;
        this.victim_ = victim_;
        this.centerPoint = centerPoint;
        this.attackID = attackID;
    }

    public int getAttacker_() {
        return attacker_;
    }

    public int getVictim_() {
        return victim_;
    }

    public Location getCenterPoint() {
        return centerPoint;
    }

    public int getAttackID() {
        return attackID;
    }
}
