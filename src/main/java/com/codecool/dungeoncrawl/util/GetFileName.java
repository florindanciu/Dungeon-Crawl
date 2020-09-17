package com.codecool.dungeoncrawl.util;

import com.codecool.dungeoncrawl.dao.GameDatabaseManager;
import com.codecool.dungeoncrawl.dao.PlayerDao;
import com.codecool.dungeoncrawl.logic.GameMap;
import com.codecool.dungeoncrawl.model.GameState;
import com.codecool.dungeoncrawl.model.PlayerModel;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.layout.VBox;
import javafx.stage.Modality;
import javafx.stage.Stage;

import java.io.IOException;
import java.util.List;

public class GetFileName {

    public static String getFileName() {
        Stage popupWindow=new Stage();

        popupWindow.initModality(Modality.APPLICATION_MODAL);
        popupWindow.setTitle(null);

        Label label2 = new Label("Enter file name");
        TextField fileName = new TextField();

        Button button1= new Button("Submit");
        button1.setOnAction(e -> {
            popupWindow.close();
        });
        VBox layout= new VBox(10);
        layout.getChildren().addAll(label2,fileName, button1);
        layout.setAlignment(Pos.CENTER);
        Scene scene1= new Scene(layout, 300, 250);
        popupWindow.setScene(scene1);
        popupWindow.showAndWait();
        return fileName.getText();
    }

}
