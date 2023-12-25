package com.example.webappinitializer;

import com.example.webappinitializer.util.ProjectInitializer;
import javafx.concurrent.Task;
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

        Task<Void> task = new Task<>() {
            @Override
            protected Void call() throws Exception {
                System.out.println("Creating React app...");
                File selectedDirectory = ProjectInitializer.selectDirectory();
                if (selectedDirectory != null) {
                    ProjectInitializer.createReactApp(appName, selectedDirectory);
                    if (installTailwind) {
                        ProjectInitializer.installTailwind(new File(selectedDirectory, appName));
                    }
                }
                return null;
            }
        };

        new Thread(task).start();
    }
}
