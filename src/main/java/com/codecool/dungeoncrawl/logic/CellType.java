package com.codecool.dungeoncrawl.logic;

public enum CellType {
    EMPTY("empty"),
    SWORD("sword"),
    KEY("key"),
    FLOOR("floor"),
    CLOSED_DOOR("closed_door"),
    OPENED_DOOR("opened_door"),
    WALL("wall"),
    SCORPION("scorpion"),
    GHOST("ghost"),
    HELMET("helmet"),
    RIVER("river"),
    RIVER_TO_RIGHT("riverToRight"),
    TREES("trees"),
    WATER("water");

    private final String tileName;

    CellType(String tileName) {
        this.tileName = tileName;
    }

    public String getTileName() {
        return tileName;
    }
}
