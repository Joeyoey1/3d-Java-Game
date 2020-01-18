package com.barbarian.game.utils;

public class Ticker {

    private long start_ticks;

    /**
     * sets ticker start
     */
    public void start() {start_ticks = System.currentTimeMillis();}

    /**
     * @param delta_time
     * @return gets current amount of ticks from beginning of measurement
     */
    public double get_ticks(double delta_time){ return ( System.currentTimeMillis() - start_ticks) * delta_time ;}
}
