package com.codecool.dungeoncrawl.util;

public class Random {

    private static final java.util.Random RANDOM = new java.util.Random();

    public static int getNumberBetween(int min, int max) {
        return RANDOM.nextInt(max-min) + min;
    }

}
