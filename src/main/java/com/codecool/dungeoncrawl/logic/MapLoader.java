package com.codecool.dungeoncrawl.logic;

import com.codecool.dungeoncrawl.logic.actors.Ghost;
import com.codecool.dungeoncrawl.logic.actors.Player;
import com.codecool.dungeoncrawl.logic.actors.Skeleton;
import com.codecool.dungeoncrawl.logic.actors.Scorpion;

import java.io.InputStream;
import java.util.Scanner;

public class MapLoader {
    public static GameMap loadMap(String level) {
        InputStream is = MapLoader.class.getResourceAsStream(level);
        Scanner scanner = new Scanner(is);
        int width = scanner.nextInt();
        int height = scanner.nextInt();

        scanner.nextLine(); // empty line

        GameMap map = new GameMap(width, height, CellType.EMPTY);
        for (int y = 0; y < height; y++) {
            String line = scanner.nextLine();
            for (int x = 0; x < width; x++) {
                if (x < line.length()) {
                    Cell cell = map.getCell(x, y);
                    switch (line.charAt(x)) {
                        case ' ':
                            cell.setType(CellType.TREES);
                            break;
                        case 'o':
                            cell.setType(CellType.DRIED_TREES);
                            break;
                        case '#':
                            cell.setType(CellType.WALL);
                            break;
                        case '.':
                            cell.setType(CellType.FLOOR);
                            break;
                        case 'b':
                            cell.setType(CellType.BRIDGE);
                            break;
                        case 'l':
                            cell.setType(CellType.RAIL);
                            break;
                        case 's':
                            cell.setType(CellType.FLOOR);
                            new Skeleton(cell);
                            break;
                        case '1':
                            cell.setType(CellType.CLOSED_DOOR1);
                            break;
                        case '2':
                            cell.setType(CellType.CLOSED_DOOR2);
                            break;
                        case '3':
                            cell.setType(CellType.CLOSED_DOOR3);
                            break;
                        case 'k':
                            cell.setType(CellType.KEY);
                            break;
                        case '*':
                            cell.setType(CellType.KEY_2);
                            break;
                        case '&':
                            cell.setType(CellType.KEY_3);
                            break;
                        case 'z':
                            cell.setType(CellType.SWORD);
                            break;
                        case 'S':
                            cell.setType(CellType.FLOOR);
                            new Scorpion(cell);
                            break;
                        case 'h':
                            cell.setType(CellType.HELMET);
                            break;
                        case 'C':
                            cell.setType(CellType.CHEST);
                            break;
                        case 'r':
                            cell.setType(CellType.RIVER);
                            break;
                        case 'q':
                            cell.setType(CellType.RIVER_END);
                            break;
                        case 'c':
                            cell.setType(CellType.RIVER_TO_RIGHT);
                            break;
                        case 'g':
                            cell.setType(CellType.FLOOR);
                            new Ghost(cell);
                            break;
                        case 'w':
                            cell.setType(CellType.WATER);
                            break;
                        case '@':
                            cell.setType(CellType.FLOOR);
                            map.setPlayer(new Player(cell));
                            map.getPlayer().setName(map.getPlayer().getName());
                            break;
                        default:
                            throw new RuntimeException("Unrecognized character: '" + line.charAt(x) + "'");
                    }
                }
            }
        }
        return map;
    }

}
