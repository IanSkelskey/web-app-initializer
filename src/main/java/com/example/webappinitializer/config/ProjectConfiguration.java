package com.example.webappinitializer.config;

import com.example.webappinitializer.util.Module;
import java.util.HashMap;
import java.util.Map;
import java.util.Objects;

public class ProjectConfiguration {
    private String directoryName;
    private String shortName;
    private String fullName;
    private String description;
    private final Map<Module, ModuleConfiguration> modules;

    public ProjectConfiguration() {
        this.directoryName = "";
        this.shortName = "";
        this.fullName = "";
        this.description = "";
        this.modules = new HashMap<>();
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

    public void addModule(Module module) {
        ModuleConfiguration config;
        if (Objects.requireNonNull(module) == Module.PRETTIER) {
            config = new PrettierConfiguration();
        } else {
            config = null;
        }
        this.modules.put(module, config);
    }

    public Map<Module, ModuleConfiguration> getModules() {
        return this.modules;
    }

    public void removeModule(Module module) {
        this.modules.remove(module);
    }

    public void setModuleConfiguration(Module module, ModuleConfiguration config) {
        this.modules.put(module, config);
    }
}
