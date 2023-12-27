package com.example.webappinitializer.config;

import com.example.webappinitializer.util.Module;
import java.util.HashMap;
import java.util.Map;
import java.util.Objects;

/**
 * This is the project configuration.
 */
public class ProjectConfiguration {
    private String directoryName;
    private String shortName;
    private String fullName;
    private String description;
    private final Map<Module, ModuleConfiguration> modules;

    /**
     * This is the constructor of the project configuration.
     */
    public ProjectConfiguration() {
        this.directoryName = "";
        this.shortName = "";
        this.fullName = "";
        this.description = "";
        this.modules = new HashMap<>();
    }

    /**
     * @param directoryName The directory name.
     */
    public void setDirectoryName(String directoryName) {
        this.directoryName = directoryName;
    }

    /**
     * @return The directory name.
     */
    public String getDirectoryName() {
        return this.directoryName;
    }

    /**
     * @param shortName The short name.
     */
    public void setShortName(String shortName) {
        this.shortName = shortName;
    }

    /**
     * @return The short name.
     */
    public String getShortName() {
        return this.shortName;
    }

    /**
     * @param fullName The full name.
     */
    public void setFullName(String fullName) {
        this.fullName = fullName;
    }

    /**
     * @return The full name.
     */
    public String getFullName() {
        return this.fullName;
    }

    /**
     * @param description The description.
     */
    public void setDescription(String description) {
        this.description = description;
    }

    /**
     * @return The description.
     */
    public String getDescription() {
        return this.description;
    }

    /**
     * @param module The module to add.
     */
    public void addModule(Module module) {
        ModuleConfiguration config;
        if (Objects.requireNonNull(module) == Module.PRETTIER) {
            config = new PrettierConfiguration();
        } else {
            config = null;
        }
        this.modules.put(module, config);
    }

    /**
     * @return The modules.
     */
    public Map<Module, ModuleConfiguration> getModules() {
        return this.modules;
    }

    /**
     * @param module The module to remove.
     */
    public void removeModule(Module module) {
        this.modules.remove(module);
    }

    /**
     * @param module The module to check.
     */
    public void setModuleConfiguration(Module module, ModuleConfiguration config) {
        this.modules.put(module, config);
    }
}
