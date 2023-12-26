package com.example.webappinitializer.util;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonObject;

import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;

public class JsonHelper {
    public static JsonObject openJsonFileAsJsonObject(File file) throws IOException {
        Gson gson = new Gson();
        JsonObject jsonObject;
        try (FileReader reader = new FileReader(file)) {
            jsonObject = gson.fromJson(reader, JsonObject.class);
        }
        return jsonObject;
    }

    public static void saveJsonObjectToFile(File file, JsonObject jsonObject) throws IOException {
        Gson prettyGson = new GsonBuilder().setPrettyPrinting().create();
        try (FileWriter writer = new FileWriter(file)) {
            prettyGson.toJson(jsonObject, writer);
        }
    }
}
