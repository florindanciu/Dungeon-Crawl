package com.codecool.dungeoncrawl.logic;

import com.codecool.dungeoncrawl.logic.actors.Actor;
import com.codecool.dungeoncrawl.logic.actors.Player;
import com.codecool.dungeoncrawl.logic.actors.Skeleton;

public class Combat {
    public static boolean done = false;

    public void fight(Player player, Actor enemy){
        if (player.checkIfAlive(player) && enemy.checkIfAlive(enemy)){
            if (player.getInventory().containsKey("SWORD")){
                player.attack(enemy,5,10);
                System.out.println("BIG DMG");
            } else {
                player.attack(enemy,0,10);
                System.out.println("small dmg");
            }
            enemy.attack(player,0,5);
        }
        if (!player.checkIfAlive(player)){
            System.out.println("GAME OVER!");
        }
    }

}
