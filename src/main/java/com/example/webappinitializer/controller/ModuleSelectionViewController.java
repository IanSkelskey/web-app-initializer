package com.example.webappinitializer.controller;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.CheckBox;

public class ModuleSelectionViewController {

    @FXML
    public CheckBox prettierCheckBox, tailwindCssCheckBox, framerMotionCheckBox;

    public void handlePrettierCheck(ActionEvent actionEvent) {
        handleModuleSelection(prettierCheckBox);
    }

    public void handleTailwindCssCheck(ActionEvent actionEvent) {
        handleModuleSelection(tailwindCssCheckBox);
    }

    private void handleModuleSelection(CheckBox checkBox) {
        if (checkBox.isSelected()) {
            // TODO: Add the module to the list of modules
            // TODO: This should be part of the project configuration manager
        } else {
            // TODO: Remove the module from the list of modules
        }

    }
}
