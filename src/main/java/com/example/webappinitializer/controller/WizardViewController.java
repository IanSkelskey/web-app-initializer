package com.example.webappinitializer.controller;

import com.example.webappinitializer.util.ProjectConfiguration;
import com.example.webappinitializer.util.ProjectInitializer;
import javafx.event.ActionEvent;
import javafx.scene.control.*;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.VBox;

import java.io.File;
import java.util.ArrayList;

public class WizardViewController {

    public StackPane stepsContainer;

    public ProjectConfiguration projectConfiguration;

    public Button backButton;
    public Button createAppButton;
    public Button nextButton;

    public ArrayList<VBox> steps = new ArrayList<>();

    public int currentStep = 0;


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

    public void onCreateAppButtonClick(ActionEvent actionEvent) {
        File selectedDirectory = ProjectInitializer.selectDirectory();
        projectConfiguration.build(selectedDirectory);
    }
}
