package com.example.webappinitializer.util;

import com.google.gson.GsonBuilder;
import javafx.stage.DirectoryChooser;

import java.io.*;

import com.google.gson.Gson;
import com.google.gson.JsonObject;

public class ProjectInitializer {
    public static void createReactApp(String appName, File directory) throws IOException, InterruptedException {
        runProcess(directory, "npx", "create-react-app", appName);
    }

    public static void updatePackageJsonDescription(File appDirectory, String description) throws IOException {
        File packageJson = new File(appDirectory, "package.json");

        Gson gson = new Gson();
        JsonObject packageJsonObject;
        try (FileReader reader = new FileReader(packageJson)) {
            packageJsonObject = gson.fromJson(reader, JsonObject.class);
        }

        packageJsonObject.addProperty("description", description);

        Gson prettyGson = new GsonBuilder().setPrettyPrinting().create();
        try (FileWriter writer = new FileWriter(packageJson)) {
            prettyGson.toJson(packageJsonObject, writer);
        }

    }

    public static void updatePublicIndexDescription(File appDirectory, String description) throws IOException {
        File indexHtml = new File(appDirectory, "public/index.html");
        try (BufferedReader reader = new BufferedReader(new FileReader(indexHtml))) {
            String line;
            StringBuilder builder = new StringBuilder();
            while ((line = reader.readLine()) != null) {
                if (line.contains("<meta name=\"description\"")) {
                    line = line.replace("content=\"\"", "content=\"" + description + "\"");
                }
                builder.append(line).append("\n");
            }
            try (PrintWriter writer = new PrintWriter(indexHtml)) {
                writer.println(builder);
            }
        }
    }

    public static void removeCommentsFromPublicIndex(File appDirectory) throws IOException {
        File indexHtml = new File(appDirectory, "public/index.html");
        try (BufferedReader reader = new BufferedReader(new FileReader(indexHtml))) {
            String line;
            StringBuilder builder = new StringBuilder();
            while ((line = reader.readLine()) != null) {
                if (line.contains("<!--")) {
                    line = line.replace("<!--", "");
                }
                if (line.contains("-->")) {
                    line = line.replace("-->", "");
                }
                builder.append(line).append("\n");
            }
            try (PrintWriter writer = new PrintWriter(indexHtml)) {
                writer.println(builder);
            }
        }
    }

    public static void installTailwind(File appDirectory) throws IOException, InterruptedException {
        runProcess(appDirectory, "npm", "install", "tailwindcss", "postcss", "autoprefixer");
        updateIndexCss(appDirectory);
    }

    private static void updateIndexCss(File appDirectory) throws FileNotFoundException {
        File indexCss = new File(appDirectory, "src/index.css");
        try (PrintWriter writer = new PrintWriter(indexCss)) {
            writer.println("@tailwind base;");
            writer.println("@tailwind components;");
            writer.println("@tailwind utilities;");
        }
    }

    private static void runProcess(File directory, String... commands) throws IOException, InterruptedException {
        ProcessBuilder builder = new ProcessBuilder("cmd", "/c", String.join(" ", commands));
        builder.directory(directory);
        builder.redirectOutput(ProcessBuilder.Redirect.INHERIT);
        builder.redirectError(ProcessBuilder.Redirect.INHERIT);
        Process process = builder.start();
        process.waitFor();
    }


    public static File selectDirectory() {
        DirectoryChooser directoryChooser = new DirectoryChooser();
        directoryChooser.setTitle("Select Directory");
        return directoryChooser.showDialog(null);
    }
}
