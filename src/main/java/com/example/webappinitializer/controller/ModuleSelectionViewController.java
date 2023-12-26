package com.example.webappinitializer.controller;

import com.example.webappinitializer.util.EventManager;
import com.example.webappinitializer.util.Modules;
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
        Modules module;
        if (checkBox == prettierCheckBox) {
            module = Modules.PRETTIER;
        } else if (checkBox == tailwindCssCheckBox) {
            module = Modules.TAILWIND_CSS;
        } else {
            module = Modules.FRAMER_MOTION;
        }
        if (checkBox.isSelected()) {
            EventManager.publish("module-selected", module);
        } else {
            EventManager.publish("module-deselected", module);
        }

    }
}
