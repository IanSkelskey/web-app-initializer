package com.example.webappinitializer.component;

import com.example.webappinitializer.util.EventManager;
import com.example.webappinitializer.util.EventType;
import javafx.geometry.Insets;
import javafx.scene.control.Button;
import javafx.scene.layout.HBox;
import org.kordamp.ikonli.fontawesome5.FontAwesomeSolid;
import org.kordamp.ikonli.javafx.FontIcon;

import static javafx.geometry.Pos.CENTER;

public class WizardNavigationBar extends HBox {

    public WizardNavigationBar() {
        super();
        EventManager.subscribe(EventType.STEP_CHANGED, (step) -> {
            updateButtons((int) step);
        });

        initHomeButton();
        initBackButton();
        initNextButton();
        setAlignment(CENTER);
        setSpacing(10);
        setPadding(new Insets(10));
    }

    private void updateButtons(int step) {
        if (step == 0) {
            hideBackButton();
        } else {
            showBackButton();
        }
        if (step == 2) {
            hideNextButton();
        } else {
            showNextButton();
        }
    }

    private void initHomeButton() {
        Button homeButton = new Button();
        FontIcon homeIcon = new FontIcon(FontAwesomeSolid.HOME);
        homeIcon.setIconColor(javafx.scene.paint.Color.WHITE);
        homeButton.setGraphic(homeIcon);
        homeButton.setOnAction(event -> EventManager.publish(EventType.HOME_BUTTON_CLICKED, null));
        homeButton.getStyleClass().addAll("btn-primary", "btn-lg");
        getChildren().add(homeButton);
    }

    private void initBackButton() {
        Button backButton = new Button("Back");
        backButton.setOnAction(event -> EventManager.publish(EventType.BACK_BUTTON_CLICKED, null));
        backButton.getStyleClass().addAll("btn-primary", "btn-lg");
        getChildren().add(backButton);
    }

    private void initNextButton() {
        Button nextButton = new Button("Next");
        nextButton.setOnAction(event -> EventManager.publish(EventType.NEXT_BUTTON_CLICKED, null));
        nextButton.getStyleClass().addAll("btn-primary", "btn-lg");
        getChildren().add(nextButton);
    }

    public void hideBackButton() {
        getChildren().get(1).setVisible(false);
    }

    public void showBackButton() {
        getChildren().get(1).setVisible(true);
    }

    public void hideNextButton() {
        getChildren().get(2).setVisible(false);
    }

    public void showNextButton() {
        getChildren().get(2).setVisible(true);
    }
}
