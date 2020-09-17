package com.codecool.dungeoncrawl.logic.actors;

import com.codecool.dungeoncrawl.logic.Cell;
import com.codecool.dungeoncrawl.util.AiMove;

public class SkeletonHead extends Actor {

    public SkeletonHead(Cell cell) {
        super(cell);
        super.setHealth(12);
        super.setDmg(4);

        new AiMove(this).start();
    }

    @Override
    public String getTileName() {
        return "skeleton_head";
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
