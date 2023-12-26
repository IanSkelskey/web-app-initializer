package com.example.webappinitializer.controller;

import com.example.webappinitializer.alert.ComingSoonAlert;
import com.example.webappinitializer.util.EventManager;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;

public class HomeViewController {

    @FXML
    public Button getStartedButton;
    public Button loadConfigButton;

    @FXML
    protected void initialize() {
        getStartedButton.getStyleClass().addAll("btn-lg", "btn-success");
        loadConfigButton.getStyleClass().addAll("btn-lg", "btn-primary");
    }

    public void handleGetStartedButtonAction(ActionEvent actionEvent) {
        EventManager.publish("getStartedButtonClicked", null);
    }

    public void handleLoadConfigButtonAction(ActionEvent actionEvent) {
        // TODO: Implement loading existing configurations
        ComingSoonAlert alert = new ComingSoonAlert();
        alert.show();
    }
}
