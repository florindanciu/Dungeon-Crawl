package com.codecool.dungeoncrawl.util;

import com.codecool.dungeoncrawl.Main;
import com.codecool.dungeoncrawl.Tiles;
import com.codecool.dungeoncrawl.logic.Cell;
import com.codecool.dungeoncrawl.logic.CellType;
import com.codecool.dungeoncrawl.logic.GameMap;
import com.codecool.dungeoncrawl.logic.actors.Actor;
import com.codecool.dungeoncrawl.logic.actors.Ghost;
import javafx.scene.canvas.GraphicsContext;

import java.lang.*;

public class AiMove extends Thread {

    Actor actor;

    public AiMove(Actor actor) {
        super();
        this.actor = actor;
    }

    public void run() {
        while (true) {
            try {
                Thread.sleep(500);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            int x = Random.getNumberBetween(-1, 2);
            int y = Random.getNumberBetween(-1, 2);
            if (!(actor.getCell().getNeighbor(x,y).getTileName().equals("wall"))){
                actor.move(x, y);
            }
        }
    }
}