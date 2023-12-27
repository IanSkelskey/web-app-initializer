package com.example.webappinitializer.config;

import com.example.webappinitializer.util.EventManager;
import com.example.webappinitializer.util.Modules;
import com.example.webappinitializer.util.ProjectBuilder;

import java.io.File;

/**
 * This class is responsible for managing the configuration of the project.
 */
public class ProjectConfigurationManager {

    /**
     * The configuration of the project.
     */
    private final ProjectConfiguration configuration = new ProjectConfiguration();

    /**
     * Creates a new instance of the ProjectConfigurationManager class.
     */
    public ProjectConfigurationManager() {
        subscribeToConfigurationChangeEvents();
    }

    /**
     * Subscribes to events that are fired when the configuration changes.
     */
    private void subscribeToConfigurationChangeEvents() {
        EventManager.subscribe("directoryNameChanged", (newName) -> {
            configuration.setDirectoryName((String) newName);
        });

        EventManager.subscribe("appShortNameChanged", (newName) -> {
            configuration.setShortName((String) newName);
        });

        EventManager.subscribe("appFullNameChanged", (newName) -> {
            configuration.setFullName((String) newName);
        });

        EventManager.subscribe("appDescriptionChanged", (newDescription) -> {
            configuration.setDescription((String) newDescription);
        });

        EventManager.subscribe("pretterConfigChanged", (newConfig) -> {
            configuration.setModuleConfiguration(Modules.PRETTIER, (PrettierConfiguration) newConfig);
        });

        EventManager.subscribe("module-selected", (module) -> {
            configuration.addModule((Modules) module);
        });

        EventManager.subscribe("module-deselected", (module) -> {
            configuration.removeModule((Modules) module);
        });
    }

    /**
     * Builds the project.
     * @param destinationDirectory The directory where the project will be built.
     */
    public void buildProject(File destinationDirectory) {
        ProjectBuilder.build(configuration, destinationDirectory);
    }

}
