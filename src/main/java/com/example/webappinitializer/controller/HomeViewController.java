package com.example.webappinitializer.controller;

import com.example.webappinitializer.alert.ComingSoonAlert;
import com.example.webappinitializer.util.EventManager;
import com.example.webappinitializer.util.EventType;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;

/**
 * This is the controller for the home view.
 */
public class HomeViewController {

    @FXML
    public Button getStartedButton;
    public Button loadConfigButton;

    /**
     * This method is called when the view is initialized.
     * <p>
     *     It styles the buttons.
     * </p>
     */
    @FXML
    protected void initialize() {
        getStartedButton.getStyleClass().addAll("btn-lg", "btn-success");
        loadConfigButton.getStyleClass().addAll("btn-lg", "btn-primary");
    }

    /**
     * This method is called when the Get Started button is clicked.
     * <p>
     *     It publishes the GET_STARTED_BUTTON_CLICKED event.
     * </p>
     * @param actionEvent The event that triggered this method.
     */
    public void handleGetStartedButtonAction(ActionEvent actionEvent) {
        EventManager.publish(EventType.GET_STARTED_BUTTON_CLICKED, null);
    }

    /**
     * This method is called when the Load Config button is clicked.
     * <p>
     *     It shows a Coming Soon alert.
     * </p>
     * @param actionEvent The event that triggered this method.
     */
    public void handleLoadConfigButtonAction(ActionEvent actionEvent) {
        // TODO: Implement loading existing configurations
        ComingSoonAlert alert = new ComingSoonAlert();
        alert.show();
    }
}
