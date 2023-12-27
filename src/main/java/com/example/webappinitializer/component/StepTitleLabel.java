package com.example.webappinitializer.component;

import javafx.geometry.Insets;
import javafx.scene.control.Label;
import javafx.scene.layout.Background;
import javafx.scene.layout.BackgroundFill;
import javafx.scene.paint.Color;

import static javafx.geometry.Pos.CENTER;

/**
 * This is the label for the step title.
 */
public class StepTitleLabel extends Label {

    private static final Color BACKGROUND_COLOR = Color.web("#3379b7");
    private static final Background BACKGROUND = new Background(
            new BackgroundFill(BACKGROUND_COLOR, null, null));
    private static final String STYLE_CLASS = "h3";
    private static final Color TEXT_COLOR = Color.WHITE;
    public static final int PADDING = 10;

    /**
     * Constructor
     */
    public StepTitleLabel() {
        this("");
    }

    /**
     * Constructor
     * @param text The text to display in the label.
     */
    public StepTitleLabel(String text) {
        super(text);
        setAlignment(CENTER);
        getStyleClass().add(STYLE_CLASS);
        setBackground(BACKGROUND);
        setTextFill(TEXT_COLOR);
        setPadding(new Insets(PADDING));
    }
}
