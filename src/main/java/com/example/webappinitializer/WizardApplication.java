package com.example.webappinitializer;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.stage.Stage;
import org.kordamp.bootstrapfx.BootstrapFX;

import java.io.IOException;

public class WizardApplication extends Application {
    @Override
    public void start(Stage stage) throws IOException {
        FXMLLoader fxmlLoader = new FXMLLoader(WizardApplication.class.getResource("wizard-view.fxml"));
        Scene scene = new Scene(fxmlLoader.load(), 500, 600);
        scene.getStylesheets().add(BootstrapFX.bootstrapFXStylesheet());
        final String TITLE = "React Wizard";
        stage.setTitle(TITLE);
        stage.setScene(scene);
        stage.show();
    }

    public static void main(String[] args) {
        launch();
    }
}