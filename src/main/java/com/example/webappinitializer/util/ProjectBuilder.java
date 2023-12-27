package com.example.webappinitializer.util;

import com.example.webappinitializer.alert.BuildProgressAlert;
import com.example.webappinitializer.config.ModuleConfiguration;
import com.example.webappinitializer.config.PrettierConfiguration;
import com.example.webappinitializer.config.ProjectConfiguration;

import javafx.concurrent.Task;

import java.io.File;
import java.util.HashMap;
import java.util.Map;

public class ProjectBuilder {
    public static void build(ProjectConfiguration config, File destinationDirectory) {

        String directoryName = config.getDirectoryName();
        String shortName = config.getShortName();
        String fullName = config.getFullName();
        String description = config.getDescription();
        Map<Modules, ModuleConfiguration> modules = config.getModules();

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
                if (modules.containsKey(Modules.TAILWIND_CSS)) {
                    ProjectInitializer.installTailwind(appDirectory);
                }
                if (modules.containsKey(Modules.PRETTIER)) {
                    ProjectInitializer.installPrettier(appDirectory);

                    ModuleConfiguration moduleConfig = modules.get(Modules.PRETTIER);
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

                if (modules.containsKey(Modules.FRAMER_MOTION)) {
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
