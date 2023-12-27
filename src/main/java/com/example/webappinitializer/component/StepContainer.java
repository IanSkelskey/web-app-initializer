package com.example.webappinitializer.component;

import com.example.webappinitializer.util.EventManager;
import com.example.webappinitializer.util.EventType;
import javafx.scene.layout.StackPane;

import java.util.ArrayList;

/**
 * A container for all the steps in the wizard.
 * <p>
 * This class is used to manage the steps in the wizard.
 * It is used to add and remove steps, and to navigate between steps.
 * </p>
 */
public class StepContainer extends StackPane {


    private int currentStep = 0;
    private final ArrayList<StepView> introSteps = new ArrayList<>();
    private final ArrayList<StepView> steps = new ArrayList<>();
    private final ArrayList<StepView> outroSteps = new ArrayList<>();

    /**
     * Constructor
     * @param introSteps The steps to be shown at the beginning of the wizard.
     * @param outroSteps The steps to be shown at the end of the wizard.
     */
    public StepContainer(ArrayList<StepView> introSteps, ArrayList<StepView> outroSteps) {
        super();
        this.introSteps.addAll(introSteps);
        this.outroSteps.addAll(outroSteps);
        getChildren().addAll(introSteps);
        getChildren().addAll(outroSteps);
        updateUI();
        EventManager.publish(EventType.STEP_COUNT_CHANGED, getSteps().size());
        EventManager.subscribe(EventType.BACK_BUTTON_CLICKED, (event) -> goToPreviousStep());
        EventManager.subscribe(EventType.NEXT_BUTTON_CLICKED, (event) -> goToNextStep());
    }

    /**
     * Get all the steps in the wizard.
     * @return An ArrayList of all the steps in the wizard.
     */
    private ArrayList<StepView> getSteps() {
        ArrayList<StepView> masterSteps = new ArrayList<>();
        masterSteps.addAll(introSteps);
        masterSteps.addAll(steps);
        masterSteps.addAll(outroSteps);
        return masterSteps;
    }

    /**
     * Add a step to the wizard.
     * @param step The step to add.
     */
    public void addStep(StepView step) {
        steps.add(step);
        replaceAllSteps(getSteps());
        updateUI();
        EventManager.publish(EventType.STEP_COUNT_CHANGED, getSteps().size());
    }

    /**
     * Remove a step from the wizard.
     * @param step The step to remove.
     */
    public void removeStep(StepView step) {
        steps.remove(step);
        replaceAllSteps(getSteps());
        updateUI();
        EventManager.publish(EventType.STEP_COUNT_CHANGED, getSteps().size());
    }

    /**
     * Show the current step.
     */
    public void showCurrentStep() {
        getSteps().get(currentStep).setVisible(true);
    }

    /**
     * Hide all the steps except the current step.
     */
    public void hideInactiveSteps() {
        for (int i = 0; i < getSteps().size(); i++) {
            if (i != currentStep) {
                getSteps().get(i).setVisible(false);
            }
        }
    }

    /**
     * Replace all the steps in the wizard.
     * @param steps The steps to replace the current steps with.
     */
    private void replaceAllSteps(ArrayList<StepView> steps) {
        getChildren().remove(0, getChildren().size());
        getChildren().addAll(steps);
    }

    /**
     * Go to the next step in the wizard.
     */
    public void goToNextStep() {
        if (isOnLastStep()) {
            return;
        }
        currentStep++;
        updateUI();
        EventManager.publish(EventType.STEP_CHANGED, currentStep);

    }

    /**
     * Go to the previous step in the wizard.
     */
    public void goToPreviousStep() {
        if (isOnFirstStep()) {
            return;
        }
        currentStep--;
        updateUI();
        EventManager.publish(EventType.STEP_CHANGED, currentStep);

    }

    /**
     * @return True if the current step is the first step in the wizard.
     */
    public boolean isOnFirstStep() {
        return currentStep == 0;
    }

    /**
     * @return True if the current step is the last step in the wizard.
     */
    public boolean isOnLastStep() {
        return currentStep == getSteps().size() - 1;
    }

    /**
     * Update the UI.
     */
    private void updateUI() {
        hideInactiveSteps();
        showCurrentStep();
    }

    /**
     * @return The current step.
     */
    public StepView getCurrentStep() {
        return getSteps().get(currentStep);
    }
}
