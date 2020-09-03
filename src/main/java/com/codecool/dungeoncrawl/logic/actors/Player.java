package com.codecool.dungeoncrawl.logic.actors;

import com.codecool.dungeoncrawl.Tiles;
import com.codecool.dungeoncrawl.logic.*;
import com.codecool.dungeoncrawl.util.PopUp;
import javafx.scene.control.Button;
import javafx.scene.control.Label;

import java.util.HashMap;
import java.util.Hashtable;

public class Player extends Actor {
    public HashMap<String,Integer> inventory = new HashMap<>();
    String name;

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

    public void openDoor(GameMap map, int x, int y, CellType door){
        if (map.getPlayer().getInventory().containsKey("KEY")) {
            map.getCell(x, y).setType(door);
        }
    }

    public void setArmor(GameMap map, String item) {
        if (map.getPlayer().getInventory().containsKey(item)) {
            map.getPlayer().getCell().setType(CellType.FLOOR);
            map.getPlayer().addHealth(5);
            Tiles.changePlayerLook(28,0);
            System.out.println("You took a helmet");
        }
    }

    public void checkCellForItem(GameMap map, Button getItemButton, Label inventory){
        if (map.getPlayer().getCell().getType().equals(CellType.SWORD) ||
                map.getPlayer().getCell().getType().equals(CellType.KEY) ||
                map.getPlayer().getCell().getType().equals(CellType.HELMET)){

            getItemButton.setVisible(true);
            getItemButton.setOnAction(event -> {
                try {
                    map.getPlayer().pickUp(map.getPlayer().getCell().getType().toString());
                    if (map.getPlayer().getInventory().containsKey("HELMET")){
                        setArmor(map, "HELMET");
                    } else if (map.getPlayer().getInventory().containsKey("SWORD")){
                        Tiles.changePlayerLook(27,0);
                    }
                    inventory.setText(map.getPlayer().getInventory().toString());
                    map.getPlayer().getCell().setType(CellType.FLOOR);
                    getItemButton.setVisible(false);
                } catch (Exception e){
                    System.out.println("Error");
                }
            });
        } else {
            getItemButton.setVisible(false);
        }
    }

    public void checkCell(GameMap map, Combat combat, int x, int y) {
        if (isAdmin()){
            checkMove(map, combat, x, y);
        } else {
            if (!(map.getPlayer().getCell().getNeighbor(x,y).getTileName().equals("wall"))){
                if(this.checkIfAlive(this)){
                    checkMove(map, combat, x, y);
                } else {
                    PopUp.display("YOU LOST", "GAME OVER!");
                }

            }
        }
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getName() {
        return this.name;
    }

    public boolean isAdmin() {
        if (name.equals("Gabi") || name.equals("Florin")){
            return true;
        } else {
            return false;
        }
    }

    private void checkMove(GameMap map, Combat combat, int x, int y) {
        try {
            if (map.getPlayer().getCell().getNeighbor(x, y).getActor().getTileName().equals("ghost")){
                PopUp.display("YOU LOST", "GAME OVER!");
            }
            map.getPlayer().getCell().getNeighbor(x, y).getActor().getTileName();
            combat.fight(map.getPlayer(),map.getPlayer().getCell().getNeighbor(x, y).getActor());
            if (map.getPlayer().getCell().getNeighbor(x, y).getActor().getHealth() <= 0){
                map.getPlayer().getCell().setType(CellType.FLOOR);
                map.getPlayer().move(x, y);
            }
        } catch (Exception e){
            map.getPlayer().move(x, y);
        }
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
