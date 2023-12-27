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

import static com.example.webappinitializer.util.JsonHelper.openJsonFileAsJsonObject;
import static com.example.webappinitializer.util.JsonHelper.saveJsonObjectToFile;

/**
 * ProjectInitializer
 * <p>
 *     This class is used to initialize a React project.
 *     <br>
 *     This class is used to update the <code>package.json</code>, <code>manifest.json</code>, and <code>index.html</code>
 *     files.
 *     <br>
 *     This class is used to install Tailwind CSS, Prettier, and Framer Motion.
 * </p>
 */
public class ProjectInitializer {

    /**
     * Create a React app.
     * <p>
     *     This method uses the <code>npx create-react-app</code> command to create a React app.
     * </p>
     * @param appName The name of the app.
     * @param directory The directory to create the app in.
     * @throws IOException If the app cannot be created.
     * @throws InterruptedException If the app cannot be created.
     */
    public static void createReactApp(String appName, File directory) throws IOException, InterruptedException {
        runProcess(directory, "npx", "create-react-app", appName);
    }

    /**
     * Update the <code>package.json</code> file.
     * <p>
     *     This method uses the {@link JsonHelper} class to open the <code>package.json</code> file as a
     *     {@link JsonObject} and update the <code>description</code> property.
     * </p>
     * @param appDirectory The app directory.
     * @param description The description.
     * @throws IOException If the file cannot be opened.
     */
    public static void updatePackageJsonDescription(File appDirectory, String description) throws IOException {
        File packageJson = new File(appDirectory, "package.json");
        JsonObject packageJsonObject = openJsonFileAsJsonObject(packageJson);
        packageJsonObject.addProperty("description", description);
        saveJsonObjectToFile(packageJson, packageJsonObject);
    }

    /**
     * Update the <code>manifest.json</code> file.
     * <p>
     *     This method uses the {@link JsonHelper} class to open the <code>manifest.json</code> file as a
     *     {@link JsonObject} and update the <code>short_name</code> and <code>name</code> properties.
     * </p>
     * @param appDirectory The app directory.
     * @param shortName The short name.
     * @param name The name.
     * @throws IOException If the file cannot be opened.
     */
    public static void updateManifestJsonName(File appDirectory, String shortName, String name) throws IOException {
        File manifestJson = new File(appDirectory, "public/manifest.json");
        JsonObject manifestJsonObject = openJsonFileAsJsonObject(manifestJson);
        manifestJsonObject.addProperty("short_name", shortName);
        manifestJsonObject.addProperty("name", name);
        saveJsonObjectToFile(manifestJson, manifestJsonObject);
    }

    /**
     * Update the <code>public/index.html</code> file.
     * <p>
     *     This method uses the {@link Jsoup} library to parse the <code>public/index.html</code> file and update the
     *     <code>title</code> tag.
     * </p>
     * @param appDirectory The app directory.
     * @param title The title.
     * @throws IOException If the file cannot be opened.
     */
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

    /**
     * Update the <code>public/index.html</code> file.
     * <p>
     *     This method uses the {@link Jsoup} library to parse the <code>public/index.html</code> file and update the
     *     <code>meta</code> tag.
     * </p>
     * @param appDirectory The app directory.
     * @param description The description.
     * @throws IOException If the file cannot be opened.
     */
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

    /**
     * Remove comments from the <code>public/index.html</code> file.
     * <p>
     *     This method uses the {@link Jsoup} library to parse the <code>public/index.html</code> file and remove all
     *     comments.
     * </p>
     * @param appDirectory The app directory.
     * @throws IOException If the file cannot be opened.
     */
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

    /**
     * Update the <code>README.md</code> file.
     * <p>
     *     This method uses the {@link PrintWriter} class to update the <code>README.md</code> file.
     * </p>
     * @param appDirectory The app directory.
     * @param appName The app name.
     * @param appDescription The app description.
     * @throws IOException If the file cannot be opened.
     */
    public static void updateReadme(File appDirectory, String appName, String appDescription) throws IOException {
        File readme = new File(appDirectory, "README.md");
        try (PrintWriter writer = new PrintWriter(readme)) {
            writer.println("# " + appName);
            writer.println(appDescription);
        }
    }

