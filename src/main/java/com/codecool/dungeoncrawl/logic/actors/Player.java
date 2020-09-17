package com.codecool.dungeoncrawl.logic.actors;

import com.codecool.dungeoncrawl.Tiles;
import com.codecool.dungeoncrawl.logic.*;
import com.codecool.dungeoncrawl.util.PopUp;
import javafx.scene.control.Button;
import javafx.scene.control.Label;

import java.util.HashMap;

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
        System.out.println("Player class: " + inventory.toString());
    }

    public HashMap<String,Integer> getInventory() {
        return inventory;
    }

    public void clearInventory() {
        inventory.entrySet().removeAll(inventory.entrySet());
    }

    public void removeItem(String item) {
        inventory.remove(item);
    }

    public void openDoor(GameMap map, int x, int y, CellType door, String key){
        if (map.getPlayer().getInventory().containsKey(key)) {
            map.getCell(x, y).setType(door);
        }
    }

    public void setArmor(GameMap map, String item, int x, int y, int value) {
        if (map.getPlayer().getInventory().containsKey(item)) {
            map.getPlayer().getCell().setType(CellType.FLOOR);
            map.getPlayer().addHealth(value);
            Tiles.changePlayerLook(x,y);
        }
    }

    public void checkCellForItem(GameMap map, Button getItemButton, Label inventory){
        if (map.getPlayer().getCell().getType().equals(CellType.SWORD) ||
                map.getPlayer().getCell().getType().equals(CellType.KEY) ||
                map.getPlayer().getCell().getType().equals(CellType.HELMET) ||
                map.getPlayer().getCell().getType().equals(CellType.CHEST) ||
                map.getPlayer().getCell().getType().equals(CellType.KEY_2) ||
                map.getPlayer().getCell().getType().equals(CellType.KEY_3) ||
                map.getPlayer().getCell().getType().equals(CellType.KEY_4)) {

            getItemButton.setVisible(true);
            getItemButton.setOnAction(event -> {
                try {
                    map.getPlayer().pickUp(map.getPlayer().getCell().getType().toString());
                    if (map.getPlayer().getInventory().containsKey("HELMET")){
                        setArmor(map, "HELMET", 28, 0, 10);
                        getInventory().remove("HELMET");
                    } else if (map.getPlayer().getInventory().containsKey("SWORD")){
                        this.setDmg(15);
                        Tiles.changePlayerLook(27,0);
                    } else if (map.getPlayer().getInventory().containsKey("CHEST")){
                        setArmor(map, "CHEST", 31, 0, 25);
                        getInventory().remove("CHEST");
                    }
                    inventory.setText(getInventory().toString());
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

    public void checkCell(GameMap map, Combat combat, int x, int y, Label label, Label label2) {
        if (isAdmin()){
            checkMove(map, combat, x, y, label, label2);
        } else {
            if (!(map.getPlayer().getCell().getNeighbor(x,y).getTileName().equals("wall")) &&
                    !(map.getPlayer().getCell().getNeighbor(x,y).getTileName().contains("closed"))){
                if(this.checkIfAlive(this)){
                    checkMove(map, combat, x, y, label, label2);
                } else {
                    PopUp.display("YOU LOST", "GAME OVER!", "Red");
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

    private void checkMove(GameMap map, Combat combat, int x, int y, Label label, Label label2) {
        try {
            if (map.getPlayer().getCell().getNeighbor(x, y).getActor().getTileName().equals("ghost")){
                this.setHealth(0);
            }
            map.getPlayer().getCell().getNeighbor(x, y).getActor().getTileName();
            combat.fight(map.getPlayer(),map.getPlayer().getCell().getNeighbor(x, y).getActor(), label, label2);
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
