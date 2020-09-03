package com.codecool.dungeoncrawl.util;

import javafx.scene.control.Alert;


public class Notification {
    public void Notification(String message){
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle(null);
        alert.setHeaderText(null);
        alert.setContentText(message);

        alert.showAndWait();

    }

}
