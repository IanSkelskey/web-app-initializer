package com.example.webappinitializer.util;

import javafx.stage.DirectoryChooser;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.PrintWriter;

public class ProjectInitializer {
    public static void createReactApp(String appName, File directory) throws IOException, InterruptedException {
        runProcess(directory, "npx", "create-react-app", appName);
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
        ProcessBuilder builder = new ProcessBuilder(commands);
        builder.directory(directory);
        Process process = builder.start();
        process.waitFor();
    }

    public static File selectDirectory() {
        DirectoryChooser directoryChooser = new DirectoryChooser();
        directoryChooser.setTitle("Select Directory");
        return directoryChooser.showDialog(null);
    }
}
