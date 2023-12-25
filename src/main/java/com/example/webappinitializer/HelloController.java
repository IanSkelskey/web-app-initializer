package com.example.webappinitializer;

import javafx.fxml.FXML;
import javafx.scene.control.CheckBox;
import javafx.scene.control.TextField;

public class HelloController {

    @FXML
    private TextField appNameTextField;

    @FXML
    private CheckBox prettierCheckBox, tailwindCssCheckBox, framerMotionCheckBox;

    @FXML
    protected void onCreateAppButtonClick() {
        String appName = appNameTextField.getText();
        boolean isPrettierSelected = prettierCheckBox.isSelected();
        boolean isTailwindCssSelected = tailwindCssCheckBox.isSelected();
        boolean isFramerMotionSelected = framerMotionCheckBox.isSelected();

        // Add your logic to handle these selections
    }
}
