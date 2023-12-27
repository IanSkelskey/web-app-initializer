package com.example.webappinitializer.controller;

import com.example.webappinitializer.component.WizardView;
import com.example.webappinitializer.util.EventManager;
import com.example.webappinitializer.util.EventType;
import javafx.fxml.FXML;
import javafx.scene.layout.VBox;

public class AppViewController {

    public VBox homeView;
    public WizardView wizardView;

    @FXML
    protected void initialize() {
        homeView.setVisible(true);
        wizardView.setVisible(false);
        EventManager.subscribe(EventType.GET_STARTED_BUTTON_CLICKED, (event) -> {
            homeView.setVisible(false);
            wizardView.setVisible(true);
        });
        EventManager.subscribe(EventType.HOME_BUTTON_CLICKED, (event) -> {
            homeView.setVisible(true);
            wizardView.setVisible(false);
        });
    }

}
