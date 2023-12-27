package com.example.webappinitializer.component;

import com.example.webappinitializer.config.ProjectConfigurationManager;
import com.example.webappinitializer.util.EventManager;
import com.example.webappinitializer.util.EventType;
import com.example.webappinitializer.util.Module;
import com.example.webappinitializer.util.ProjectInitializer;
import javafx.scene.layout.BorderPane;

import java.io.File;
import java.util.ArrayList;
import java.util.Map;

public class WizardView extends BorderPane {

    private final ProjectConfigurationManager projectConfigurationManager = new ProjectConfigurationManager();

    private final WizardNavigationBar navigationBar = new WizardNavigationBar();

    private final NameAndDescriptionView nameAndDescriptionView = new NameAndDescriptionView();
    private final StepContainer stepsContainer;

    private final ModuleSelectionView moduleSelectionView = new ModuleSelectionView();
    private final TailwindConfigurationView tailwindConfigurationView = new TailwindConfigurationView();
    private final PrettierConfigurationView prettierConfigurationView = new PrettierConfigurationView();
    private final ConfigurationSummaryView configurationSummaryView = new ConfigurationSummaryView();

    private final Map<Module, ModuleConfigurationView> moduleConfigurationViewMap = Map.of(
        Module.TAILWIND_CSS, tailwindConfigurationView,
        Module.PRETTIER, prettierConfigurationView
    );

    public WizardView() {
        super();
        ArrayList<StepView> introSteps = new ArrayList<>();
        introSteps.add(nameAndDescriptionView);
        introSteps.add(moduleSelectionView);
        ArrayList<StepView> outroSteps = new ArrayList<>();
        outroSteps.add(configurationSummaryView);
        stepsContainer = new StepContainer(introSteps, outroSteps);

        EventManager.subscribe(EventType.CREATE_APP_BUTTON_CLICKED, (event) -> handleCreateAppButtonClicked());
        EventManager.subscribe(EventType.MODULE_SELECTED, (module) -> {
            stepsContainer.addStep(moduleConfigurationViewMap.get((Module) module));
        });

        EventManager.subscribe(EventType.MODULE_DESELECTED, (module) -> {
            stepsContainer.removeStep(moduleConfigurationViewMap.get((Module) module));
        });

        setTop(navigationBar);
        setCenter(stepsContainer);
    }


    public void handleCreateAppButtonClicked() {
        File selectedDirectory = ProjectInitializer.selectDirectory();
        projectConfigurationManager.buildProject(selectedDirectory);
    }

}
