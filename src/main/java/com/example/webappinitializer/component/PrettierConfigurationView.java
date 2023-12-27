package com.example.webappinitializer.component;

import com.example.webappinitializer.form.PrettierConfigurationForm;

public class PrettierConfigurationView extends ModuleConfigurationView {
    public PrettierConfigurationView() {
        setName("Prettier Configuration");
        setDescription("Prettier is a code formatter that will help you keep your code consistent. You can configure it to your liking.");
        setCenter(new PrettierConfigurationForm());
    }
}
