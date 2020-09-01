package com.codecool.dungeoncrawl;

import com.codecool.dungeoncrawl.logic.Cell;
import com.codecool.dungeoncrawl.logic.CellType;
import com.codecool.dungeoncrawl.logic.GameMap;
import com.codecool.dungeoncrawl.logic.MapLoader;
import com.codecool.dungeoncrawl.logic.actors.Player;
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
    GameMap map = MapLoader.loadMap();
    Canvas canvas = new Canvas(
            map.getWidth() * Tiles.TILE_WIDTH,
            map.getHeight() * Tiles.TILE_WIDTH);
    GraphicsContext context = canvas.getGraphicsContext2D();
    Label healthLabel = new Label();
    Button getItem = new Button("Take item");

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
        getItem.setVisible(false);
        ui.add(getItem, 0, 2);
        ui.add(healthLabel, 1, 0);

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
                if (!(map.getPlayer().getCell().getNeighbor(0,-1).getTileName().equals("wall"))){
                    try {
                        map.getPlayer().getCell().getNeighbor(0, -1).getActor().getTileName();
                    } catch (Exception e){
                        map.getPlayer().move(0, -1);
                    }
                }
                refresh();
                break;
            case DOWN:
                if (!(map.getPlayer().getCell().getNeighbor(0,1).getTileName().equals("wall"))){
                    try {
                        map.getPlayer().getCell().getNeighbor(0, 1).getActor().getTileName();
                    } catch (Exception e){
                        map.getPlayer().move(0, 1);
                    }
                }
                refresh();
                break;
            case LEFT:
                if (!(map.getPlayer().getCell().getNeighbor(-1,0).getTileName().equals("wall"))){
                    try {
                        map.getPlayer().getCell().getNeighbor(-1, 0).getActor().getTileName();
                    } catch (Exception e){
                        map.getPlayer().move(-1, 0);
                    }
                }
                refresh();
                break;
            case RIGHT:
                if (!(map.getPlayer().getCell().getNeighbor(1,0).getTileName().equals("wall"))){
                    try {
                        map.getPlayer().getCell().getNeighbor(1, 0).getActor().getTileName();
                    } catch (Exception e){
                        map.getPlayer().move(1, 0);
                    }
                }
                refresh();
                break;
        }
        if (map.getPlayer().getCell().getType().equals(CellType.SWORD) || map.getPlayer().getCell().getType().equals(CellType.KEY)){
            getItem.setVisible(true);
            getItem.setOnAction(event -> {
                try {
                    map.getPlayer().pickUp(map.getPlayer().getCell().getType().toString());
                    map.getPlayer().getCell().setType(CellType.FLOOR);
                    getItem.setVisible(false);
                } catch (Exception e){
                    System.out.println("Error");
                }
            });
        } else {
            getItem.setVisible(false);
        }
        keyEvent.consume();
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
