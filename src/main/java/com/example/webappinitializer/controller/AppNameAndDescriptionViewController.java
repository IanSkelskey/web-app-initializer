package com.example.webappinitializer.controller;

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
            // to lowecase and replace spaces with hyphens
            directoryNameLabel.setText(newValue.toLowerCase().replaceAll("\\s+", "-"));
        });
    }

    // TODO: Whenever one of these fields is changed, some data structure should be updated
    // TODO: Create a project configuration manager that holds all the project configuration data for the wizard
    // TODO: Create a class to validate the project configuration data
}
