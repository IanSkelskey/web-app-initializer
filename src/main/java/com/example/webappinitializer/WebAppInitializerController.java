package com.example.webappinitializer;

import com.example.webappinitializer.util.ProjectInitializer;
import javafx.concurrent.Task;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.VBox;

import java.io.File;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

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
    public VBox prettierConfigContainer;
    public CheckBox prettierSemiCheckBox;
    public CheckBox prettierSingleQuoteCheckBox;
    public CheckBox prettierTrailingCommaCheckBox;
    public Spinner prettierTabWidthSpinner;
    public Spinner prettierPrintWidthSpinner;
    public CheckBox prettierBracketSpacingCheckBox;
    public CheckBox prettierJSXBracketsCheckBox;
    public ComboBox prettierEOLComboBox;
    public Button getStartedButton;

    @FXML
    protected void initialize() {
        steps.add(step0Container);
        steps.add(step1Container);
        steps.add(step2Container);
        getStartedButton.getStyleClass().addAll("btn-lg", "btn-primary");
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
        boolean installPrettier = prettierCheckBox.isSelected();

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
                ProjectInitializer.updateReadme(appDirectory, appName, description);
                if (installTailwind) {
                    ProjectInitializer.installTailwind(appDirectory);
                }
                if (installPrettier) {
                    ProjectInitializer.installPrettier(appDirectory);
                    Map<String, Object> prettierConfig = new HashMap<>();
                    prettierConfig.put("semi", prettierSemiCheckBox.isSelected());
                    prettierConfig.put("singleQuote", prettierSingleQuoteCheckBox.isSelected());
                    prettierConfig.put("trailingComma", prettierTrailingCommaCheckBox.isSelected());
                    prettierConfig.put("tabWidth", prettierTabWidthSpinner.getValue());
                    prettierConfig.put("printWidth", prettierPrintWidthSpinner.getValue());
                    prettierConfig.put("bracketSpacing", prettierBracketSpacingCheckBox.isSelected());
                    prettierConfig.put("jsxBracketSameLine", prettierJSXBracketsCheckBox.isSelected());
                    prettierConfig.put("endOfLine", prettierEOLComboBox.getValue());
                    ProjectInitializer.configurePrettier(appDirectory, prettierConfig);
                    ProjectInitializer.runPrettier(appDirectory);
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

    public void handlePrettierCheck(ActionEvent actionEvent) {
        if (prettierCheckBox.isSelected()) {
            steps.add(prettierConfigContainer);
        } else {
            steps.remove(prettierConfigContainer);
        }
        if (currentStep < steps.size() - 1) {
            nextButton.setVisible(true);
            createAppButton.setVisible(false);
        }
    }
}
