package com.example.webappinitializer.component;

import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;
import javafx.scene.layout.BorderPane;

public class StepView extends BorderPane {
    private final StringProperty name = new SimpleStringProperty(this, "name");
    private final StringProperty description = new SimpleStringProperty(this, "description");

    private final int CONTENT_PADDING = 10;

    public StepView() {
        StepTitleLabel titleLabel = new StepTitleLabel();
        titleLabel.textProperty().bind(name);
        titleLabel.prefWidthProperty().bind(widthProperty());
        setTop(titleLabel);
    }

    public String getName() {
        return name.get();
    }

    public StringProperty nameProperty() {
        return name;
    }

    public void setName(String name) {
        this.name.set(name);
    }

    public String getDescription() {
        return description.get();
    }

    public StringProperty descriptionProperty() {
        return description;
    }

    public void setDescription(String description) {
        this.description.set(description);
    }

    @Override
    public String toString() {
        return "Step Name: " + getName() + "\nStep Description: " + getDescription() + "\n";
    }
}
