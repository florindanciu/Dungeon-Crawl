package com.codecool.dungeoncrawl;

import com.codecool.dungeoncrawl.logic.*;
import com.codecool.dungeoncrawl.logic.actors.Player;
import com.codecool.dungeoncrawl.util.HelloThread;
import com.codecool.dungeoncrawl.util.Notification;
import javafx.application.Application;
import javafx.geometry.Insets;
import javafx.scene.Scene;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.input.KeyEvent;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.GridPane;
import javafx.scene.paint.Color;
import javafx.stage.Stage;

import java.util.HashMap;

public class Main extends Application {
    Notification notification = new Notification();
    GameMap map = MapLoader.loadMap("/level1.txt");
    Canvas canvas = new Canvas(
            map.getWidth() * Tiles.TILE_WIDTH,
            map.getHeight() * Tiles.TILE_WIDTH);
    GraphicsContext context = canvas.getGraphicsContext2D();
    Combat combat = new Combat();
    Label healthLabel = new Label();
    Label inventory = new Label();
    Button getItemButton = new Button("Take item");

    public static void main(String[] args) {
        launch(args);
    }

    @Override
    public void start(Stage primaryStage) throws Exception {
        GridPane ui = new GridPane();
        ui.setPrefWidth(200);
        ui.setPadding(new Insets(10));

        ui.add(new Label("Health: "), 0, 0);
        ui.add(new Label("Inventory: "), 0, 1);
        getItemButton.setVisible(false);
        ui.add(getItemButton, 0, 3);
        ui.add(healthLabel, 1, 0);
        ui.add(inventory, 0, 2);

        BorderPane borderPane = new BorderPane();

        borderPane.setCenter(canvas);
        borderPane.setRight(ui);

        Scene scene = new Scene(borderPane);
        primaryStage.setScene(scene);
        refresh();
        scene.addEventFilter(KeyEvent.KEY_PRESSED,this::onKeyPressed);

        primaryStage.setTitle("Dungeon Crawl");
        primaryStage.show();
    }

    private void onKeyPressed(KeyEvent keyEvent) {
        switch (keyEvent.getCode()) {
            case UP:
                map.getPlayer().checkCell(map, combat,0,-1);
                refresh();
                break;
            case DOWN:
                map.getPlayer().checkCell(map, combat,0,1);
                refresh();
                break;
            case LEFT:
                map.getPlayer().checkCell(map, combat,-1,0);
                refresh();
                break;
            case RIGHT:
                map.getPlayer().checkCell(map, combat,1,0);
                refresh();
                break;
        }
        // FIX BUG
        map.getPlayer().checkCellForItem(map, getItemButton, inventory);
        refresh();
        map.getPlayer().openDoor(map);
        refresh();
        keyEvent.consume();
        nextLevel();
//        refresh();
    }

    public void nextLevel() {
        if (map.getPlayer().getCell().getX() == 20 && map.getPlayer().getCell().getY() == 18){
            if (map.getCell(20, 19).getType().equals(CellType.OPENED_DOOR)) {
                map = MapLoader.loadMap("/level2.txt");
                refresh();
                notification.Notification("Level 2 now");
            }
        }
    }

    private void refresh() {
        context.setFill(Color.BLACK);
        context.fillRect(0, 0, canvas.getWidth(), canvas.getHeight());
        for (int x = 0; x < map.getWidth(); x++) {
            for (int y = 0; y < map.getHeight(); y++) {
                Cell cell = map.getCell(x, y);
                if (cell.getActor() != null) {
                    Tiles.drawTile(context, cell.getActor(), x, y);
                }else {
                    Tiles.drawTile(context, cell, x, y);
                }
            }
        }
        healthLabel.setText("" + map.getPlayer().getHealth());
    }

}
