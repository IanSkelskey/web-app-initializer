package com.example.webappinitializer.component;

import com.example.webappinitializer.util.EventManager;
import com.example.webappinitializer.util.EventType;
import javafx.geometry.Insets;
import javafx.scene.control.Button;
import javafx.scene.layout.HBox;
import org.kordamp.ikonli.fontawesome5.FontAwesomeSolid;
import org.kordamp.ikonli.javafx.FontIcon;

import static javafx.geometry.Pos.CENTER;

/**
 * This is the navigation bar for the wizard.
 */
public class WizardNavigationBar extends HBox {

    private int currentStep = 0;
    private int stepCount = 0;

    /**
     * Constructor
     */
    public WizardNavigationBar() {
        super();
        EventManager.subscribe(EventType.STEP_CHANGED, (step) -> {
            currentStep = (int) step;
            updateButtons();
        });

        EventManager.subscribe(EventType.STEP_COUNT_CHANGED, (stepCount) -> {
            this.stepCount = (int) stepCount;
            updateButtons();
        });

        initHomeButton();
        initBackButton();
        initNextButton();
        setAlignment(CENTER);
        setSpacing(10);
        setPadding(new Insets(10));
    }

    /**
     * Update the buttons based on the current step.
     */
    private void updateButtons() {
        if (currentStep == 0) {
            hideBackButton();
        } else {
            showBackButton();
        }
        if (currentStep == stepCount - 1) {
            hideNextButton();
        } else {
            showNextButton();
        }
    }

    /**
     * Initialize the home button.
     */
    private void initHomeButton() {
        Button homeButton = new Button();
        FontIcon homeIcon = new FontIcon(FontAwesomeSolid.HOME);
        homeIcon.setIconColor(javafx.scene.paint.Color.WHITE);
        homeButton.setGraphic(homeIcon);
        homeButton.setOnAction(event -> EventManager.publish(EventType.HOME_BUTTON_CLICKED, null));
        homeButton.getStyleClass().addAll("btn-primary", "btn-lg");
        getChildren().add(homeButton);
    }

    /**
     * Initialize the back button.
     */
    private void initBackButton() {
        Button backButton = new Button("Back");
        backButton.setOnAction(event -> EventManager.publish(EventType.BACK_BUTTON_CLICKED, null));
        backButton.getStyleClass().addAll("btn-primary", "btn-lg");
        getChildren().add(backButton);
    }

    /**
     * Initialize the next button.
     */
    private void initNextButton() {
        Button nextButton = new Button("Next");
        nextButton.setOnAction(event -> EventManager.publish(EventType.NEXT_BUTTON_CLICKED, null));
        nextButton.getStyleClass().addAll("btn-primary", "btn-lg");
        getChildren().add(nextButton);
    }

    /**
     * Hide the back button.
     */
    public void hideBackButton() {
        getChildren().get(1).setVisible(false);
    }

    /**
     * Show the back button.
     */
    public void showBackButton() {
        getChildren().get(1).setVisible(true);
    }

    /**
     * Hide the next button.
     */
    public void hideNextButton() {
        getChildren().get(2).setVisible(false);
    }

    /**
     * Show the next button.
     */
    public void showNextButton() {
        getChildren().get(2).setVisible(true);
    }
}
