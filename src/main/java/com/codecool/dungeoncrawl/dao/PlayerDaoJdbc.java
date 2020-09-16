package com.codecool.dungeoncrawl.dao;

import com.codecool.dungeoncrawl.model.PlayerModel;

import javax.sql.DataSource;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class PlayerDaoJdbc implements PlayerDao {
    private DataSource dataSource;

    public PlayerDaoJdbc(DataSource dataSource) {
        this.dataSource = dataSource;
    }

    @Override
    public void add(PlayerModel player) {
        try (Connection conn = dataSource.getConnection()) {
            String sql = "INSERT INTO player (player_name, hp, x, y) VALUES (?, ?, ?, ?)";
            PreparedStatement statement = conn.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS);
            statement.setString(1, player.getPlayerName());
            statement.setInt(2, player.getHp());
            statement.setInt(3, player.getX());
            statement.setInt(4, player.getY());
            statement.executeUpdate();
            ResultSet resultSet = statement.getGeneratedKeys();
            resultSet.next();
            player.setId(resultSet.getInt(1));
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public void update(PlayerModel player) {
        try (Connection connection = dataSource.getConnection()){
            String sql = "UPDATE player SET player_name = ?, hp = ?, x = ?, y = ? WHERE id = ?";
            PreparedStatement statement = connection.prepareStatement(sql);
            statement.setString(1, player.getPlayerName());
            statement.setInt(2, player.getHp());
            statement.setInt(3,player.getX());
            statement.setInt(4, player.getY());
            statement.setInt(5, player.getId());
            statement.executeUpdate();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public PlayerModel get(int id) {
        try (Connection connection = dataSource.getConnection()){
            String sql = "SELECT player_name, hp, x, y FROM player WHERE id = ?";
            PreparedStatement statement = connection.prepareStatement(sql);
            statement.setInt(1, id);
            ResultSet resultSet = statement.executeQuery();
            if (!resultSet.next()){
                return null;
            }
            String player_name = resultSet.getString(1);
            int hp = resultSet.getInt(2);
            int x = resultSet.getInt(3);
            int y = resultSet.getInt(4);
            PlayerModel playerModel = new PlayerModel(player_name, x, y);
            playerModel.setId(id);
            return playerModel;

        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public List<PlayerModel> getAll() {
        try (Connection connection = dataSource.getConnection()){
            String sql = "SELECT id, player_name, hp, x, y FROM player";
            ResultSet resultSet = connection.createStatement().executeQuery(sql);
            List<PlayerModel> players = new ArrayList<>();
            while (resultSet.next()) {
                PlayerModel playerModel = new PlayerModel(resultSet.getString(2),
                        resultSet.getInt(3),
                        resultSet.getInt(5));
                playerModel.setId(resultSet.getInt(1));
                players.add(playerModel);
            }
            return players;
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }
}
