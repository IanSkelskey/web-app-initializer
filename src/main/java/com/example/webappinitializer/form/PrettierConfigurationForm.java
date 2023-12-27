package com.example.webappinitializer.form;

import com.example.webappinitializer.config.PrettierConfiguration;
import com.example.webappinitializer.util.EventManager;
import com.example.webappinitializer.util.EventType;
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

/**
 * This is the form for configuring Prettier.
 */
public class PrettierConfigurationForm extends VBox {
    private Image logoImage = new Image(Objects.requireNonNull(getClass().getResourceAsStream("/images/prettier.png")));
    private CheckBox semiCheckBox = new CheckBox("Semicolons");
    private CheckBox singleQuoteCheckBox = new CheckBox("Single Quote");
    private CheckBox bracketSpacingCheckBox = new CheckBox("Bracket Spacing");
    private ComboBox<String> trailingCommaComboBox = new ComboBox<>();
    private ComboBox<String> EOLComboBox = new ComboBox<>();
    private Spinner<Integer> tabWidthSpinner = new Spinner<>(2, 8, 2);
    private Spinner<Integer> printWidthSpinner = new Spinner<>(40, 120, 80);

    private PrettierConfiguration configuration = new PrettierConfiguration();

    /**
     * Constructor
     * This is the constructor of the Prettier configuration form.
     * It sets up the form.
     * It also sets up the event listeners for the form fields.
     */
    public PrettierConfigurationForm() {
        tabWidthSpinner.getEditor().textProperty().addListener((observable, oldValue, newValue) -> {
            handlePrettierConfigChange();
        });

        printWidthSpinner.getEditor().textProperty().addListener((observable, oldValue, newValue) -> {
            handlePrettierConfigChange();
        });

        ImageView logoImageView = new ImageView(logoImage);
        logoImageView.setFitHeight(100);
        logoImageView.setFitWidth(100);
        logoImageView.setPreserveRatio(true);
        setPickOnBounds(true);

        trailingCommaComboBox.getItems().addAll("none", "es5", "all");
        trailingCommaComboBox.getSelectionModel().selectFirst();
        EOLComboBox.getItems().addAll("auto", "lf", "crlf", "cr");
        EOLComboBox.getSelectionModel().selectFirst();

        setAlignment(CENTER);
        setSpacing(20);
        setPadding(new Insets(20));
        getChildren().add(logoImageView);
        getChildren().add(new Label("Prettier Configuration: "));
        getChildren().addAll(semiCheckBox, singleQuoteCheckBox, bracketSpacingCheckBox);
        getChildren().add(new Label("Trailing Comma: "));
        getChildren().add(trailingCommaComboBox);
        getChildren().add(new Label("End of Line: "));
        getChildren().add(EOLComboBox);
        getChildren().add(new Label("Tab Width: "));
        getChildren().add(tabWidthSpinner);
        getChildren().add(new Label("Print Width: "));
        getChildren().add(printWidthSpinner);

        handlePrettierConfigChange();

    }

    /**
     * Handle the Prettier configuration change.
     */
    public void handlePrettierConfigChange() {
        configuration = new PrettierConfiguration(
                semiCheckBox.isSelected(),
                singleQuoteCheckBox.isSelected(),
                bracketSpacingCheckBox.isSelected(),
                trailingCommaComboBox.getValue().toLowerCase(),
                tabWidthSpinner.getValue(),
                printWidthSpinner.getValue(),
                EOLComboBox.getValue().toLowerCase()
        );

        EventManager.publish(EventType.PRETTIER_CONFIG_CHANGED, configuration);

    }
}
