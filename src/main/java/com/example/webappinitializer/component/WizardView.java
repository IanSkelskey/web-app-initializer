package com.example.webappinitializer.component;

import com.example.webappinitializer.config.ProjectConfigurationManager;
import com.example.webappinitializer.util.EventManager;
import com.example.webappinitializer.util.EventType;
import com.example.webappinitializer.util.Module;
import com.example.webappinitializer.util.ProjectInitializer;
import javafx.scene.layout.BorderPane;

import java.io.File;

public class WizardView extends BorderPane {

    private final ProjectConfigurationManager projectConfigurationManager = new ProjectConfigurationManager();

    private final WizardNavigationBar navigationBar = new WizardNavigationBar();
    private final StepContainer stepsContainer = new StepContainer();

    private final NameAndDescriptionView nameAndDescriptionView = new NameAndDescriptionView();
    private final ModuleSelectionView moduleSelectionView = new ModuleSelectionView();
    private final TailwindConfigurationView tailwindConfigurationView = new TailwindConfigurationView();
    private final PrettierConfigurationView prettierConfigurationView = new PrettierConfigurationView();
    private final ConfigurationSummaryView configurationSummaryView = new ConfigurationSummaryView();

    public WizardView() {
        super();
        tailwindConfigurationView.setVisible(false);
        prettierConfigurationView.setVisible(false);
        stepsContainer.addStep(nameAndDescriptionView);
        stepsContainer.addStep(moduleSelectionView);
        stepsContainer.addStep(configurationSummaryView);
        EventManager.subscribe(EventType.BACK_BUTTON_CLICKED, (event) -> handleBackButtonClicked());
        EventManager.subscribe(EventType.NEXT_BUTTON_CLICKED, (event) -> handleNextButtonClicked());
        EventManager.subscribe(EventType.CREATE_APP_BUTTON_CLICKED, (event) -> handleCreateAppButtonClicked());
        EventManager.subscribe(EventType.MODULE_SELECTED, (module) -> {
            if (module == Module.TAILWIND_CSS) {
                stepsContainer.addStep(tailwindConfigurationView);
            } else if (module == Module.PRETTIER) {
                stepsContainer.addStep(prettierConfigurationView);
            }
            updateUI();
        });

        EventManager.subscribe(EventType.MODULE_DESELECTED, (module) -> {
            if (module == Module.TAILWIND_CSS) {
                stepsContainer.removeStep(tailwindConfigurationView);
            } else if (module == Module.PRETTIER) {
                stepsContainer.removeStep(prettierConfigurationView);
            }
            updateUI();
        });

        setTop(navigationBar);
        setCenter(stepsContainer);
        updateUI();
    }

    public void updateButtons() {
        if (stepsContainer.isOnFirstStep()) {
            navigationBar.hideBackButton();
        } else {
            navigationBar.showBackButton();
        }

        if (stepsContainer.isOnLastStep()) {
            navigationBar.hideNextButton();
        } else {
            navigationBar.showNextButton();
        }
    }

    public void updateUI() {
        updateButtons();
    }


    private void handleBackButtonClicked() {
        System.out.println("Back button clicked");
        updateUI();
    }

    private void handleNextButtonClicked() {
        System.out.println("Next button clicked");
        updateUI();
    }

    public void handleCreateAppButtonClicked() {
        File selectedDirectory = ProjectInitializer.selectDirectory();
        projectConfigurationManager.buildProject(selectedDirectory);
    }

}
