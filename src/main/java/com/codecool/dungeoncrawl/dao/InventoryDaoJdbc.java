package com.codecool.dungeoncrawl.dao;

import com.codecool.dungeoncrawl.model.InventoryModel;
import com.codecool.dungeoncrawl.model.PlayerModel;

import javax.sql.DataSource;
import java.sql.*;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;


public class InventoryDaoJdbc implements InventoryDao{

    private DataSource dataSource;
    private PlayerDao playerDao;

    public InventoryDaoJdbc(DataSource dataSource, PlayerDao playerDao) {
        this.dataSource = dataSource;
        this.playerDao = playerDao;
    }

    @Override
    public void add(InventoryModel inventoryModel) {
        try (Connection connection = dataSource.getConnection()) {
            System.out.println("add in");
            for (String key : inventoryModel.getInventoryItems().keySet()) {
                String sql = "INSERT INTO inventory(item, quantity, player_id) VALUES (?, ?, ?)";
                PreparedStatement statement = connection.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS);
                statement.setString(1, key);
                statement.setInt(2, inventoryModel.getInventoryItems().get(key));
                statement.setInt(3, inventoryModel.getPlayer().getId());
                statement.executeUpdate();
                System.out.println("add inventory " + key);
                ResultSet resultSet = statement.getGeneratedKeys();
                resultSet.next();
                inventoryModel.setId(resultSet.getInt(1));
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public void update(InventoryModel inventoryModel) {
        try (Connection connection = dataSource.getConnection()) {
            for (String key : inventoryModel.getInventoryItems().keySet()) {
                String sql = "UPDATE inventory SET item = ?, quantity = ?, player_id = ?";
                PreparedStatement statement = connection.prepareStatement(sql);
                statement.setString(1, key);
                statement.setInt(2, inventoryModel.getInventoryItems().get(key));
                statement.setInt(3, inventoryModel.getPlayer().getId());
                statement.executeUpdate();
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public InventoryModel get(int id) {
        try (Connection connection = dataSource.getConnection()) {
            String sql = "SELECT item, quantity, player_id FROM inventory WHERE id = ?";
            PreparedStatement statement = connection.prepareStatement(sql);
            statement.setInt(1, id);
            ResultSet resultSet = statement.executeQuery();
            if (!resultSet.next()) {
                return null;
            }
            String item = resultSet.getString(1);
            int quantity = resultSet.getInt(2);
            int player_id = resultSet.getInt(3);
            PlayerModel playerModel = playerDao.get(player_id);

            HashMap<String, Integer> inventoryFromDB = new HashMap<>();
            inventoryFromDB.put(item, quantity);

            InventoryModel inventoryModel = new InventoryModel(inventoryFromDB, playerModel);
            inventoryModel.setId(id);
            return inventoryModel;

        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public List<InventoryModel> getAll() {
        try (Connection connection = dataSource.getConnection()) {
            String sql = "SELECT id, item, quantity, player_id FROM inventory";
            ResultSet resultSet = connection.createStatement().executeQuery(sql);

            List<InventoryModel> inventoryModels = new ArrayList<>();
            while (resultSet.next()) {
                int id = resultSet.getInt(1);
                String item = resultSet.getString(2);
                int quantity = resultSet.getInt(3);
                int player_id = resultSet.getInt(4);

                // find PlayerModel with id == player_id
                PlayerModel playerModel = playerDao.get(player_id);

                HashMap<String, Integer> inventoryFromDB = new HashMap<>();
                inventoryFromDB.put(item, quantity);

                InventoryModel inventoryModel = new InventoryModel(inventoryFromDB, playerModel);
                inventoryModel.setId(id);
                inventoryModels.add(inventoryModel);
            }
            return inventoryModels;
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }
}
