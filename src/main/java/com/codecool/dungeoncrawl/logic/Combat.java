package com.codecool.dungeoncrawl.logic;

import com.codecool.dungeoncrawl.logic.actors.Actor;
import com.codecool.dungeoncrawl.logic.actors.Player;
import com.codecool.dungeoncrawl.logic.actors.Skeleton;

public class Combat {
    public static boolean done = false;

    public void fight(Player player, Actor enemy){
        if (player.checkIfAlive(player) && enemy.checkIfAlive(enemy)){
            if (player.getInventory().containsKey("SWORD")){
                player.setDmg(15);
                player.attack(enemy,5,player.getDmg());
                System.out.println("BIG DMG");
                System.out.println("Monster health" + " " + enemy.getHealth());
            } else {
                player.attack(enemy,0,5);
                System.out.println("small dmg");
                System.out.println("Monster health" + " " + enemy.getHealth());
            }
            enemy.attack(player,0,enemy.getDmg());
            System.out.println("Player health" + " " + player.getHealth());
        }
        if (!player.checkIfAlive(player)){
            System.out.println("GAME OVER!");
        }
    }

}
