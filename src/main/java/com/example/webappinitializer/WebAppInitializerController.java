package com.example.webappinitializer;

import javafx.fxml.FXML;
import javafx.scene.control.CheckBox;
import javafx.scene.control.TextField;
import javafx.stage.DirectoryChooser;

import java.io.File;

public class WebAppInitializerController {

    @FXML
    private TextField appNameTextField;

    @FXML
    private CheckBox prettierCheckBox, tailwindCssCheckBox, framerMotionCheckBox;

    @FXML
    protected void onCreateAppButtonClick() {
        String appName = appNameTextField.getText();
        boolean installTailwind = tailwindCssCheckBox.isSelected();

        DirectoryChooser directoryChooser = new DirectoryChooser();
        directoryChooser.setTitle("Select a folder to create your app");
        File selectedDirectory = directoryChooser.showDialog(null);

        if (selectedDirectory != null) {
            createReactApp(selectedDirectory, appName);
        }

    }

    private void createReactApp(File directory, String appName) {
        try {
            ProcessBuilder builder = new ProcessBuilder("cmd", "/c", "npx create-react-app " + appName);
            builder.directory(directory);
            Process process = builder.start();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private void installTailwindCSS(
}
