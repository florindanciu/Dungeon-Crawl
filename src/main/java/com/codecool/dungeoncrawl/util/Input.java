package com.codecool.dungeoncrawl.util;

import java.util.Scanner;

public class Input {
    public static Scanner scanner = new Scanner(System.in);

    public static String  getInput(){
        String name = scanner.nextLine();
        return name;
    }

}
