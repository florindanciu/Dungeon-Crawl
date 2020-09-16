package com.codecool.dungeoncrawl;

import com.codecool.dungeoncrawl.dao.PlayerDaoJdbc;
import com.codecool.dungeoncrawl.logic.*;
import com.codecool.dungeoncrawl.model.GameState;
import com.codecool.dungeoncrawl.model.PlayerModel;
import com.codecool.dungeoncrawl.util.Input;
import com.codecool.dungeoncrawl.model.InventoryModel;
import com.codecool.dungeoncrawl.model.PlayerModel;
import com.codecool.dungeoncrawl.util.Modal;
import com.codecool.dungeoncrawl.util.Notification;

import com.codecool.dungeoncrawl.util.PopUp;
import com.codecool.dungeoncrawl.dao.GameDatabaseManager;
import com.codecool.dungeoncrawl.logic.Cell;
import com.codecool.dungeoncrawl.logic.GameMap;
import com.codecool.dungeoncrawl.logic.MapLoader;
import javafx.application.Application;
import javafx.geometry.Insets;
import javafx.geometry.Rectangle2D;
import javafx.scene.Scene;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyCodeCombination;
import javafx.scene.input.KeyCombination;
import javafx.scene.input.KeyEvent;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.GridPane;
import javafx.scene.paint.Color;
import javafx.stage.Screen;
import javafx.stage.Stage;

