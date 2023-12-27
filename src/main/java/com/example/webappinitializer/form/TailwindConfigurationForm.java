package com.example.webappinitializer.form;

import javafx.geometry.Insets;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.VBox;

import java.util.Objects;

import static javafx.geometry.Pos.CENTER;

/**
 * This is the form for configuring Tailwind CSS.
 */
public class TailwindConfigurationForm extends VBox {
    private Image logoImage = new Image(Objects.requireNonNull(getClass().getResourceAsStream("/images/tailwind-css.png")));

    /**
     * Constructor
     * This is the constructor of the Tailwind CSS configuration form.
     * It sets up the form.
     * It also sets up the event listeners for the form fields.
     */
    public TailwindConfigurationForm() {
        ImageView logoImageView = new ImageView(logoImage);
        logoImageView.setFitHeight(100);
        logoImageView.setFitWidth(100);
        logoImageView.setPreserveRatio(true);
        setPickOnBounds(true);

        setAlignment(CENTER);
        setSpacing(20);
        setPadding(new Insets(20));
        getChildren().add(logoImageView);
    }
}
