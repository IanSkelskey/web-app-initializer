package com.example.webappinitializer.component;

import com.example.webappinitializer.util.EventManager;
import com.example.webappinitializer.util.EventType;
import javafx.scene.layout.StackPane;

import java.util.ArrayList;

public class StepContainer extends StackPane {

    private int currentStep = 0;
    private final ArrayList<StepView> steps = new ArrayList<>();

    public StepContainer() {
        super();
        EventManager.subscribe(EventType.BACK_BUTTON_CLICKED, (event) -> goToPreviousStep());
        EventManager.subscribe(EventType.NEXT_BUTTON_CLICKED, (event) -> goToNextStep());
    }

    public void addStep(StepView step) {
        steps.add(step);
        getChildren().add(step);
        updateUI();
    }

    public void removeStep(StepView step) {
        steps.remove(step);
        getChildren().remove(step);
        updateUI();
    }

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

    public void goToNextStep() {
        if (currentStep < steps.size() - 1) {
            currentStep++;
            updateUI();
            EventManager.publish(EventType.STEP_CHANGED, currentStep);
        }
    }

    public void goToPreviousStep() {
        if (currentStep > 0) {
            currentStep--;
            updateUI();
            EventManager.publish(EventType.STEP_CHANGED, currentStep);
        }
    }

    public boolean isOnFirstStep() {
        return currentStep == 0;
    }

    public boolean isOnLastStep() {
        return currentStep == steps.size() - 1;
    }

    private void updateUI() {
        hideInactiveSteps();
        showCurrentStep();
    }

    public StepView getCurrentStep() {
        return steps.get(currentStep);
    }
}