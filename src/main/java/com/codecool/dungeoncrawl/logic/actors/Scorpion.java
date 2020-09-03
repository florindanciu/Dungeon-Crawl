package com.codecool.dungeoncrawl.logic.actors;

import com.codecool.dungeoncrawl.logic.Cell;
import com.codecool.dungeoncrawl.util.AiMove;

public class Scorpion extends Actor {

    public Scorpion(Cell cell) {
        super(cell);
        super.setHealth(15);
        super.setDmg(5);
    }

    @Override
    public String getTileName() {
        return "scorpion";
    }

    @Override
    public int getHealth() {
        return super.getHealth();
    }

    @Override
    public void setHealth(int value) {
        super.setHealth(value);
    }

}
