package com.codecool.dungeoncrawl.dao;

import com.codecool.dungeoncrawl.model.GameState;
import com.codecool.dungeoncrawl.model.PlayerModel;

import javax.sql.DataSource;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class GameStateDaoJdbc implements GameStateDao {

    private DataSource dataSource;
    private PlayerDao playerDao;

    public GameStateDaoJdbc(DataSource dataSource, PlayerDao playerDao) {
        this.dataSource = dataSource;
        this.playerDao = playerDao;
    }

    @Override
    public void add(GameState state) {
        try (Connection connection = dataSource.getConnection()) {
            String sql = "INSERT INTO game_state(current_map, saved_at, player_id) VALUES (?, ?, ?)";
            PreparedStatement statement = connection.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS);
            statement.setString(1, state.getCurrentMap());
            statement.setDate(2, state.getSavedAt());
            statement.setInt(3, state.getPlayer().getId());
            statement.executeUpdate();
            ResultSet resultSet = statement.getGeneratedKeys();
            resultSet.next();
            state.setId(resultSet.getInt(1));
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public void update(GameState state) {
        try (Connection connection = dataSource.getConnection()) {
            String sql = "UPDATE game_state SET current_map = ?, saved_at = ?, player_id = ?";
            PreparedStatement statement = connection.prepareStatement(sql);
            statement.setString(1, state.getCurrentMap());
            statement.setDate(2, state.getSavedAt());
            statement.setInt(3, state.getPlayer().getId());
            statement.executeUpdate();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public GameState get(int id) {
        try (Connection connection = dataSource.getConnection()) {
            String sql = "SELECT current_map, saved_at, player_id FROM game_state WHERE id = ?";
            PreparedStatement statement = connection.prepareStatement(sql);
            statement.setInt(1, id);
            ResultSet resultSet = statement.executeQuery();
            if (!resultSet.next()) {
                return null;
            }
            String current_map = resultSet.getString(1);
            Date saved_at = resultSet.getDate(2);
            int player_id = resultSet.getInt(3);
            PlayerModel playerModel = playerDao.get(player_id);

            GameState gameState = new GameState(current_map, saved_at, playerModel);
            gameState.setId(id);
            return gameState;

        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public List<GameState> getAll() {
        try (Connection connection = dataSource.getConnection()) {
            String sql = "SELECT id, current_map, saved_at, player_id FROM game_state";
            ResultSet resultSet = connection.createStatement().executeQuery(sql);

            List<GameState> game_states = new ArrayList<>();
            while (resultSet.next()) {
                int id = resultSet.getInt(1);
                String current_map = resultSet.getString(2);
                Date saved_at = resultSet.getDate(3);
                int player_id = resultSet.getInt(4);

                // find PlayerModel with id == player_id
                PlayerModel playerModel = playerDao.get(player_id);

                // create a new GameState class instance and add it to result list
                GameState gameState = new GameState(current_map, saved_at, playerModel);
                gameState.setId(id);
                game_states.add(gameState);
            }
            return game_states;
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }
}

