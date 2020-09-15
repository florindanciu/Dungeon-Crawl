package com.codecool.dungeoncrawl.util;

import com.codecool.dungeoncrawl.dao.GameDatabaseManager;

import com.codecool.dungeoncrawl.logic.actors.Player;
import com.codecool.dungeoncrawl.model.GameState;
import com.codecool.dungeoncrawl.model.InventoryModel;

public class SaveDB {
    public static void saveDb(Player player, GameDatabaseManager gameDatabaseManager, GameState gameState, InventoryModel inventoryModel) {
        gameDatabaseManager.savePlayer(player);
        gameDatabaseManager.saveGameState(gameState);
        gameDatabaseManager.saveInventory(inventoryModel);
    }
}
