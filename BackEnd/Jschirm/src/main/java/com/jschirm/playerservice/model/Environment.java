package com.jschirm.playerservice.model;

import javax.persistence.*;

@Entity
public class Environment {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    @Column
    private int color;

    public int getColor() { return this.color; }

    public void setColor(int color) {
        this.color = color;
    }

}
