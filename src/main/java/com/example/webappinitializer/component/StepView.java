package com.example.webappinitializer.component;

import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;
import javafx.scene.layout.BorderPane;

/**
 * This is the abstract class for the step view.
 *
 * @see com.example.webappinitializer.component.NameAndDescriptionView
 * @see com.example.webappinitializer.component.ModuleSelectionView
 * @see com.example.webappinitializer.component.ModuleConfigurationView
 * @see com.example.webappinitializer.component.ConfigurationSummaryView
 */
public class StepView extends BorderPane {
    private final StringProperty name = new SimpleStringProperty(this, "name");
    private final StringProperty description = new SimpleStringProperty(this, "description");

    private final int CONTENT_PADDING = 10;

    /**
     * Constructor
     */
    public StepView() {
        StepTitleLabel titleLabel = new StepTitleLabel();
        titleLabel.textProperty().bind(name);
        titleLabel.prefWidthProperty().bind(widthProperty());
        setTop(titleLabel);
    }

    /**
     * @return The name of the step.
     */
    public String getName() {
        return name.get();
    }

    /**
     * @return The name property of the step.
     */
    public StringProperty nameProperty() {
        return name;
    }

    /**
     * @param name The name of the step.
     */
    public void setName(String name) {
        this.name.set(name);
    }

    /**
     * @return The description of the step.
     */
    public String getDescription() {
        return description.get();
    }

    /**
     * @return The description property of the step.
     */
    public StringProperty descriptionProperty() {
        return description;
    }

    /**
     * @param description The description of the step.
     */
    public void setDescription(String description) {
        this.description.set(description);
    }

    @Override
    public String toString() {
        return "Step Name: " + getName() + "\nStep Description: " + getDescription() + "\n";
    }
}
