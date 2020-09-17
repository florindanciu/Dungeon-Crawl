package com.codecool.dungeoncrawl.util;

import com.codecool.dungeoncrawl.logic.actors.Actor;
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
                Thread.sleep(1000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            int x = Random.getNumberBetween(-1, 2);
            int y = Random.getNumberBetween(-1, 2);
            if (!(actor.getCell().getNeighbor(x,y).getTileName().equals("wall")) && !(actor.getCell().getNeighbor(x,y).getTileName().contains("door"))){
                if (!(actor.getCell().getNeighbor(x,y).getTileName().equals("bridge"))){
                    actor.move(x, y);
                }
            }
        }
    }
}