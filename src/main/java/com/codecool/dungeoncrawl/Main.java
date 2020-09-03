package com.codecool.dungeoncrawl;

import com.codecool.dungeoncrawl.logic.*;
import com.codecool.dungeoncrawl.util.Input;
import com.codecool.dungeoncrawl.util.Notification;

import javafx.application.Application;
import javafx.geometry.Insets;
import javafx.scene.Scene;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.input.KeyEvent;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.GridPane;
import javafx.scene.paint.Color;
import javafx.stage.Stage;

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
    Label playerName = new Label();
    TextField textField = new TextField();
    Button getItemButton = new Button("Take item");
    Stage primaryStage;
    BorderPane borderPane;
    String levelNumber = "1";
    boolean gameOver = false;

    public static void main(String[] args) {
        launch(args);
    }

    @Override
    public void start(Stage primaryStage) throws Exception {
        map.getPlayer().setName(Input.getInput());
        this.primaryStage = primaryStage;

        GridPane ui = new GridPane();
        ui.setPrefWidth(200);
        ui.setPadding(new Insets(10));

        ui.add(new Label("Health: "), 0, 0);
        ui.add(new Label("Inventory: "), 0, 1);
        ui.add(new Label("Player name: "), 0, 4);

        getItemButton.setVisible(false);

        ui.add(getItemButton, 0, 3);
        ui.add(healthLabel, 1, 0);
        ui.add(inventory, 0, 2);
        ui.add(playerName, 2, 4);
        ui.add(textField, 0, 6);

        borderPane = new BorderPane();

        borderPane.setCenter(canvas);
        borderPane.setRight(ui);

        Scene scene = new Scene(borderPane);
        primaryStage.setScene(scene);
        refresh();
        scene.addEventFilter(KeyEvent.KEY_PRESSED,this::onKeyPressed);

        primaryStage.setTitle("Dungeon Crawl");
        primaryStage.show();

        new Thread(this::permanentRefresh).start();
    }

    public void permanentRefresh(){
        while (true){
            try {
                refresh();
                Thread.sleep(500);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
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
        refresh();
        keyEvent.consume();
        nextLevel("/level2.txt");

        if (levelNumber.equals("1")){
            map.getPlayer().openDoor(map, 20, 19, CellType.OPENED_DOOR1);
            levelNumber = "2";
            System.out.println(levelNumber);
            System.out.println("DOOR 1");
        } else if (levelNumber.equals("2")){
            map.getPlayer().openDoor(map, 9, 1, CellType.OPENED_DOOR2);
            System.out.println(levelNumber);
            System.out.println("DOOR 2");
            levelNumber = "3";
        } else if (levelNumber.equals("3")){
            map.getPlayer().openDoor(map, 0, 19, CellType.OPENED_DOOR3);
            System.out.println(levelNumber);
            System.out.println("DOOR 3");
        }
//        refresh();
    }

    public void nextLevel(String level) {
        if (map.getPlayer().getCell().getX() == 20 && map.getPlayer().getCell().getY() == 18){
            if (map.getCell(20, 19).getType().equals(CellType.OPENED_DOOR1)) {
                String name = map.getPlayer().getName();
                map = MapLoader.loadMap(level);
                map.getPlayer().setName(name);
                canvas = new Canvas(
                        map.getWidth() * Tiles.TILE_WIDTH,
                        map.getHeight() * Tiles.TILE_WIDTH);
                borderPane.setCenter(canvas);
                primaryStage.sizeToScene();
                context = canvas.getGraphicsContext2D();
                refresh();
                System.out.println(map.getWidth());
                System.out.println(map.getHeight());
                notification.Notification("Level 2 now");
            }
        }
    }

    public void refresh() {
        if (!(map.getPlayer().checkIfAlive(map.getPlayer()))){
            gameOver = true;
        }
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
