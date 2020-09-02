package com.codecool.dungeoncrawl.logic.actors;

import com.codecool.dungeoncrawl.logic.Cell;
import com.codecool.dungeoncrawl.util.HelloThread;

public class Ghost extends Actor {

    public Ghost(Cell cell) {
        super(cell);
        super.setHealth(12);
        super.setDmg(4);

//        new HelloThread().start();
    }

    @Override
    public String getTileName() {
        return "ghost";
    }

    @Override
    public int getHealth() {
        return super.getHealth();
    }

    @Override
    public void setHealth(int value) {
        super.setHealth(value);
    }

    @Override
    public void move(int dx, int dy) {
        super.move(dx, dy);
    }
}
