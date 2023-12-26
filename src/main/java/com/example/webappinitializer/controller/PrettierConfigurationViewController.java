package com.example.webappinitializer.controller;

import com.example.webappinitializer.config.PrettierConfiguration;
import com.example.webappinitializer.util.EventManager;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.CheckBox;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.control.Spinner;

public class PrettierConfigurationViewController {

    public PrettierConfiguration configuration = new PrettierConfiguration();

    public CheckBox prettierSemiCheckBox;
    public CheckBox prettierSingleQuoteCheckBox;
    public ComboBox prettierTrailingCommaComboBox;
    public Spinner prettierTabWidthSpinner;
    public Spinner prettierPrintWidthSpinner;
    public CheckBox prettierBracketSpacingCheckBox;
    public ComboBox prettierEOLComboBox;
    public Label prettierConfigPreview;

    @FXML
    protected void initialize() {
        prettierTabWidthSpinner.getEditor().textProperty().addListener((observable, oldValue, newValue) -> {
            handlePrettierConfigChange(null);
        });

        prettierPrintWidthSpinner.getEditor().textProperty().addListener((observable, oldValue, newValue) -> {
            handlePrettierConfigChange(null);
        });

        handlePrettierConfigChange(null);
    }

    public void handlePrettierConfigChange(ActionEvent actionEvent) {
        configuration = new PrettierConfiguration(
                prettierSemiCheckBox.isSelected(),
                prettierSingleQuoteCheckBox.isSelected(),
                prettierBracketSpacingCheckBox.isSelected(),
                prettierTrailingCommaComboBox.getValue().toString().toLowerCase(),
                (int) prettierTabWidthSpinner.getValue(),
                (int) prettierPrintWidthSpinner.getValue(),
                prettierEOLComboBox.getValue().toString().toLowerCase()
        );

        EventManager.publish("prettierConfigChanged", configuration);

        prettierConfigPreview.setText(configuration.toString());

    }
}
