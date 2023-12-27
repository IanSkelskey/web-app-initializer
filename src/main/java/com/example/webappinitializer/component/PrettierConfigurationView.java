package com.example.webappinitializer.component;

import com.example.webappinitializer.form.PrettierConfigurationForm;

/**
 * This is the prettier configuration view.
 */
public class PrettierConfigurationView extends ModuleConfigurationView {
    /**
     * This is the constructor for the prettier configuration view.
     */
    public PrettierConfigurationView() {
        setName("Prettier Configuration");
        setDescription("Prettier is a code formatter that will help you keep your code consistent. You can configure it to your liking.");
        setCenter(new PrettierConfigurationForm());
    }
}
