package com.codecool.dungeoncrawl.util;

import com.codecool.dungeoncrawl.dao.GameDatabaseManager;

import com.codecool.dungeoncrawl.logic.actors.Player;
import com.codecool.dungeoncrawl.model.GameState;

public class SaveDB {
    public static void saveDb(Player player, GameDatabaseManager gameDatabaseManager, GameState gameState) {
        gameDatabaseManager.saveGameState(gameState);
        gameDatabaseManager.savePlayer(player);
    }
}
