package com.codecool.dungeoncrawl.logic;

public enum CellType {
    EMPTY("empty"),
    SWORD("sword"),
    KEY("key"),
    KEY_2("key2"),
    KEY_3("key3"),
    KEY_4("key4"),
    FLOOR("floor"),
    WHITE_FLOOR("white_floor"),
    CLOSED_DOOR1("closed_door1"),
    CLOSED_DOOR2("closed_door2"),
    CLOSED_DOOR3("closed_door3"),
    CLOSED_DOOR4("closed_door4"),
    OPENED_DOOR1("opened_door1"),
    OPENED_DOOR2("opened_door2"),
    OPENED_DOOR3("opened_door3"),
    OPENED_DOOR4("opened_door4"),
    WALL("wall"),
    SCORPION("scorpion"),
    GHOST("ghost"),
    HELMET("helmet"),
    CHEST("chest"),
    RIVER("river"),
    RIVER_END("riverEnd"),
    RIVER_TO_RIGHT("riverToRight"),
    TREES("trees"),
    DRIED_TREES("dried_trees"),
    BRIDGE("bridge"),
    RAIL("rail"),
    SKELETON_HEAD("skeleton_head"),
    TRAP("trap"),
    APPLE("apple"),
    WATER("water");

    private final String tileName;

    CellType(String tileName) {
        this.tileName = tileName;
    }

    public String getTileName() {
        return tileName;
    }
}
