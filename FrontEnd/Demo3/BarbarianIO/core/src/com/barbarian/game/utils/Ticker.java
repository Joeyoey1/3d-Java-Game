package com.barbarian.game.utils;

public class Ticker {

    private long start_ticks;

    public void start() {start_ticks = System.currentTimeMillis();}
    public double get_ticks(double delta_time){ return ( System.currentTimeMillis() - start_ticks) * delta_time ;}
}
