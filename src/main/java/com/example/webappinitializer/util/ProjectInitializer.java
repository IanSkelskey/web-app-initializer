package com.example.webappinitializer.util;

import com.google.gson.GsonBuilder;
import javafx.stage.DirectoryChooser;

import java.io.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import com.google.gson.Gson;
import com.google.gson.JsonObject;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Comment;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.nodes.Node;
import org.jsoup.select.NodeVisitor;

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

    public static void updateManifestJsonName(File appDirectory, String shortName, String name) throws IOException {
        File manifestJson = new File(appDirectory, "public/manifest.json");

        Gson gson = new Gson();
        JsonObject manifestJsonObject;
        try (FileReader reader = new FileReader(manifestJson)) {
            manifestJsonObject = gson.fromJson(reader, JsonObject.class);
        }

        manifestJsonObject.addProperty("short_name", shortName);
        manifestJsonObject.addProperty("name", name);

        Gson prettyGson = new GsonBuilder().setPrettyPrinting().create();
        try (FileWriter writer = new FileWriter(manifestJson)) {
            prettyGson.toJson(manifestJsonObject, writer);
        }

    }

    public static void updatePublicIndexTitle(File appDirectory, String title) throws IOException {
        File indexHtml = new File(appDirectory, "public/index.html");

        Document doc = Jsoup.parse(indexHtml, "UTF-8");
        Element titleTag = doc.selectFirst("title");

        if (titleTag != null) {
            titleTag.text(title);
        } else {
            titleTag = doc.createElement("title");
            titleTag.text(title);
            doc.head().appendChild(titleTag);
        }

        try (FileWriter writer = new FileWriter(indexHtml)) {
            writer.write(doc.toString());
        }
    }

    public static void updatePublicIndexDescription(File appDirectory, String description) throws IOException {
        File indexHtml = new File(appDirectory, "public/index.html");

        Document doc = Jsoup.parse(indexHtml, "UTF-8");
        Element metaTag = doc.selectFirst("meta[name=description]");

        if (metaTag != null) {
            metaTag.attr("content", description);
        } else {
            metaTag = doc.createElement("meta");
            metaTag.attr("name", "description");
            metaTag.attr("content", description);
            doc.head().appendChild(metaTag);
        }

        try (FileWriter writer = new FileWriter(indexHtml)) {
            writer.write(doc.toString());
        }
    }

    public static void removeCommentsFromPublicIndex(File appDirectory) throws IOException {
        File indexHtml = new File(appDirectory, "public/index.html");
        Document doc = Jsoup.parse(indexHtml, "UTF-8");
        List<Node> toRemove = new ArrayList<>();

        doc.traverse(new NodeVisitor() {
            @Override
            public void head(Node node, int i) {
                if (node instanceof Comment) {
                    toRemove.add(node);
                }
            }

            @Override
            public void tail(Node node, int i) {

            }
        });

        for (Node node : toRemove) {
            node.remove();
        }

        try (FileWriter writer = new FileWriter(indexHtml)) {
            writer.write(doc.toString());
        }
    }

    public static void updateReadme(File appDirectory, String appName, String appDescription) throws IOException {
        File readme = new File(appDirectory, "README.md");
        try (PrintWriter writer = new PrintWriter(readme)) {
            writer.println("# " + appName);
            writer.println(appDescription);
        }
    }

    public static void installTailwind(File appDirectory) throws IOException, InterruptedException {
        runProcess(appDirectory, "npm", "install", "tailwindcss", "postcss", "autoprefixer");
        updateIndexCss(appDirectory);
        initializeTailwind(appDirectory);
    }

    public static void installPrettier(File appDirectory) throws IOException, InterruptedException {
        runProcess(appDirectory, "npm", "install", "--save-dev", "prettier");
    }

    public static void installFramerMotion(File appDirectory) throws IOException, InterruptedException {
        runProcess(appDirectory, "npm", "install", "framer-motion");
    }

    public static void configurePrettier(File appDirectory, Map<String, Object> options) throws IOException {
        File prettierConfig = new File(appDirectory, ".prettierrc");
        Gson gson = new GsonBuilder().setPrettyPrinting().create();
        try (FileWriter writer = new FileWriter(prettierConfig)) {
            gson.toJson(options, writer);
        }
    }

    public static void runPrettier(File appDirectory) throws IOException, InterruptedException {
        runProcess(appDirectory, "npx", "prettier", "--write", ".");
    }

    private static void updateIndexCss(File appDirectory) throws FileNotFoundException {
        File indexCss = new File(appDirectory, "src/index.css");
        try (PrintWriter writer = new PrintWriter(indexCss)) {
            writer.println("@tailwind base;");
            writer.println("@tailwind components;");
            writer.println("@tailwind utilities;");
        }
    }

    private static void initializeTailwind(File appDirectory) throws IOException, InterruptedException {
        runProcess(appDirectory, "npx", "tailwindcss", "init");
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
