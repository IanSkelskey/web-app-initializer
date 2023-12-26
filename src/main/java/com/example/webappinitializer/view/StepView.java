package com.example.webappinitializer.view;

import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;
import javafx.scene.layout.VBox;

public class StepView extends VBox {
    private final StringProperty name = new SimpleStringProperty(this, "name");
    private final StringProperty description = new SimpleStringProperty(this, "description");

    public StepView() {
        // set the padding to 20 all around
        setStyle("-fx-padding: 20;");
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
}
