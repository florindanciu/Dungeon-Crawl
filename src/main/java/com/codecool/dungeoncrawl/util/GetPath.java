package com.codecool.dungeoncrawl.util;

import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.layout.VBox;
import javafx.stage.Modality;
import javafx.stage.Stage;

import java.io.IOException;

public class GetPath {

    public static String getPath(){
        Stage popupWindow=new Stage();

        popupWindow.initModality(Modality.APPLICATION_MODAL);
        popupWindow.setTitle(null);
        Label label1= new Label("Enter path");

        TextField path = new TextField();
        path.setMaxWidth(150);


        Button button1= new Button("Submit");
        button1.setOnAction(e -> {
            popupWindow.close();
        });

        VBox layout= new VBox(10);
        layout.getChildren().addAll(label1,path, button1);
        layout.setAlignment(Pos.CENTER);
        Scene scene1= new Scene(layout, 300, 250);
        popupWindow.setScene(scene1);
        popupWindow.showAndWait();

        return path.getText();
    }

}
