package com.example.webappinitializer.component;

import com.example.webappinitializer.config.PrettierConfiguration;
import com.example.webappinitializer.config.ProjectConfiguration;
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
        EventManager.subscribe("backButtonClicked", (event) -> handleBackButtonClicked());
        EventManager.subscribe("nextButtonClicked", (event) -> handleNextButtonClicked());
        EventManager.subscribe("createAppButtonClicked", (event) -> handleCreateAppButtonClicked());
        EventManager.subscribe("module-selected", (module) -> {
            if (module == Modules.TAILWIND_CSS) {
                addStep(tailwindConfigurationView);
            } else if (module == Modules.PRETTIER) {
                addStep(prettierConfigurationView);
            }
            if (currentStep < steps.size() - 1) {
                navigationBar.showNextButton();
                navigationBar.hideCreateAppButton();
            }
        });

        EventManager.subscribe("module-deselected", (module) -> {
            if (module == Modules.TAILWIND_CSS) {
                removeStep(tailwindConfigurationView);
            } else if (module == Modules.PRETTIER) {
                removeStep(prettierConfigurationView);
            }
            if (currentStep == steps.size() - 1) {
                navigationBar.hideNextButton();
                navigationBar.showCreateAppButton();
            }
        });

        setTop(navigationBar);
        setCenter(stepsContainer);
        hideInactiveSteps();
        showCurrentStep();
    }

    public void addStep(StepView step) {
        steps.add(step);
        stepsContainer.getChildren().add(step);
    }

    public void removeStep(StepView step) {
        steps.remove(step);
        stepsContainer.getChildren().remove(step);
    }

    private void handleBackButtonClicked() {
        if (currentStep == 0) {
            return;
        }
        currentStep--;
        if (currentStep == 0) {
            navigationBar.hideBackButton();
        }
        if (currentStep < steps.size() - 1) {
            navigationBar.showNextButton();
            navigationBar.hideCreateAppButton();
        }

        hideInactiveSteps();
        showCurrentStep();

    }

    private void handleNextButtonClicked() {
        if (currentStep == steps.size() - 1) {
            return;
        }
        currentStep++;
        if (currentStep > 0) {
            navigationBar.showBackButton();
        }
        if (currentStep == steps.size() - 1) {
            navigationBar.hideNextButton();
            navigationBar.showCreateAppButton();
        }

        hideInactiveSteps();
        showCurrentStep();
    }

    public void handleCreateAppButtonClicked() {
        File selectedDirectory = ProjectInitializer.selectDirectory();
        projectConfigurationManager.buildProject(selectedDirectory);
    }

}
