package com.codecool.dungeoncrawl.logic.actors;

import com.codecool.dungeoncrawl.logic.Cell;
import com.codecool.dungeoncrawl.logic.GameMap;
import com.codecool.dungeoncrawl.logic.MapLoader;

import java.util.HashMap;
import java.util.Hashtable;

public class Player extends Actor {
    HashMap<String,Integer> inventory = new HashMap<>();

    public Player(Cell cell) {
        super(cell);
    }

    public String getTileName() {
        return "player";
    }

    public void pickUp(String item){
        int count = inventory.getOrDefault(item, 1);
        if (inventory.containsKey(item)){
            inventory.put(item, count++);
        }
        inventory.put(item, count);
        System.out.println(inventory.toString());
    }

    public HashMap<String,Integer> getInventory() {
        return inventory;
    }

}
