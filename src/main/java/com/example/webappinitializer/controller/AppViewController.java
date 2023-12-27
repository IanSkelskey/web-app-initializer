package com.example.webappinitializer.controller;

import com.example.webappinitializer.component.WizardView;
import com.example.webappinitializer.util.EventManager;
import javafx.fxml.FXML;
import javafx.scene.layout.VBox;

public class AppViewController {

    public VBox homeView;
    public WizardView wizardView;

    @FXML
    protected void initialize() {
        homeView.setVisible(true);
        wizardView.setVisible(false);
        EventManager.subscribe("getStartedButtonClicked", (event) -> {
            homeView.setVisible(false);
            wizardView.setVisible(true);
        });
    }

}
