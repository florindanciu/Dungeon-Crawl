package com.codecool.dungeoncrawl.dao;

import com.codecool.dungeoncrawl.logic.actors.Player;
import com.codecool.dungeoncrawl.model.GameState;
import com.codecool.dungeoncrawl.model.InventoryModel;
import com.codecool.dungeoncrawl.model.PlayerModel;
import org.postgresql.ds.PGSimpleDataSource;

import javax.sql.DataSource;
import java.sql.SQLException;
import java.util.List;
import java.util.concurrent.atomic.AtomicBoolean;
import java.util.concurrent.atomic.AtomicReference;

public class GameDatabaseManager {
    private PlayerDao playerDao;
    private GameStateDao gameStateDao;
    private InventoryDao inventoryDao;

    public void setup() throws SQLException {
        DataSource dataSource = connect();
        playerDao = new PlayerDaoJdbc(dataSource);
        gameStateDao = new GameStateDaoJdbc(dataSource, playerDao);
        inventoryDao = new InventoryDaoJdbc(dataSource, playerDao);
    }

    public List<PlayerModel> getPlayers(){
        return playerDao.getAll();
    }

    public GameStateDao getGameStateDao() {
        return gameStateDao;
    }

    public PlayerDao getPlayerDao() {
        return playerDao;
    }

    public void savePlayer(Player player) {
        PlayerModel model = new PlayerModel(player);
        playerDao.getAll().forEach(playerModel -> {
            if (playerModel.getPlayerName().equals(player.getName())){
                model.setId(playerModel.getId());
            }
        });
        if (model.getId() != null) {
            playerDao.update(model);

        } else {
            playerDao.add(model);
        }
    }

    public void saveGameState(GameState gameState) {
//        AtomicReference<Integer> id = new AtomicReference<>();
        GameState newState = new GameState(gameState.getCurrentMap(), gameState.getSavedAt(), gameState.getPlayer());
        playerDao.getAll().forEach(playerModel -> {
            if (playerModel.getPlayerName().equals(gameState.getPlayer().getPlayerName())){
//                id.set(playerModel.getId());
                newState.getPlayer().setId(playerModel.getId());
            }
        });
        if (gameStateDao.getAll().stream().anyMatch(state -> state.getPlayer().getId().equals(newState.getPlayer().getId()))) {
            gameStateDao.update(newState);
            System.out.println("update");
        } else {
            gameStateDao.add(newState);
            System.out.println("add");
        }
    }

    public void saveInventory(InventoryModel inventoryModel) {
        InventoryModel newInventoryModel = new InventoryModel(inventoryModel.getInventoryItems(), inventoryModel.getPlayer());
        playerDao.getAll().forEach(playerModel -> {
            if (playerModel.getPlayerName().equals(inventoryModel.getPlayer().getPlayerName())) {
                newInventoryModel.setId(playerModel.getId());
            }
        });
        if (inventoryModel.getId() != null) {
            inventoryDao.update(newInventoryModel);
        } else {
            inventoryDao.add(newInventoryModel);
        }
    }

    public boolean checkPlayerDb(Player player) {
        AtomicBoolean playerDB = new AtomicBoolean(false);
        playerDao.getAll().forEach(playerModel -> {
            if (playerModel.getPlayerName().equals(player.getName())) {
                playerDB.set(true);
            }
        });
        return playerDB.get();
    }

    private DataSource connect() throws SQLException {
        PGSimpleDataSource dataSource = new PGSimpleDataSource();
        String dbName = "dungeon_crawl";
        String user = "gabi";
        String password = "123";
        dataSource.setDatabaseName(dbName);
        dataSource.setUser(user);
        dataSource.setPassword(password);

        System.out.println("Trying to connect");
        dataSource.getConnection().close();
        System.out.println("Connection ok.");

        return dataSource;
    }


}
