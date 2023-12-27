package com.example.webappinitializer.util;

import com.example.webappinitializer.config.PrettierConfiguration;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonObject;

import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;

/**
 * Helper class for JSON operations.
 * <p>
 *     This class contains methods for opening a JSON file as a {@link JsonObject} and saving a {@link JsonObject} to
 *     a JSON file.
 *     <br>
 *     This class is used by {@link ProjectInitializer} to update the <code>package.json</code> and
 *     <code>manifest.json</code> files.
 *     <br>
 *     This class is used by {@link PrettierConfiguration} to save the Prettier configuration to a JSON file.
 *     <br>
 *</p>
 */
public class JsonHelper {
    /**
     * Opens a JSON file as a {@link JsonObject}.
     * <p>
     *     This method uses the {@link Gson} library to open a JSON file as a {@link JsonObject}.
     * </p>
     * @param file The JSON file to open.
     * @return The JSON file as a {@link JsonObject}.
     * @throws IOException If the file cannot be opened.
     */
    public static JsonObject openJsonFileAsJsonObject(File file) throws IOException {
        Gson gson = new Gson();
        JsonObject jsonObject;
        try (FileReader reader = new FileReader(file)) {
            jsonObject = gson.fromJson(reader, JsonObject.class);
        }
        return jsonObject;
    }

    /**
     * Saves a {@link JsonObject} to a JSON file.
     * <p>
     *     This method uses the {@link Gson} library to save a {@link JsonObject} to a JSON file.
     * </p>
     * @param file The JSON file to save to.
     * @param jsonObject The {@link JsonObject} to save.
     * @throws IOException If the file cannot be saved.
     */
    public static void saveJsonObjectToFile(File file, JsonObject jsonObject) throws IOException {
        Gson prettyGson = new GsonBuilder().setPrettyPrinting().create();
        try (FileWriter writer = new FileWriter(file)) {
            prettyGson.toJson(jsonObject, writer);
        }
    }
}
