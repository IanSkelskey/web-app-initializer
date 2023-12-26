package com.example.webappinitializer.config;

import com.example.webappinitializer.alert.BuildProgressAlert;
import com.example.webappinitializer.util.Modules;
import com.example.webappinitializer.util.ProjectInitializer;
import javafx.concurrent.Task;

import java.io.File;
import java.util.HashMap;
import java.util.Map;
import java.util.Objects;

public class ProjectConfiguration {
    private String directoryName;
    private String shortName;
    private String fullName;
    private String description;
    private final Map<Modules, ModuleConfiguration> modules;

    public ProjectConfiguration() {
        this.directoryName = "";
        this.shortName = "";
        this.fullName = "";
        this.description = "";
        this.modules = new HashMap<>();
    }

    public void build(File destinationDirectory) {
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
                    if (moduleConfig instanceof PrettierConfiguration) {
                        PrettierConfiguration prettierConfig = (PrettierConfiguration) moduleConfig;

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

    public void setDirectoryName(String directoryName) {
        this.directoryName = directoryName;
    }

    public String getDirectoryName() {
        return this.directoryName;
    }

    public void setShortName(String shortName) {
        this.shortName = shortName;
    }

    public String getShortName() {
        return this.shortName;
    }

    public void setFullName(String fullName) {
        this.fullName = fullName;
    }

    public String getFullName() {
        return this.fullName;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getDescription() {
        return this.description;
    }

    public void addModule(Modules module) {
        ModuleConfiguration config;
        if (Objects.requireNonNull(module) == Modules.PRETTIER) {
            config = new PrettierConfiguration();
        } else {
            config = null;
        }
        this.modules.put(module, config);
    }

    public Map<Modules, ModuleConfiguration> getModules() {
        return this.modules;
    }

    public void removeModule(Modules module) {
        this.modules.remove(module);
    }

    public void setModuleConfiguration(Modules module, ModuleConfiguration config) {
        this.modules.put(module, config);
    }
}
