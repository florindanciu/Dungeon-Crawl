package com.codecool.dungeoncrawl.logic;

import com.codecool.dungeoncrawl.App;
import com.codecool.dungeoncrawl.logic.actors.Actor;
import com.codecool.dungeoncrawl.logic.actors.Player;
import com.codecool.dungeoncrawl.logic.actors.Skeleton;
import javafx.scene.control.Label;

public class Combat {

    public void fight(Player player, Actor enemy, Label label, Label label2){
        if (player.checkIfAlive(player) && enemy.checkIfAlive(enemy)){
            if (player.getInventory().containsKey("SWORD")){
                player.setDmg(15);
                player.attack(enemy,10,player.getDmg());
                label.setText("" + enemy.getHealth());
                label2.setText("" + player.getHealth());
            } else {
                player.attack(enemy,0,5);
                label.setText("" + enemy.getHealth());
                label.setText("" + player.getHealth());
            }
            enemy.attack(player,0,enemy.getDmg());
            System.out.println("Player health" + " " + player.getHealth());
        }
    }

}
