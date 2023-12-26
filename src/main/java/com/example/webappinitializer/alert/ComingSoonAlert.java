package com.example.webappinitializer.alert;

import javafx.scene.control.Alert;

public class ComingSoonAlert extends Alert {
    public ComingSoonAlert() {
        super(AlertType.INFORMATION);
        setTitle("Coming Soon");
        setHeaderText("This feature is coming soon!");
        setContentText("This feature is coming soon!");
    }
}
