package com.codecool.dungeoncrawl.dao;

import com.codecool.dungeoncrawl.logic.actors.Player;
import com.codecool.dungeoncrawl.model.GameState;
import com.codecool.dungeoncrawl.model.PlayerModel;
import org.postgresql.ds.PGSimpleDataSource;

import javax.sql.DataSource;
import java.sql.SQLException;
import java.util.concurrent.atomic.AtomicBoolean;

public class GameDatabaseManager {
    private PlayerDao playerDao;
    private GameStateDao gameStateDao;

    public void setup() throws SQLException {
        DataSource dataSource = connect();
        playerDao = new PlayerDaoJdbc(dataSource);
        gameStateDao = new GameStateDaoJdbc(dataSource, playerDao);
    }

    public void savePlayer(Player player) {
        PlayerModel model = new PlayerModel(player);
        playerDao.getAll().forEach(p -> {
            if (p.getPlayerName().equals(player.getName())){
                model.setId(p.getId());
            }
        });
        if (model.getId() != null) {
            playerDao.update(model);

        } else {
            playerDao.add(model);
        }
    }

    public void saveGameState(GameState gameState) {
        gameStateDao.getAll().forEach(g -> {
            if (g.getPlayer().getPlayerName().equals(gameState.getPlayer().getPlayerName())){
                gameState.getPlayer().setId(g.getPlayer().getId());
            }
        });

        if (gameState.getId() != null) {
            gameStateDao.update(gameState);
        } else {
            gameStateDao.add(gameState);
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
        String user = "florin";
        String password = "1234";

        dataSource.setDatabaseName(dbName);
        dataSource.setUser(user);
        dataSource.setPassword(password);

        System.out.println("Trying to connect");
        dataSource.getConnection().close();
        System.out.println("Connection ok.");

        return dataSource;
    }


}
