package com.codecool.dungeoncrawl.model;

import java.util.HashMap;

public class InventoryModel extends BaseModel{
    private HashMap<String, Integer> inventoryItems;
    private PlayerModel player;

    public InventoryModel(HashMap<String, Integer> inventoryItems, PlayerModel player) {
        this.inventoryItems = inventoryItems;
        this.player = player;
    }

    public HashMap<String, Integer> getInventoryItems() {
        return inventoryItems;
    }

    public void setInventoryItems(HashMap<String, Integer> inventoryItems) {
        this.inventoryItems = inventoryItems;
    }

    public PlayerModel getPlayer() {
        return player;
    }

    public void setPlayer(PlayerModel player) {
        this.player = player;
    }
}
