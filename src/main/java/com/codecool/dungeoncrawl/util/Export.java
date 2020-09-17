package com.codecool.dungeoncrawl.util;

import com.codecool.dungeoncrawl.model.GameState;
import com.fasterxml.jackson.databind.ObjectMapper;

import java.io.File;
import java.io.IOException;


public class Export {

    public static void serialize(GameState gameState, String path, String fileName) throws IOException {
        ObjectMapper objectMapper = new ObjectMapper();
        objectMapper.writeValue(new File(path + fileName), gameState);
    }

}
