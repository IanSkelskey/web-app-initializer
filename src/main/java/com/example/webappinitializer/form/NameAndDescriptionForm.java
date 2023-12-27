package com.example.webappinitializer.form;

import com.example.webappinitializer.util.EventManager;
import javafx.geometry.Insets;
import javafx.scene.control.Label;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;

import static javafx.geometry.Pos.CENTER;

public class NameAndDescriptionForm extends VBox {

    private String directoryName;
    private final Label directoryNameLabel = new Label();
    private final TextField shortNameTextField = new TextField(), fullNameTextField = new TextField();
    private TextArea descriptionTextArea = new TextArea();

    public NameAndDescriptionForm() {
        shortNameTextField.textProperty().addListener((observable, oldValue, newValue) -> {
            directoryName = newValue.toLowerCase().replaceAll("\\s+", "-");
            directoryNameLabel.setText(directoryName);
            EventManager.publish("appShortNameChanged", newValue);
            EventManager.publish("directoryNameChanged", directoryName);
        });
        fullNameTextField.textProperty().addListener((observable, oldValue, newValue) -> {
            EventManager.publish("appFullNameChanged", newValue);
        });
        descriptionTextArea.textProperty().addListener((observable, oldValue, newValue) -> {
            EventManager.publish("appDescriptionChanged", newValue);
        });
        setAlignment(CENTER);
        setSpacing(20);
        setPadding(new Insets(20));

        HBox directoryNameHbox = new HBox();
        directoryNameHbox.setSpacing(20);
        directoryNameHbox.getChildren().addAll(new Label("Directory Name: "), directoryNameLabel);
        getChildren().add(directoryNameHbox);

        getChildren().add(new Label("Short Name: "));
        getChildren().add(shortNameTextField);
        getChildren().add(new Label("Full Name: "));
        getChildren().add(fullNameTextField);
        getChildren().add(new Label("Description: "));
        getChildren().add(descriptionTextArea);
    }
}
