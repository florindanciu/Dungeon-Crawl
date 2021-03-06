package com.codecool.dungeoncrawl.util;

import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Paint;
import javafx.scene.text.Font;
import javafx.stage.Modality;
import javafx.stage.Stage;

public class PopUp {

    public static void display(String title, String message, String color) {
        Stage window = new Stage();

        window.initModality(Modality.WINDOW_MODAL);
        window.setTitle(title);
        window.setMinWidth(50);
        window.setMinHeight(50);

        Label label = new Label();
        label.setText(message);

        VBox layout = new VBox();
        layout.getChildren().addAll(label);
        label.setAlignment(Pos.CENTER);
        label.setTextFill(Paint.valueOf(color));
        label.setMinHeight(50);
        label.setMinWidth(100);
        label.setFont(new Font(50.0));

        Scene scene = new Scene(layout);
        window.setScene(scene);
        window.showAndWait();

    }
}



