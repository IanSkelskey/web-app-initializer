package com.example.webappinitializer.controller;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;

public class HomeViewController {

    @FXML
    public Button getStartedButton;

    @FXML
    protected void initialize() {
        getStartedButton.getStyleClass().addAll("btn-lg", "btn-primary");
    }

    public void handleGetStartedButtonAction(ActionEvent actionEvent) {
        // TODO: Create an event manager to handle the event
        // TODO: Send out an event to the wizard controller to switch to the first step
    }
}
