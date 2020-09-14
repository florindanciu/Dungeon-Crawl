package com.codecool.dungeoncrawl.util;
import javafx.geometry.Pos;
import javafx.util.Duration;
import org.controlsfx.control.Notifications;

public class FlashMessage {

    public static void display(String title, String content){
        Notifications notifications = Notifications.create()
                .title(title)
                .text(content)
                .position(Pos.CENTER)
                .hideAfter(Duration.millis(2500));
        notifications.show();
    }
}
