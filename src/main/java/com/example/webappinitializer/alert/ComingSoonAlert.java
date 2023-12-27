package com.example.webappinitializer.alert;

import javafx.scene.control.Alert;

/**
 * This class is used to display an alert when a feature is not yet implemented.
 */
public class ComingSoonAlert extends Alert {

    /**
     * Creates a new instance of {@link ComingSoonAlert}.
     */
    public ComingSoonAlert() {
        super(AlertType.INFORMATION);
        setTitle("Coming Soon");
        setHeaderText("This feature is coming soon!");
        setContentText("This feature is coming soon!");
    }
}