    /**
     * Install Tailwind CSS.
     * <p>
     *     This method uses the <code>npm install</code> command to install Tailwind CSS, PostCSS, and Autoprefixer.
     *     <br>
     *     This method uses the {@link #updateIndexCss(File)} method to update the <code>src/index.css</code> file.
     *     <br>
     *     This method uses the {@link #initializeTailwind(File)} method to initialize Tailwind CSS.
     * </p>
     * @param appDirectory The app directory.
     * @throws IOException If the app cannot be created.
     * @throws InterruptedException If the app cannot be created.
     */
    public static void installTailwind(File appDirectory) throws IOException, InterruptedException {
        runProcess(appDirectory, "npm", "install", "tailwindcss", "postcss", "autoprefixer");
        updateIndexCss(appDirectory);
        initializeTailwind(appDirectory);
    }

    /**
     * Install Prettier.
     * <p>
     *     This method uses the <code>npm install</code> command to install Prettier.
     * </p>
     * @param appDirectory The app directory.
     * @throws IOException If the app cannot be created.
     * @throws InterruptedException If the app cannot be created.
     */
    public static void installPrettier(File appDirectory) throws IOException, InterruptedException {
        runProcess(appDirectory, "npm", "install", "--save-dev", "prettier");
    }

    /**
     * Install Framer Motion.
     * <p>
     *     This method uses the <code>npm install</code> command to install Framer Motion.
     * </p>
     * @param appDirectory The app directory.
     * @throws IOException If the app cannot be created.
     * @throws InterruptedException If the app cannot be created.
     */
    public static void installFramerMotion(File appDirectory) throws IOException, InterruptedException {
        runProcess(appDirectory, "npm", "install", "framer-motion");
    }

    /**
     * Configure Prettier.
     * <p>
     *     This method uses the {@link Gson} library to save the Prettier configuration to a JSON file.
     * </p>
     * @param appDirectory The app directory.
     * @param options The Prettier options.
     * @throws IOException If the file cannot be saved.
     */
    public static void configurePrettier(File appDirectory, Map<String, Object> options) throws IOException {
        File prettierConfig = new File(appDirectory, ".prettierrc");
        Gson gson = new GsonBuilder().setPrettyPrinting().create();
        try (FileWriter writer = new FileWriter(prettierConfig)) {
            gson.toJson(options, writer);
        }
    }

    /**
     * Run Prettier.
     * <p>
     *     This method uses the <code>npx prettier --write .</code> command to run Prettier.
     * </p>
     * @param appDirectory The app directory.
     * @throws IOException If the app cannot be created.
     * @throws InterruptedException If the app cannot be created.
     */
    public static void runPrettier(File appDirectory) throws IOException, InterruptedException {
        runProcess(appDirectory, "npx", "prettier", "--write", ".");
    }

    /**
     * Update the <code>src/index.css</code> file.
     * <p>
     *     This method uses the {@link PrintWriter} class to update the <code>src/index.css</code> file.
     * </p>
     * @param appDirectory The app directory.
     * @throws FileNotFoundException If the file cannot be opened.
     */
    private static void updateIndexCss(File appDirectory) throws FileNotFoundException {
        File indexCss = new File(appDirectory, "src/index.css");
        try (PrintWriter writer = new PrintWriter(indexCss)) {
            writer.println("@tailwind base;");
            writer.println("@tailwind components;");
            writer.println("@tailwind utilities;");
        }
    }

    /**
     * Initialize Tailwind CSS.
     * <p>
     *     This method uses the <code>npx tailwindcss init</code> command to initialize Tailwind CSS.
     * </p>
     * @param appDirectory The app directory.
     * @throws IOException If the app cannot be created.
     * @throws InterruptedException If the app cannot be created.
     */
    private static void initializeTailwind(File appDirectory) throws IOException, InterruptedException {
        runProcess(appDirectory, "npx", "tailwindcss", "init");
    }

    /**
     * Run a process.
     * <p>
     *     This method uses the {@link ProcessBuilder} class to run a process.
     * </p>
     * @param directory The directory to run the process in.
     * @param commands The commands to run.
     * @throws IOException If the app cannot be created.
     * @throws InterruptedException If the app cannot be created.
     */
    private static void runProcess(File directory, String... commands) throws IOException, InterruptedException {
        ProcessBuilder builder = new ProcessBuilder("cmd", "/c", String.join(" ", commands));
        builder.directory(directory);
        builder.redirectOutput(ProcessBuilder.Redirect.INHERIT);
        builder.redirectError(ProcessBuilder.Redirect.INHERIT);
        Process process = builder.start();
        process.waitFor();
    }

    /**
     * Select a directory.
     * <p>
     *     This method uses the {@link DirectoryChooser} class to select a directory.
     * </p>
     * @return The selected directory.
     */
    public static File selectDirectory() {
        DirectoryChooser directoryChooser = new DirectoryChooser();
        directoryChooser.setTitle("Select Directory");
        return directoryChooser.showDialog(null);
    }
}
