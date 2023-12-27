package com.example.webappinitializer.component;

import com.example.webappinitializer.form.NameAndDescriptionForm;

/**
 * This is the name and description view.
 */
public class NameAndDescriptionView extends StepView {
    /**
     * This is the constructor for the name and description view.
     */
    public NameAndDescriptionView() {
        setName("Name and Description");
        setDescription("Enter the name and description of your app. This information will be used to name the app directory, populate the title and metadata in index.html and update the relevant fields in manifest.json and package.json");
        setCenter(new NameAndDescriptionForm());
    }
}
