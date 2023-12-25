package com.example.webappinitializer;

import com.example.webappinitializer.util.ProjectInitializer;
import javafx.concurrent.Task;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.CheckBox;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.VBox;

import java.io.File;
import java.util.ArrayList;

public class WebAppInitializerController {

    public TextArea appDescriptionTextField;
    public StackPane stepsContainer;
    public VBox step0Container;
    public VBox step1Container;
    public VBox step2Container;

    public Button backButton;
    public Button createAppButton;
    public Button nextButton;

    public ArrayList<VBox> steps = new ArrayList<>();

    public int currentStep = 0;

    @FXML
    protected void initialize() {
        steps.add(step0Container);
        steps.add(step1Container);
        steps.add(step2Container);
    }

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
                ProjectInitializer.updatePublicIndexTitle(appDirectory, appName);
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

    public void handleNextButtonAction(ActionEvent actionEvent) {
        if (currentStep < steps.size() - 1) {
            steps.get(currentStep).setVisible(false);
            steps.get(currentStep + 1).setVisible(true);
            currentStep++;
            if (currentStep == steps.size() - 1) {
                nextButton.setVisible(false);
                createAppButton.setVisible(true);
            }
            if (currentStep > 0) {
                backButton.setVisible(true);
            }
        }

    }

    public void handleBackButtonAction(ActionEvent actionEvent) {
        if (currentStep > 0) {
            steps.get(currentStep).setVisible(false);
            steps.get(currentStep - 1).setVisible(true);
            currentStep--;
            if (currentStep == 0) {
                backButton.setVisible(false);
            }
            if (currentStep < steps.size() - 1) {
                nextButton.setVisible(true);
                createAppButton.setVisible(false);
            }
        }
    }

    public void handleGetStartedButtonAction(ActionEvent actionEvent) {
        step0Container.setVisible(false);
        step1Container.setVisible(true);
        backButton.setVisible(true);
        nextButton.setVisible(true);
    }
}
