package com.codecool.dungeoncrawl.util;

import com.codecool.dungeoncrawl.logic.GameMap;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.layout.VBox;
import javafx.stage.Modality;
import javafx.stage.Stage;


public class PlayerInput {


    public PlayerInput(GameMap map) {
        Stage popupWindow=new Stage();

        popupWindow.initModality(Modality.APPLICATION_MODAL);
        popupWindow.setTitle(null);Label label1= new Label("Enter player's name");

        TextField textField = new TextField("Player");
        textField.setMaxWidth(150);
        Button button1= new Button("Submit");
        button1.setOnAction(e -> {
            map.getPlayer().setName(textField.getText());
            popupWindow.close();
        });
        VBox layout= new VBox(10);
        layout.getChildren().addAll(label1,textField, button1);
        layout.setAlignment(Pos.CENTER);
        Scene scene1= new Scene(layout, 300, 250);
        popupWindow.setScene(scene1);
        popupWindow.showAndWait();
    }
}