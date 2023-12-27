package com.example.webappinitializer.component;

import com.example.webappinitializer.form.TailwindConfigurationForm;

public class TailwindConfigurationView extends ModuleConfigurationView {
    public TailwindConfigurationView() {
        setName("Tailwind CSS Configuration");
        setDescription("Tailwind CSS is a utility-first CSS framework that will help you build your UI faster. You can configure it to your liking.");
        setCenter(new TailwindConfigurationForm());
    }
}
