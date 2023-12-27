package com.example.webappinitializer.component;

import com.example.webappinitializer.alert.ComingSoonAlert;
import com.example.webappinitializer.config.ProjectConfiguration;
import com.example.webappinitializer.util.EventManager;
import com.example.webappinitializer.util.EventType;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.VBox;

import static javafx.geometry.Pos.CENTER;

public class ConfigurationSummaryView extends StepView {

    private final VBox summary = new VBox();
    private ProjectConfiguration projectConfiguration = new ProjectConfiguration();

    public ConfigurationSummaryView() {
        EventManager.subscribe(EventType.PROJECT_CONFIGURATION_UPDATED, (config) -> {
            System.out.println("Updating project configuration in summary view.");
            projectConfiguration = (ProjectConfiguration) config;
            initSummary();
        });

        initSummary();

        setName("Configuration Summary");
        setDescription("Please review your configuration before proceeding.");
        setCenter(summary);
    }

    private void initSummary() {
        Label directoryName = new Label("Directory name: " + projectConfiguration.getDirectoryName());
        Label shortName = new Label(projectConfiguration.getShortName());
        shortName.getStyleClass().add("h4");
        Label fullName = new Label(projectConfiguration.getFullName());
        fullName.getStyleClass().add("h3");
        Label description = new Label("Description: " + projectConfiguration.getDescription());



        summary.setAlignment(CENTER);
        summary.setSpacing(20);
        summary.getChildren().clear();
        summary.getChildren().add(fullName);
        summary.getChildren().add(shortName);
        summary.getChildren().add(directoryName);
        summary.getChildren().add(description);
        summary.getChildren().add(new Label("Modules: "));
        projectConfiguration.getModules().forEach((key, value) -> {
            summary.getChildren().add(new Label("  " + key.name()));
        });
        initCreateAppButton();
        initSaveConfigurationButton();
    }

    private void initCreateAppButton() {
        Button createAppButton = new Button("Create App");
        createAppButton.setOnAction(event -> EventManager.publish(EventType.CREATE_APP_BUTTON_CLICKED, null));
        createAppButton.getStyleClass().addAll("btn-success", "btn-lg");
        summary.getChildren().add(createAppButton);
    }

    private void initSaveConfigurationButton() {
        Button saveConfigurationButton = new Button("Save Configuration");
        saveConfigurationButton.setOnAction(event -> new ComingSoonAlert().show());
        saveConfigurationButton.getStyleClass().addAll("btn-primary", "btn-lg");
        summary.getChildren().add(saveConfigurationButton);
    }

}
