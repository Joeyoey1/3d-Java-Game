package com.barbarian.game.components;

import com.badlogic.ashley.core.Component;
import com.barbarian.game.attacks.Attack;

public class AttackComponent implements Component {
    private Attack attack;
    private boolean landed;

    public AttackComponent(){
        attack = new Attack();
        landed  = false;
    }

    public Attack getAttack(){return attack;}

    public boolean getAttackStatus(){return landed;}
    public void setAttackStatus(){landed = true;}
}
