package com.example.webappinitializer.component;

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
            projectConfiguration = (ProjectConfiguration) config;
            initSummary();
        });

        initSummary();

        setName("Configuration Summary");
        setDescription("Please review your configuration before proceeding.");
        setCenter(summary);
    }

    private void initSummary() {
        summary.setAlignment(CENTER);
        summary.setSpacing(20);
        summary.getChildren().clear();
        summary.getChildren().add(new Label("Directory name: " + projectConfiguration.getDirectoryName()));
        summary.getChildren().add(new Label("Short Name: " + projectConfiguration.getShortName()));
        summary.getChildren().add(new Label("Full Name: " + projectConfiguration.getFullName()));
        summary.getChildren().add(new Label("Description: " + projectConfiguration.getDescription()));
        summary.getChildren().add(new Label("Modules: "));
        projectConfiguration.getModules().forEach((key, value) -> {
            summary.getChildren().add(new Label("  " + key.name()));
        });
        initCreateAppButton();
    }

    private void initCreateAppButton() {
        Button createAppButton = new Button("Create App");
        createAppButton.setOnAction(event -> EventManager.publish(EventType.CREATE_APP_BUTTON_CLICKED, null));
        createAppButton.getStyleClass().addAll("btn-primary", "btn-lg");
        summary.getChildren().add(createAppButton);
    }

}
