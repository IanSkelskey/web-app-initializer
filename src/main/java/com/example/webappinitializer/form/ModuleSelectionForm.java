package com.example.webappinitializer.form;

import com.example.webappinitializer.util.EventManager;
import com.example.webappinitializer.util.Modules;
import javafx.event.ActionEvent;
import javafx.geometry.Insets;
import javafx.scene.control.CheckBox;
import javafx.scene.layout.VBox;

import static javafx.geometry.Pos.CENTER;

public class ModuleSelectionForm extends VBox {

    private CheckBox prettierCheckBox = new CheckBox("Prettier"), tailwindCheckBox = new CheckBox("Tailwind CSS"), framerMotionCheckBox = new CheckBox("Framer Motion");

    public ModuleSelectionForm() {
        setAlignment(CENTER);
        setSpacing(20);
        setPadding(new Insets(20));
        prettierCheckBox.getStyleClass().add("checkbox");
        prettierCheckBox.setOnAction(this::handlePrettierCheck);
        tailwindCheckBox.getStyleClass().add("checkbox");
        tailwindCheckBox.setOnAction(this::handleTailwindCssCheck);
        framerMotionCheckBox.getStyleClass().add("checkbox");
        getChildren().addAll(prettierCheckBox, tailwindCheckBox, framerMotionCheckBox);
    }
    public void handlePrettierCheck(ActionEvent actionEvent) {
        handleModuleSelection(prettierCheckBox);
    }

    public void handleTailwindCssCheck(ActionEvent actionEvent) {
        handleModuleSelection(tailwindCheckBox);
    }

    private void handleModuleSelection(CheckBox checkBox) {
        Modules module;
        if (checkBox == prettierCheckBox) {
            module = Modules.PRETTIER;
        } else if (checkBox == tailwindCheckBox) {
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
