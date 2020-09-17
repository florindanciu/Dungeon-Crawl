package com.codecool.dungeoncrawl;

import com.codecool.dungeoncrawl.logic.Drawable;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.image.Image;

import java.util.HashMap;
import java.util.Map;

public class Tiles {
    public static int TILE_WIDTH = 32;

    private static Image tileset = new Image("/tiles.png", 543 * 2, 543 * 2, true, false);
    private static Map<String, Tile> tileMap = new HashMap<>();
    public static class Tile {
        public final int x, y, w, h;
        Tile(int i, int j) {
            x = i * (TILE_WIDTH + 2);
            y = j * (TILE_WIDTH + 2);
            w = TILE_WIDTH;
            h = TILE_WIDTH;
        }
    }

    static {
        tileMap.put("empty", new Tile(0, 0));
        tileMap.put("river", new Tile(12, 5));
        tileMap.put("riverToRight", new Tile(13, 5));
        tileMap.put("riverEnd", new Tile(14, 5));
        tileMap.put("water", new Tile(8, 5));
        tileMap.put("trees", new Tile(3, 1));
        tileMap.put("wall", new Tile(10, 17));
        tileMap.put("floor", new Tile(2, 0));
        tileMap.put("white_floor", new Tile(1, 17));
        tileMap.put("player", new Tile(25, 0));
        tileMap.put("skeleton", new Tile(29, 6));
        tileMap.put("ghost", new Tile(27, 6));
        tileMap.put("closed_door1", new Tile(9, 11));
        tileMap.put("opened_door1", new Tile(8, 10));
        tileMap.put("closed_door2", new Tile(1, 9));
        tileMap.put("opened_door2", new Tile(2, 9));
        tileMap.put("closed_door3", new Tile(5, 9));
        tileMap.put("opened_door3", new Tile(6, 9));
        tileMap.put("closed_door4", new Tile(7, 10));
        tileMap.put("opened_door4", new Tile(12, 11));
        tileMap.put("key", new Tile(16, 23));
        tileMap.put("key2", new Tile(17, 23));
        tileMap.put("key3", new Tile(18, 23));
        tileMap.put("key4", new Tile(17, 25));
        tileMap.put("sword", new Tile(0, 29));
        tileMap.put("scorpion", new Tile(24, 5));
        tileMap.put("helmet", new Tile(3, 22));
        tileMap.put("chest", new Tile(3, 23));
        tileMap.put("rail", new Tile(21, 1));
        tileMap.put("bridge", new Tile(7, 5));
        tileMap.put("dried_trees", new Tile(6, 0));
        tileMap.put("skeleton_head", new Tile(18, 24));
        tileMap.put("trap", new Tile(13, 18));
        tileMap.put("apple", new Tile(15, 29));
    }

    public static void drawTile(GraphicsContext context, Drawable d, int x, int y) {
        Tile tile = tileMap.get(d.getTileName());
        context.drawImage(tileset, tile.x, tile.y, tile.w, tile.h,
                x * TILE_WIDTH, y * TILE_WIDTH, TILE_WIDTH, TILE_WIDTH);
    }

    public static void changePlayerLook(int x, int y) {
        tileMap.put("player", new Tile(x, y));
    }
}
