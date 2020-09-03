package com.codecool.dungeoncrawl.logic;

public enum CellType {
    EMPTY("empty"),
    SWORD("sword"),
    KEY("key"),
    FLOOR("floor"),
    CLOSED_DOOR1("closed_door1"),
    CLOSED_DOOR2("closed_door2"),
    CLOSED_DOOR3("closed_door3"),
    OPENED_DOOR1("opened_door1"),
    OPENED_DOOR2("opened_door2"),
    OPENED_DOOR3("opened_door3"),
    WALL("wall"),
    SCORPION("scorpion"),
    GHOST("ghost"),
    HELMET("helmet"),
    RIVER("river"),
    RIVER_END("riverEnd"),
    RIVER_TO_RIGHT("riverToRight"),
    TREES("trees"),
    DRIED_TREES("dried_trees"),
    BRIDGE("bridge"),
    RAIL("rail"),
    WATER("water");

    private final String tileName;

    CellType(String tileName) {
        this.tileName = tileName;
    }

    public String getTileName() {
        return tileName;
    }
}
