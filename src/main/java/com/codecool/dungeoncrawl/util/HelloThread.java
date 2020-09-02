package com.codecool.dungeoncrawl.util;

import com.codecool.dungeoncrawl.logic.CellType;
import com.codecool.dungeoncrawl.logic.GameMap;
import com.codecool.dungeoncrawl.logic.actors.Actor;
import java.lang.*;

public class HelloThread extends Thread {
    public void run() {
        while(true) {
            try {
                Thread.sleep(1000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        int x = Random.getNumberBetween(0,2);
        int y = Random.getNumberBetween(0,2);



        }
    }

    public static void moveActor(GameMap map, int x, int y, Actor actor){
        map.getCell(x,y).setActor(actor);
        map.getCell(x,y).setType(CellType.FLOOR);
    }
}

