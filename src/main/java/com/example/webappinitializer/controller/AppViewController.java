package com.example.webappinitializer.controller;

import com.example.webappinitializer.component.WizardView;
import com.example.webappinitializer.util.EventManager;
import com.example.webappinitializer.util.EventType;
import javafx.fxml.FXML;
import javafx.scene.layout.VBox;

/**
 * This is the controller for the main view.
 */
public class AppViewController {

    public VBox homeView;
    public WizardView wizardView;

    /**
     * This method is called when the view is initialized.
     * <p>
     *     It subscribes to the GET_STARTED_BUTTON_CLICKED and HOME_BUTTON_CLICKED events.
     *     When the GET_STARTED_BUTTON_CLICKED event is fired, the home view is hidden and the wizard view is shown.
     *     When the HOME_BUTTON_CLICKED event is fired, the home view is shown and the wizard view is hidden.
     * </p>
     */
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
