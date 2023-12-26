package com.example.webappinitializer.controller;

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

public class WizardViewController {

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
    public VBox prettierConfigContainer;
    public VBox tailWindConfigContainer;

    @FXML
    private CheckBox prettierCheckBox, tailwindCssCheckBox, framerMotionCheckBox, prettierSemiCheckBox, prettierSingleQuoteCheckBox, prettierBracketSpacingCheckBox;

    @FXML
    public Spinner prettierTabWidthSpinner, prettierPrintWidthSpinner;

    @FXML
    public ComboBox prettierEOLComboBox, prettierTrailingCommaComboBox;

    public Button getStartedButton;
    public TextField appShortNameTextField;
    public TextField appFullNameTextField;
    public Label directoryNameLabel;
    public Label prettierConfigPreview;


    @FXML
    protected void initialize() {
        steps.add(step0Container);
        steps.add(step1Container);
        steps.add(step2Container);
        getStartedButton.getStyleClass().addAll("btn-lg", "btn-primary");
        createAppButton.getStyleClass().addAll("btn-lg", "btn-primary");

        appShortNameTextField.textProperty().addListener((observable, oldValue, newValue) -> {
            directoryNameLabel.setText(newValue.toLowerCase().replaceAll("\\s+", "-"));
        });

        prettierTabWidthSpinner.getEditor().textProperty().addListener((observable, oldValue, newValue) -> {
            handlePrettierConfigChange(null);
        });

        prettierPrintWidthSpinner.getEditor().textProperty().addListener((observable, oldValue, newValue) -> {
            handlePrettierConfigChange(null);
        });

        handlePrettierConfigChange(null);
    }

    @FXML
    protected void onCreateAppButtonClick() {
        // TODO: This should probable be moved to something like a ProjectBuilder class
        String directoryName = directoryNameLabel.getText();
        String shortName = appShortNameTextField.getText();
        String fullName = appFullNameTextField.getText();

        String description = appDescriptionTextField.getText();
        boolean installTailwind = tailwindCssCheckBox.isSelected();
        boolean installPrettier = prettierCheckBox.isSelected();
        boolean installFramerMotion = framerMotionCheckBox.isSelected();

        File selectedDirectory = ProjectInitializer.selectDirectory();
        if (selectedDirectory == null) {
            return;
        }

        ProgressBar progressBar = new ProgressBar();
        progressBar.setProgress(ProgressBar.INDETERMINATE_PROGRESS);

        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle("Creating Application");
        alert.setHeaderText(null);
        alert.setContentText("Please wait...");
        alert.getDialogPane().setContent(progressBar);

        Task<Void> task = new Task<>() {
            @Override
            protected Void call() throws Exception {
                File appDirectory = new File(selectedDirectory, directoryName);
                ProjectInitializer.createReactApp(directoryName, selectedDirectory);
                ProjectInitializer.updatePackageJsonDescription(appDirectory, description);
                ProjectInitializer.updatePublicIndexDescription(appDirectory, description);
                ProjectInitializer.updatePublicIndexTitle(appDirectory, fullName);
                ProjectInitializer.removeCommentsFromPublicIndex(appDirectory);
                ProjectInitializer.updateReadme(appDirectory, fullName, description);
                ProjectInitializer.updateManifestJsonName(appDirectory, shortName, fullName);
                if (installTailwind) {
                    ProjectInitializer.installTailwind(appDirectory);
                }
                if (installPrettier) {
                    ProjectInitializer.installPrettier(appDirectory);
                    Map<String, Object> prettierConfig = new HashMap<>();
                    prettierConfig.put("semi", prettierSemiCheckBox.isSelected());
                    prettierConfig.put("singleQuote", prettierSingleQuoteCheckBox.isSelected());
                    prettierConfig.put("trailingComma", prettierTrailingCommaComboBox.getValue().toString().toLowerCase());
                    prettierConfig.put("tabWidth", prettierTabWidthSpinner.getValue());
                    prettierConfig.put("printWidth", prettierPrintWidthSpinner.getValue());
                    prettierConfig.put("bracketSpacing", prettierBracketSpacingCheckBox.isSelected());
                    prettierConfig.put("endOfLine", prettierEOLComboBox.getValue().toString().toLowerCase());
                    ProjectInitializer.configurePrettier(appDirectory, prettierConfig);
                    ProjectInitializer.runPrettier(appDirectory);
                }
                if (installFramerMotion) {
                    ProjectInitializer.installFramerMotion(appDirectory);
                }
                return null;
            }
        };

        task.setOnSucceeded(event -> {
            alert.close();
            System.out.println("React app created successfully");
        });

        task.setOnFailed(event -> {
            Throwable exception = task.getException();
            System.out.println("Failed to create React app: " + exception.getMessage());
        });

        new Thread(task).start();
        alert.showAndWait();
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
                nextButton.setVisible(false);
            } else if (currentStep < steps.size() - 1) {
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
        handleModuleSelect(prettierCheckBox, prettierConfigContainer);
    }

    public void handleTailwindCssCheck(ActionEvent actionEvent) {
        handleModuleSelect(tailwindCssCheckBox, tailWindConfigContainer);
    }

    private void handleModuleSelect(CheckBox checkBox, VBox configContainer) {
        if (checkBox.isSelected()) {
            steps.add(configContainer);
        } else {
            steps.remove(configContainer);
        }
        if (currentStep < steps.size() - 1) {
            nextButton.setVisible(true);
            createAppButton.setVisible(false);
        } else {
            nextButton.setVisible(false);
            createAppButton.setVisible(true);
        }
    }

    public void handlePrettierConfigChange(ActionEvent actionEvent) {
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
