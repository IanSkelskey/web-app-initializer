package com.example.webappinitializer.component;

import com.example.webappinitializer.form.TailwindConfigurationForm;

/**
 * This is the tailwind configuration view.
 */
public class TailwindConfigurationView extends ModuleConfigurationView {
    /**
     * This is the constructor for the tailwind configuration view.
     */
    public TailwindConfigurationView() {
        setName("Tailwind CSS Configuration");
        setDescription("Tailwind CSS is a utility-first CSS framework that will help you build your UI faster. You can configure it to your liking.");
        setCenter(new TailwindConfigurationForm());
    }
}
