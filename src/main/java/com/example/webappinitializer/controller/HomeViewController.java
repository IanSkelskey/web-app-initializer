package com.example.webappinitializer.controller;

import com.example.webappinitializer.util.EventManager;
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
        EventManager.publish("getStartedButtonClicked", null);
    }
}
