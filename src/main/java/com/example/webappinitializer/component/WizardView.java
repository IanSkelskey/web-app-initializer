package com.example.webappinitializer.component;

import com.example.webappinitializer.config.ProjectConfigurationManager;
import com.example.webappinitializer.util.EventManager;
import com.example.webappinitializer.util.Modules;
import com.example.webappinitializer.util.ProjectInitializer;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.StackPane;
import java.io.File;
import java.util.ArrayList;

public class WizardView extends BorderPane {

    private final ProjectConfigurationManager projectConfigurationManager = new ProjectConfigurationManager();

    public int currentStep = 0;
    private final StackPane stepsContainer = new StackPane();
    private final ArrayList<StepView> steps = new ArrayList<>();
    private final WizardNavigationBar navigationBar = new WizardNavigationBar();

    private final NameAndDescriptionView nameAndDescriptionView = new NameAndDescriptionView();
    private final ModuleSelectionView moduleSelectionView = new ModuleSelectionView();
    private final TailwindConfigurationView tailwindConfigurationView = new TailwindConfigurationView();
    private final PrettierConfigurationView prettierConfigurationView = new PrettierConfigurationView();
    private final ConfigurationSummaryView configurationSummaryView = new ConfigurationSummaryView();

    public void showCurrentStep() {
        steps.get(currentStep).setVisible(true);
    }

    public void hideInactiveSteps() {
        for (int i = 0; i < steps.size(); i++) {
            if (i != currentStep) {
                steps.get(i).setVisible(false);
            }
        }
    }

    public WizardView() {
        super();
        tailwindConfigurationView.setVisible(false);
        prettierConfigurationView.setVisible(false);
        addStep(nameAndDescriptionView);
        addStep(moduleSelectionView);
        addStep(configurationSummaryView);
        EventManager.subscribe("backButtonClicked", (event) -> handleBackButtonClicked());
        EventManager.subscribe("nextButtonClicked", (event) -> handleNextButtonClicked());
        EventManager.subscribe("createAppButtonClicked", (event) -> handleCreateAppButtonClicked());
        EventManager.subscribe("module-selected", (module) -> {
            if (module == Modules.TAILWIND_CSS) {
                addStep(tailwindConfigurationView);
            } else if (module == Modules.PRETTIER) {
                addStep(prettierConfigurationView);
            }
            updateUI();
        });

        EventManager.subscribe("module-deselected", (module) -> {
            if (module == Modules.TAILWIND_CSS) {
                removeStep(tailwindConfigurationView);
            } else if (module == Modules.PRETTIER) {
                removeStep(prettierConfigurationView);
            }
            updateUI();
        });

        setTop(navigationBar);
        setCenter(stepsContainer);
        updateUI();
    }

    public void updateButtons() {
        if (isOnFirstStep()) {
            navigationBar.hideBackButton();
        } else {
            navigationBar.showBackButton();
        }

        if (isOnLastStep()) {
            navigationBar.hideNextButton();
        } else {
            navigationBar.showNextButton();
        }
    }

    public void updateUI() {
        updateButtons();
        hideInactiveSteps();
        showCurrentStep();
    }

    public boolean isOnFirstStep() {
        return currentStep == 0;
    }

    public boolean isOnLastStep() {
        return currentStep == steps.size() - 1;
    }

    public void addStep(StepView step) {
        if (!steps.contains(configurationSummaryView)) {
            steps.add(configurationSummaryView); // Add summary view at the end if not already present
        }
        int summaryIndex = steps.indexOf(configurationSummaryView);
        steps.add(summaryIndex, step); // Add new step before summary step
        stepsContainer.getChildren().add(summaryIndex, step);
    }

    public void removeStep(StepView step) {
        if (step != configurationSummaryView) { // Prevent removing summary view
            steps.remove(step);
            stepsContainer.getChildren().remove(step);
        }
    }


    private void handleBackButtonClicked() {
        if (isOnFirstStep()) {
            return;
        }
        currentStep--;
        updateUI();
    }

    private void handleNextButtonClicked() {
        if (isOnLastStep()) {
            return;
        }
        currentStep++;
        updateUI();
    }

    public void handleCreateAppButtonClicked() {
        File selectedDirectory = ProjectInitializer.selectDirectory();
        projectConfigurationManager.buildProject(selectedDirectory);
    }

}
