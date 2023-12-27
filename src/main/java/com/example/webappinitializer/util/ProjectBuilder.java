package com.example.webappinitializer.util;

import com.example.webappinitializer.alert.BuildProgressAlert;
import com.example.webappinitializer.config.ModuleConfiguration;
import com.example.webappinitializer.config.PrettierConfiguration;
import com.example.webappinitializer.config.ProjectConfiguration;

import javafx.concurrent.Task;

import java.io.File;
import java.util.HashMap;
import java.util.Map;

/**
 * ProjectBuilder
 * <p>
 *     This class is used to build a project based on the configuration.
 * </p>
 */
public class ProjectBuilder {

    /**
     * Build a project based on the configuration.
     * <p>
     *     This method uses the {@link ProjectInitializer} class to create a React app and update the files.
     *     <br>
     *     This method uses the {@link BuildProgressAlert} class to display a progress alert.
     * </p>
     * @param config The project configuration.
     * @param destinationDirectory The destination directory.
     */
    public static void build(ProjectConfiguration config, File destinationDirectory) {

        String directoryName = config.getDirectoryName();
        String shortName = config.getShortName();
        String fullName = config.getFullName();
        String description = config.getDescription();
        Map<Module, ModuleConfiguration> modules = config.getModules();

        BuildProgressAlert alert = new BuildProgressAlert();

        Task<Void> task = new Task<>() {
            @Override
            protected Void call() throws Exception {
                File appDirectory = new File(destinationDirectory, directoryName);
                ProjectInitializer.createReactApp(directoryName, destinationDirectory);
                ProjectInitializer.updatePackageJsonDescription(appDirectory, description);
                ProjectInitializer.updatePublicIndexDescription(appDirectory, description);
                ProjectInitializer.updatePublicIndexTitle(appDirectory, fullName);
                ProjectInitializer.removeCommentsFromPublicIndex(appDirectory);
                ProjectInitializer.updateReadme(appDirectory, fullName, description);
                ProjectInitializer.updateManifestJsonName(appDirectory, shortName, fullName);
                if (modules.containsKey(Module.TAILWIND_CSS)) {
                    ProjectInitializer.installTailwind(appDirectory);
                }
                if (modules.containsKey(Module.PRETTIER)) {
                    ProjectInitializer.installPrettier(appDirectory);

                    ModuleConfiguration moduleConfig = modules.get(Module.PRETTIER);
                    if (moduleConfig instanceof PrettierConfiguration prettierConfig) {

                        Map<String, Object> prettierOptions = new HashMap<>();
                        prettierOptions.put("semi", prettierConfig.isSemi());
                        prettierOptions.put("singleQuote", prettierConfig.isSingleQuote());
                        prettierOptions.put("tabWidth", prettierConfig.getTabWidth());
                        prettierOptions.put("printWidth", prettierConfig.getPrintWidth());

                        ProjectInitializer.configurePrettier(appDirectory, prettierOptions);
                        ProjectInitializer.runPrettier(appDirectory);
                    }
                }

                if (modules.containsKey(Module.FRAMER_MOTION)) {
                    ProjectInitializer.installFramerMotion(appDirectory);
                }
                return null;
            }
        };

        task.setOnSucceeded(event -> {
            alert.close();
            System.out.println("React app created successfully");
        });

        task.setOnFailed(event -> {
            Throwable exception = task.getException();
            System.out.println("Failed to create React app: " + exception.getMessage());
        });

        new Thread(task).start();
        alert.showAndWait();

    }
}
