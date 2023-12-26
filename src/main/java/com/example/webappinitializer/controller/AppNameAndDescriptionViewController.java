package com.example.webappinitializer.controller;

import com.example.webappinitializer.util.EventManager;
import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;

public class AppNameAndDescriptionViewController {

    @FXML
    public Label directoryNameLabel;

    @FXML
    public TextField appShortNameTextField, appFullNameTextField;

    @FXML
    public TextArea appDescriptionTextField;

    @FXML
    protected void initialize() {
        appShortNameTextField.textProperty().addListener((observable, oldValue, newValue) -> {
            String directoryName = newValue.toLowerCase().replaceAll("\\s+", "-");
            directoryNameLabel.setText(directoryName);
            EventManager.publish("appShortNameChanged", newValue);
            EventManager.publish("directoryNameChanged", directoryName);
        });
        appFullNameTextField.textProperty().addListener((observable, oldValue, newValue) -> {
            EventManager.publish("appFullNameChanged", newValue);
        });
        appDescriptionTextField.textProperty().addListener((observable, oldValue, newValue) -> {
            EventManager.publish("appDescriptionChanged", newValue);
        });
    }

    // TODO: Whenever one of these fields is changed, some data structure should be updated
    // TODO: Create a project configuration manager that holds all the project configuration data for the wizard
    // TODO: Create a class to validate the project configuration data
}
