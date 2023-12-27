package com.example.webappinitializer.controller;

import com.example.webappinitializer.config.PrettierConfiguration;
import com.example.webappinitializer.util.EventManager;
import com.example.webappinitializer.config.ProjectConfiguration;
import com.example.webappinitializer.util.Modules;
import com.example.webappinitializer.util.ProjectInitializer;
import com.example.webappinitializer.component.StepView;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.layout.Background;
import javafx.scene.layout.BackgroundFill;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.VBox;
import org.controlsfx.control.BreadCrumbBar;

import java.io.File;
import java.util.ArrayList;

public class WizardViewController {

    public StackPane stepsContainer;

    public ProjectConfiguration projectConfiguration = new ProjectConfiguration();

    public Button backButton;
    public Button createAppButton;
    public Button nextButton;

    public ArrayList<StepView> steps = new ArrayList<>();

    public int currentStep = 0;
    public VBox homeView;
    public StepView nameAndDescriptionView;
    public StepView moduleSelectionView;
    public StepView tailwindConfigurationView;
    public StepView prettierConfigurationView;

    @FXML
    protected void initialize() {
        EventManager.subscribe("getStartedButtonClicked", (event) -> {
            homeView.setVisible(false);
            steps.get(0).setVisible(true);
            nextButton.setVisible(true);
        });

        EventManager.subscribe("module-selected", (module) -> {
            if (module == Modules.TAILWIND_CSS) {
                projectConfiguration.addModule(Modules.TAILWIND_CSS);
                steps.add(tailwindConfigurationView);
            } else if (module == Modules.PRETTIER) {
                projectConfiguration.addModule(Modules.PRETTIER);
                steps.add(prettierConfigurationView);
            } else if (module == Modules.FRAMER_MOTION) {
                projectConfiguration.addModule(Modules.FRAMER_MOTION);
            }
            if (currentStep < steps.size() - 1) {
                nextButton.setVisible(true);
                createAppButton.setVisible(false);
            }
        });

        EventManager.subscribe("module-deselected", (module) -> {
            if (module == Modules.TAILWIND_CSS) {
                projectConfiguration.removeModule(Modules.TAILWIND_CSS);
                steps.remove(tailwindConfigurationView);
            } else if (module == Modules.PRETTIER) {
                projectConfiguration.removeModule(Modules.PRETTIER);
                steps.remove(prettierConfigurationView);
            } else if (module == Modules.FRAMER_MOTION) {
                projectConfiguration.removeModule(Modules.FRAMER_MOTION);
            }
            if (currentStep == steps.size() - 1) {
                nextButton.setVisible(false);
                createAppButton.setVisible(true);
            }
        });

        EventManager.subscribe("directoryNameChanged", (name) -> {
            projectConfiguration.setDirectoryName((String) name);
        });

        EventManager.subscribe("appShortNameChanged", (name) -> {
            projectConfiguration.setShortName((String) name);
        });

        EventManager.subscribe("appFullNameChanged", (name) -> {
            projectConfiguration.setFullName((String) name);
        });

        EventManager.subscribe("appDescriptionChanged", (description) -> {
            projectConfiguration.setDescription((String) description);
        });

        EventManager.subscribe("pretterConfigChanged", (config) -> {
            projectConfiguration.setModuleConfiguration(Modules.PRETTIER, (PrettierConfiguration) config);
        });

        steps.add(nameAndDescriptionView);
        steps.add(moduleSelectionView);

        createAppButton.getStyleClass().addAll("btn-lg", "btn-primary");
    }

    public void handleNextButtonAction(ActionEvent actionEvent) {
        if (currentStep == steps.size() - 1) {
            return;
        }
        steps.get(currentStep).setVisible(false);
        steps.get(currentStep + 1).setVisible(true);
        currentStep++;
        if (currentStep == steps.size() - 1) {
            nextButton.setVisible(false);
            createAppButton.setVisible(true);
        } else {
            nextButton.setVisible(true);
            createAppButton.setVisible(false);
        }
        if (currentStep > 0) {
            backButton.setVisible(true);
        }

    }

    public void handleBackButtonAction(ActionEvent actionEvent) {
        if (currentStep == 0) {
            return;
        }
        steps.get(currentStep).setVisible(false);
        steps.get(currentStep - 1).setVisible(true);
        currentStep--;

        if (currentStep < steps.size() - 1) {
            nextButton.setVisible(true);
            createAppButton.setVisible(false);
        }

    }

    public void onCreateAppButtonClick(ActionEvent actionEvent) {
        File selectedDirectory = ProjectInitializer.selectDirectory();
        projectConfiguration.build(selectedDirectory);
    }
}