import java.sql.Date;
import java.sql.SQLException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class Main extends Application {
    GameDatabaseManager dbManager;
    GameMap map = MapLoader.loadMap("/level1.txt");
    Canvas canvas = new Canvas(
            map.getWidth() * Tiles.TILE_WIDTH,
            map.getHeight() * Tiles.TILE_WIDTH);
    GraphicsContext context = canvas.getGraphicsContext2D();
    Combat combat = new Combat();
    Label healthLabel = new Label();
    Label monsterHp = new Label();
    Label inventory = new Label();
    Label playerName = new Label();
    TextField textField = new TextField();
    Button getItemButton = new Button("Take item");
    Stage primaryStage;
    BorderPane borderPane;
    String levelNumber = "1";
    boolean gameOver = false;
    boolean resize = false;

    public static void main(String[] args) {
        launch(args);
    }

    @Override
    public void start(Stage primaryStage) {
        setupDbManager();
        map.getPlayer().setHealth(10);
        new Notification(map);
        textField.setOnAction(event -> map.getPlayer().setName(textField.getText()));
        this.primaryStage = primaryStage;

        GridPane ui = new GridPane();
        ui.setPrefWidth(300);
        ui.setPadding(new Insets(10));

        ui.add(new Label("Health: "), 0, 0);
        ui.add(new Label("Inventory: "), 0, 3);
        ui.add(new Label("Player name: " + map.getPlayer().getName()), 0, 5);
        ui.add(new Label("Monster health: "), 0, 6);

        getItemButton.setVisible(false);

        ui.add(getItemButton, 0, 3);
        ui.add(healthLabel, 0, 2);
        ui.add(inventory, 0, 4);
        ui.add(playerName, 2, 5);
        ui.add(monsterHp, 2, 6);

        borderPane = new BorderPane();

        borderPane.setCenter(canvas);
        borderPane.setRight(ui);

        Scene scene = new Scene(borderPane);
        primaryStage.setScene(scene);
        refresh();
        scene.addEventFilter(KeyEvent.KEY_PRESSED, this::onKeyPressed);

        scene.setOnKeyPressed(this::onKeyPressed);
        scene.setOnKeyReleased(this::onKeyReleased);
        primaryStage.setTitle("Dungeon Crawl");
        primaryStage.show();
        new Thread(this::permanentRefresh).start();



    }

    private void onKeyReleased(KeyEvent keyEvent) {
        KeyCombination exitCombinationMac = new KeyCodeCombination(KeyCode.W, KeyCombination.SHORTCUT_DOWN);
        KeyCombination exitCombinationWin = new KeyCodeCombination(KeyCode.F4, KeyCombination.ALT_DOWN);
        KeyCombination modalCombinationWin = new KeyCodeCombination(KeyCode.S, KeyCombination.CONTROL_ANY);
        if (exitCombinationMac.match(keyEvent)
                || exitCombinationWin.match(keyEvent)
                || keyEvent.getCode() == KeyCode.ESCAPE) {
            exit();

        } else if (modalCombinationWin.match(keyEvent)) {
            long now = System.currentTimeMillis();
            Date sqlDate = new Date(now);
            PlayerModel playerModel = new PlayerModel(map.getPlayer());
            GameState gameState = new GameState("map level " + levelNumber, sqlDate, playerModel);
            InventoryModel inventoryModel = new InventoryModel(map.getPlayer().getInventory(), playerModel);
            new Modal(map.getPlayer(), dbManager, gameState, inventoryModel);
        }
    }

    public void permanentRefresh() {
        while (true) {
            try {
                refreshMovingMap();
                Thread.sleep(500);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }

    private void onKeyPressed(KeyEvent keyEvent) {
        switch (keyEvent.getCode()) {
            case UP:
                map.getPlayer().checkCell(map, combat, 0, -1, monsterHp);
                refresh();
                break;
            case DOWN:
                map.getPlayer().checkCell(map, combat, 0, 1, monsterHp);
                refresh();
                break;
            case LEFT:
                map.getPlayer().checkCell(map, combat, -1, 0, monsterHp);
                refresh();
                break;
            case RIGHT:
                map.getPlayer().checkCell(map, combat, 1, 0, monsterHp);
                refresh();
                break;
        }
        map.getPlayer().checkCellForItem(map, getItemButton, inventory);
        keyEvent.consume();
        nextLevel("/level2.txt");

        if (map.getPlayer().getInventory().containsKey("KEY") && levelNumber.equals("1")) {
            PopUp.display("Good job!", "DOOR 1 OPENED !", "Green");
            map.getPlayer().openDoor(map, 20, 19, CellType.OPENED_DOOR1, "KEY");
            map.getPlayer().removeItem("KEY");
            System.out.println(levelNumber);
        } else if (map.getPlayer().getInventory().containsKey("KEY_2") && levelNumber.equals("2")) {
            PopUp.display("Good job!", "DOOR 2 OPENED !", "Green");
            map.getPlayer().openDoor(map, 9, 1, CellType.OPENED_DOOR2, "KEY_2");
            map.getPlayer().removeItem("KEY_2");
            System.out.println(map.getPlayer().getInventory());
            System.out.println(levelNumber);
        } else if (map.getPlayer().getInventory().containsKey("KEY_3") && levelNumber.equals("2")) {
            PopUp.display("Good job!", "DOOR 3 OPENED !", "Green");
            map.getPlayer().openDoor(map, 0, 19, CellType.OPENED_DOOR3, "KEY_3");
            map.getPlayer().removeItem("KEY_3");
            System.out.println(map.getPlayer().getInventory());
            System.out.println(levelNumber);
        }
    }

    public void gameEnd() {
        if (map.getPlayer().getCell().getX() == 0 && map.getPlayer().getCell().getY() == 19) {
            if (map.getCell(0, 19).getType().equals(CellType.OPENED_DOOR3)) {
                System.out.println("Test");
                PopUp.display("Dungeon Crawl", "YOU WON!", "Green");
            }
            levelNumber = "1";
            gameOver = true;
            map = MapLoader.loadMap("/level1.txt");
            canvas = new Canvas(
                    map.getWidth() * Tiles.TILE_WIDTH,
                    map.getHeight() * Tiles.TILE_WIDTH);
            borderPane.setCenter(canvas);
            primaryStage.sizeToScene();
            context = canvas.getGraphicsContext2D();
            map.getPlayer().setHealth(10);
            map.getPlayer().setName(textField.getText());
            inventory.setText("");
            Tiles.changePlayerLook(25, 0);
            refresh();
            gameOver = false;
        }
    }

    public void nextLevel(String level) {
        if (map.getPlayer().getCell().getX() == 20 && map.getPlayer().getCell().getY() == 18) {
            if (map.getCell(20, 19).getType().equals(CellType.OPENED_DOOR1)) {
                String name = map.getPlayer().getName();
                map = MapLoader.loadMap(level);
                map.getPlayer().setName(name);
                canvas = new Canvas(
                        600,700);
                borderPane.setCenter(canvas);
                primaryStage.sizeToScene();
                context = canvas.getGraphicsContext2D();
                resize = true;
                refreshMovingMap();
                levelNumber = "2";
                saveInDb();
            }
        }
    }

    public void saveInDb(){
        Date date = new Date(2020,10,10);
        PlayerModel playerModel = new PlayerModel(map.getPlayer().getName(), map.getPlayer().getX(),map.getPlayer().getY());
        GameState gameState = new GameState("Map 1", date, playerModel);
        GameDatabaseManager gameDatabaseManager = new GameDatabaseManager();
        gameDatabaseManager.savePlayer(map.getPlayer());
    }

    public List<Double> calculateMapSize(){
        Double x = canvas.getWidth() / Tiles.TILE_WIDTH * 2;
        Double y = x * Tiles.TILE_WIDTH * 2;
        Double z = y - Tiles.TILE_WIDTH;
        Double result = z / Tiles.TILE_WIDTH;
        System.out.println(z);
        System.out.println(result);

        Double left = result - x;
        Double right = result + x;

        List<Double> finalResult = List.of(left,right,z);
        return finalResult;
    }

    private void refreshMovingMap() {

        Rectangle2D bounds= Screen.getPrimary().getVisualBounds();
        int avWidth=(int)(bounds.getWidth()/1.5);
        int avHeight=(int)(bounds.getHeight());
        int canvasWidth=0;
        int canvasHeight=0;


        int startX=0, endX=0, startY=0, endY=0;
        if (avWidth<=map.getWidth()*Tiles.TILE_WIDTH){

            canvasWidth = avWidth - avWidth % (2 * Tiles.TILE_WIDTH);
            canvasWidth = (canvasWidth + Tiles.TILE_WIDTH <= avWidth) ? canvasWidth + Tiles.TILE_WIDTH : canvasWidth - Tiles.TILE_WIDTH;
            int widthCells = canvasWidth / Tiles.TILE_WIDTH;
            int beforeWidth = widthCells / 2;
            int afterWidth = beforeWidth;
            int playerX = map.getPlayer().getX();
            if (beforeWidth > playerX) {
                beforeWidth = playerX;
                afterWidth += afterWidth - beforeWidth;
            } else if (afterWidth > map.getWidth() - 1 - playerX) {

                afterWidth = map.getWidth() - 1 - playerX;
                beforeWidth += beforeWidth - afterWidth;

            }
            startX=playerX-beforeWidth;
            endX=playerX+afterWidth;

        }else{
            canvasWidth=map.getWidth()*Tiles.TILE_WIDTH;
            startX=0;
            endX=map.getWidth()-1;

        }

        if (avHeight<=map.getHeight()*Tiles.TILE_WIDTH) {

            canvasHeight = avHeight - avHeight % (2 * Tiles.TILE_WIDTH);
            canvasHeight = (canvasHeight + Tiles.TILE_WIDTH <= avHeight) ? canvasHeight + Tiles.TILE_WIDTH : canvasHeight - Tiles.TILE_WIDTH;

            int beforeHeight = canvasHeight / Tiles.TILE_WIDTH / 2;
            int afterHeight = beforeHeight;
            int playerY = map.getPlayer().getY();
            if (beforeHeight > playerY) {
                beforeHeight = playerY;
                afterHeight += afterHeight - beforeHeight;
            } else if (afterHeight > map.getHeight() - 1 - playerY) {
                afterHeight = map.getHeight() - 1 - playerY;
                beforeHeight += beforeHeight - afterHeight;
            }
            startY=playerY-beforeHeight;
            endY=playerY+afterHeight;

        } else{
            canvasHeight=map.getHeight()*Tiles.TILE_WIDTH;
            startY=0;
            endY=map.getHeight()-1;

        }

        if (resize) {
            canvas = new Canvas(canvasWidth, canvasHeight);
            context = canvas.getGraphicsContext2D();
            borderPane.setCenter(canvas);
            primaryStage.sizeToScene();
            primaryStage.centerOnScreen();
            resize=false;
        }
        //System.out.println(playerX+" "+playerY);
        for (int x = startX,i=0; x <= endX; x++,i++) {
            for (int y =startY,j=0; y <= endY; y++,j++) {
                //System.out.println("Drawing: "+x+" "+y);
                Cell cell = map.getCell(x, y);
                if (cell.getActor() != null) {
                    Tiles.drawTile(context, cell.getActor(), i, j);
                } else {
                    Tiles.drawTile(context, cell, i, j);
                }
            }
        }


        //healthLabel.setText("" + map.getPlayer().getHealth());
    }

    public void refresh() {
        if (!(map.getPlayer().checkIfAlive(map.getPlayer()))){
            levelNumber = "1";
            PopUp.display("YOU LOST", "GAME OVER!", "Red");
            map = MapLoader.loadMap("/level1.txt");
            canvas = new Canvas(
                    map.getWidth() * Tiles.TILE_WIDTH,
                    map.getHeight() * Tiles.TILE_WIDTH);
            borderPane.setCenter(canvas);
            primaryStage.sizeToScene();
            context = canvas.getGraphicsContext2D();
            map.getPlayer().setHealth(10);
            map.getPlayer().setName(textField.getText());
            inventory.setText("");
            Tiles.changePlayerLook(25,0);
//            refresh();
        }
        gameEnd();
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
        healthLabel.setText(" " + map.getPlayer().getHealth());
    }

    private void setupDbManager() {
        dbManager = new GameDatabaseManager();
        try {
            dbManager.setup();
        } catch (SQLException ex) {
            System.out.println("Cannot connect to database.");
        }
    }

    private void exit() {
        try {
            stop();
        } catch (Exception e) {
            System.exit(1);
        }
        System.exit(0);
    }
}
