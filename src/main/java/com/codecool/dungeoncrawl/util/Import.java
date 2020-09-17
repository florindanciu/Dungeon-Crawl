package com.codecool.dungeoncrawl.util;

import com.codecool.dungeoncrawl.model.GameState;
import com.fasterxml.jackson.databind.ObjectMapper;

import java.io.File;
import java.io.IOException;

public class Import {

    public static GameState Import(String path) throws IOException {
        ObjectMapper objectMapper = new ObjectMapper();
        return objectMapper.readValue(new File(path), GameState.class);
    }

}
