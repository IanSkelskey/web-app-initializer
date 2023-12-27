package com.example.webappinitializer.config;

import com.example.webappinitializer.util.EventManager;
import com.example.webappinitializer.util.EventType;
import com.example.webappinitializer.util.Module;
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
    public void subscribeToConfigurationChangeEvents() {
        EventManager.subscribe(EventType.DIRECTORY_NAME_CHANGED, (newName) -> {
            System.out.println("directory name changed: " + newName);
            configuration.setDirectoryName((String) newName);
            EventManager.publish(EventType.PROJECT_CONFIGURATION_UPDATED, configuration);
        });

        EventManager.subscribe(EventType.SHORT_NAME_CHANGED, (newName) -> {
            configuration.setShortName((String) newName);
            EventManager.publish(EventType.PROJECT_CONFIGURATION_UPDATED, configuration);
        });

        EventManager.subscribe(EventType.FULL_NAME_CHANGED, (newName) -> {
            configuration.setFullName((String) newName);
            EventManager.publish(EventType.PROJECT_CONFIGURATION_UPDATED, configuration);
        });

        EventManager.subscribe(EventType.DESCRIPTION_CHANGED, (newDescription) -> {
            configuration.setDescription((String) newDescription);
            EventManager.publish(EventType.PROJECT_CONFIGURATION_UPDATED, configuration);
        });

        EventManager.subscribe(EventType.PRETTIER_CONFIG_CHANGED, (newConfig) -> {
            configuration.setModuleConfiguration(Module.PRETTIER, (PrettierConfiguration) newConfig);
            EventManager.publish(EventType.PROJECT_CONFIGURATION_UPDATED, configuration);
        });

        EventManager.subscribe(EventType.MODULE_SELECTED, (module) -> {
            System.out.println("module selected: " + module);
            if (module == Module.TAILWIND_CSS) {
                configuration.addModule(Module.TAILWIND_CSS);
            } else if (module == Module.PRETTIER) {
                configuration.addModule(Module.PRETTIER);
            } else if (module == Module.FRAMER_MOTION) {
                configuration.addModule(Module.FRAMER_MOTION);
            }
            EventManager.publish(EventType.PROJECT_CONFIGURATION_UPDATED, configuration);
        });

        EventManager.subscribe(EventType.MODULE_DESELECTED, (module) -> {
            System.out.println("module deselected: " + module);
            if (module == Module.TAILWIND_CSS) {
                configuration.removeModule(Module.TAILWIND_CSS);
            } else if (module == Module.PRETTIER) {
                configuration.removeModule(Module.PRETTIER);
            } else if (module == Module.FRAMER_MOTION) {
                configuration.removeModule(Module.FRAMER_MOTION);
            }
            EventManager.publish(EventType.PROJECT_CONFIGURATION_UPDATED, configuration);
        });
    }

    /**
     * Builds the project.
     * @param destinationDirectory The directory where the project will be built.
     */
    public void buildProject(File destinationDirectory) {
        ProjectBuilder.build(configuration, destinationDirectory);
    }

    public ProjectConfiguration getConfiguration() {
        return configuration;
    }

}
