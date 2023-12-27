package com.example.webappinitializer.component;

import com.example.webappinitializer.util.EventManager;
import com.example.webappinitializer.util.EventType;
import javafx.scene.layout.StackPane;

import java.util.ArrayList;

public class StepContainer extends StackPane {

    private int currentStep = 0;
    private final ArrayList<StepView> introSteps = new ArrayList<>();
    private final ArrayList<StepView> steps = new ArrayList<>();
    private final ArrayList<StepView> outroSteps = new ArrayList<>();

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

    private ArrayList<StepView> getSteps() {
        ArrayList<StepView> masterSteps = new ArrayList<>();
        masterSteps.addAll(introSteps);
        masterSteps.addAll(steps);
        masterSteps.addAll(outroSteps);
        return masterSteps;
    }

    public void addStep(StepView step) {
        steps.add(step);
        replaceAllSteps(getSteps());
        updateUI();
        EventManager.publish(EventType.STEP_COUNT_CHANGED, getSteps().size());
    }

    public void removeStep(StepView step) {
        steps.remove(step);
        replaceAllSteps(getSteps());
        updateUI();
        EventManager.publish(EventType.STEP_COUNT_CHANGED, getSteps().size());
    }

    public void showCurrentStep() {
        getSteps().get(currentStep).setVisible(true);
    }

    public void hideInactiveSteps() {
        for (int i = 0; i < getSteps().size(); i++) {
            if (i != currentStep) {
                getSteps().get(i).setVisible(false);
            }
        }
    }

    private void replaceAllSteps(ArrayList<StepView> steps) {
        getChildren().remove(0, getChildren().size());
        getChildren().addAll(steps);
    }

    public void goToNextStep() {
        if (isOnLastStep()) {
            return;
        }
        currentStep++;
        updateUI();
        EventManager.publish(EventType.STEP_CHANGED, currentStep);

    }

    public void goToPreviousStep() {
        if (isOnFirstStep()) {
            return;
        }
        currentStep--;
        updateUI();
        EventManager.publish(EventType.STEP_CHANGED, currentStep);

    }

    public boolean isOnFirstStep() {
        return currentStep == 0;
    }

    public boolean isOnLastStep() {
        return currentStep == getSteps().size() - 1;
    }

    private void updateUI() {
        hideInactiveSteps();
        showCurrentStep();
    }

    public StepView getCurrentStep() {
        return getSteps().get(currentStep);
    }
}
