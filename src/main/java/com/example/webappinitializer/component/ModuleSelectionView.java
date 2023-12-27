package com.example.webappinitializer.component;

import com.example.webappinitializer.form.ModuleSelectionForm;

public class ModuleSelectionView extends StepView {
    public ModuleSelectionView() {
        setName("Module Selection");
        setDescription("Select the modules you want to install in your app. You will be able to configure these in future steps.");
        setCenter(new ModuleSelectionForm());
    }
}
