package com.codecool.dungeoncrawl.logic.actors;

import com.codecool.dungeoncrawl.logic.Cell;
import com.codecool.dungeoncrawl.logic.Drawable;
import com.sun.javafx.util.Utils;
import com.codecool.dungeoncrawl.util.Random;

public abstract class Actor implements Drawable {
    private Cell cell;
    private int health = 10;
    private int dmg = 2;

    public Actor(Cell cell) {
        this.cell = cell;
        this.cell.setActor(this);
    }

    public void move(int dx, int dy) {
        Cell nextCell = cell.getNeighbor(dx, dy);
        cell.setActor(null);
        nextCell.setActor(this);
        cell = nextCell;
    }

    public int getHealth() {
        return health;
    }

    public void setHealth(int value) {
        health = value;
    }

    public void addHealth(int value) { health += value; };

    public void setDmg(int v) {
        dmg = v;
    }

    public int getDmg() {
        return dmg;
    }

    public void subtractFromHealth(int value) {
        this.health -= value;
    }

    public Cell getCell() {
        return cell;
    }

    public int getX() {
        return cell.getX();
    }

    public int getY() {
        return cell.getY();
    }

    public void attack(Actor enemy, int min, int max){
        enemy.subtractFromHealth(Random.getNumberBetween(min,max));
    }

    public boolean checkIfAlive(Actor actor) {
        return actor.health > 0;
    }
}
