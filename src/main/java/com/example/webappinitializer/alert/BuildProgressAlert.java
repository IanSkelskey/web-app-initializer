package com.example.webappinitializer.alert;

import javafx.scene.control.Alert;
import javafx.scene.control.ProgressBar;

public class BuildProgressAlert extends Alert {

    ProgressBar progressBar = new ProgressBar();

    public BuildProgressAlert() {
        super(AlertType.INFORMATION);
        setTitle("Build in progress");
        setHeaderText("Your app is being built");
        setContentText("Your app is being built. Please wait for a few seconds.");
        progressBar.setProgress(ProgressBar.INDETERMINATE_PROGRESS);
    }

}
