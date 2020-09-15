package com.codecool.dungeoncrawl.dao;

import com.codecool.dungeoncrawl.model.GameState;
import com.codecool.dungeoncrawl.model.InventoryModel;

import java.util.List;

public interface InventoryDao {
    void add(InventoryModel inventoryModel);
    void update(InventoryModel inventoryModel);
    InventoryModel get(int id);
    List<InventoryModel> getAll();
}
