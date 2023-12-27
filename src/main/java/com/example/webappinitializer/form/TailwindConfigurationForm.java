package com.example.webappinitializer.form;

import com.example.webappinitializer.config.PrettierConfiguration;
import com.example.webappinitializer.util.EventManager;
import javafx.geometry.Insets;
import javafx.scene.control.CheckBox;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.control.Spinner;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.VBox;

import java.util.Objects;

import static javafx.geometry.Pos.CENTER;

public class TailwindConfigurationForm extends VBox {
    private Image logoImage = new Image(Objects.requireNonNull(getClass().getResourceAsStream("/images/tailwind-css.png")));

    public TailwindConfigurationForm() {
        ImageView logoImageView = new ImageView(logoImage);
        logoImageView.setFitHeight(100);
        logoImageView.setFitWidth(100);
        setPickOnBounds(true);

        setAlignment(CENTER);
        setSpacing(20);
        setPadding(new Insets(20));
    }
}
