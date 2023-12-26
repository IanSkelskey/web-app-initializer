package com.example.webappinitializer.controller;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.CheckBox;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.control.Spinner;

public class PrettierConfigurationViewController {
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
        // TODO: This should also update the configuration with the project configuration manager
        boolean semi = prettierSemiCheckBox.isSelected();
        boolean singleQuote = prettierSingleQuoteCheckBox.isSelected();
        boolean bracketSpacing = prettierBracketSpacingCheckBox.isSelected();
        String trailingComma = prettierTrailingCommaComboBox.getValue().toString().toLowerCase();
        int tabWidth = (int) prettierTabWidthSpinner.getValue();
        int printWidth = (int) prettierPrintWidthSpinner.getValue();
        String endOfLine = prettierEOLComboBox.getValue().toString().toLowerCase();
        String preview = "{\n" +
                "  semi: " + semi + ",\n" +
                "  singleQuote: " + singleQuote + ",\n" +
                "  trailingComma: \"" + trailingComma + "\",\n" +
                "  tabWidth: " + tabWidth + ",\n" +
                "  printWidth: " + printWidth + ",\n" +
                "  bracketSpacing: " + bracketSpacing + ",\n" +
                "  endOfLine: \"" + endOfLine + "\",\n" +
                "};";
        prettierConfigPreview.setText(preview);
    }
}
