package com.example.webappinitializer;

import com.example.webappinitializer.util.ProjectInitializer;
import javafx.concurrent.Task;
import javafx.fxml.FXML;
import javafx.scene.control.CheckBox;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;

import java.io.File;

public class WebAppInitializerController {

    public TextArea appDescriptionTextField;
    @FXML
    private TextField appNameTextField;

    @FXML
    private CheckBox prettierCheckBox, tailwindCssCheckBox, framerMotionCheckBox;

    @FXML
    protected void onCreateAppButtonClick() {
        String appName = appNameTextField.getText();
        String description = appDescriptionTextField.getText();
        boolean installTailwind = tailwindCssCheckBox.isSelected();

        File selectedDirectory = ProjectInitializer.selectDirectory();
        if (selectedDirectory == null) {
            return;
        }
        Task<Void> task = new Task<>() {
            @Override
            protected Void call() throws Exception {
                File appDirectory = new File(selectedDirectory, appName);
                ProjectInitializer.createReactApp(appName, selectedDirectory);
                ProjectInitializer.updatePackageJsonDescription(appDirectory, description);
                ProjectInitializer.updatePublicIndexDescription(appDirectory, description);
                ProjectInitializer.removeCommentsFromPublicIndex(appDirectory);
                if (installTailwind) {
                    ProjectInitializer.installTailwind(new File(selectedDirectory, appName));
                }
                return null;
            }
        };

        task.setOnSucceeded(event -> {
            System.out.println("React app created successfully");
        });

        task.setOnFailed(event -> {
            Throwable exception = task.getException();
            System.out.println("Failed to create React app: " + exception.getMessage());
        });

        new Thread(task).start();
    }
}
