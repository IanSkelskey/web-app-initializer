package com.example.webappinitializer.component;

import com.example.webappinitializer.config.PrettierConfiguration;
import com.example.webappinitializer.config.ProjectConfiguration;
import com.example.webappinitializer.util.EventManager;
import com.example.webappinitializer.util.Modules;
import com.example.webappinitializer.util.ProjectInitializer;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.StackPane;
import java.io.File;
import java.util.ArrayList;

public class WizardView extends BorderPane {

    public ProjectConfiguration projectConfiguration = new ProjectConfiguration();

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
        addStep(nameAndDescriptionView);
        addStep(moduleSelectionView);
        EventManager.subscribe("backButtonClicked", (event) -> handleBackButtonClicked());
        EventManager.subscribe("nextButtonClicked", (event) -> handleNextButtonClicked());
        EventManager.subscribe("createAppButtonClicked", (event) -> handleCreateAppButtonClicked());
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
                navigationBar.showNextButton();
                navigationBar.hideCreateAppButton();
            }
        });

        EventManager.subscribe("module-deselected", (module) -> {
            if (module == Modules.TAILWIND_CSS) {
                projectConfiguration.removeModule(Modules.TAILWIND_CSS);
                removeStep(tailwindConfigurationView);
            } else if (module == Modules.PRETTIER) {
                projectConfiguration.removeModule(Modules.PRETTIER);
                removeStep(prettierConfigurationView);
            } else if (module == Modules.FRAMER_MOTION) {
                projectConfiguration.removeModule(Modules.FRAMER_MOTION);
            }
            if (currentStep == steps.size() - 1) {
                navigationBar.hideNextButton();
                navigationBar.showCreateAppButton();
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
        steps.get(currentStep).setVisible(false);
        steps.get(currentStep - 1).setVisible(true);
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
        steps.get(currentStep).setVisible(false);
        steps.get(currentStep + 1).setVisible(true);
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
        projectConfiguration.build(selectedDirectory);
    }

}
