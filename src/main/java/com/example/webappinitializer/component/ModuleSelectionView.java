package com.example.webappinitializer.component;

import com.example.webappinitializer.form.ModuleSelectionForm;

/**
 * This is the module selection view.
 */
public class ModuleSelectionView extends StepView {
    /**
     * This is the constructor for the module selection view.
     */
    public ModuleSelectionView() {
        setName("Module Selection");
        setDescription("Select the modules you want to install in your app. You will be able to configure these in future steps.");
        setCenter(new ModuleSelectionForm());
    }
}
