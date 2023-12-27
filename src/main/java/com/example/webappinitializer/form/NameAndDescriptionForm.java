package com.example.webappinitializer.form;

import com.example.webappinitializer.util.EventManager;
import com.example.webappinitializer.util.EventType;
import javafx.geometry.Insets;
import javafx.scene.control.Label;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;

import static javafx.geometry.Pos.CENTER;

/**
 * This is the form for entering the name and description.
 */
public class NameAndDescriptionForm extends VBox {

    private String directoryName;
    private final Label directoryNameLabel = new Label();
    private final TextField shortNameTextField = new TextField(), fullNameTextField = new TextField();
    private TextArea descriptionTextArea = new TextArea();

    /**
     * Constructor
     * This is the constructor of the name and description form.
     * It sets up the form.
     * It also sets up the event listeners for the text fields.
     */
    public NameAndDescriptionForm() {
        shortNameTextField.textProperty().addListener((observable, oldValue, newValue) -> {
            directoryName = newValue.toLowerCase().replaceAll("\\s+", "-");
            directoryNameLabel.setText(directoryName);
            EventManager.publish(EventType.SHORT_NAME_CHANGED, newValue);
            EventManager.publish(EventType.DIRECTORY_NAME_CHANGED, directoryName);
        });
        fullNameTextField.textProperty().addListener((observable, oldValue, newValue) -> {
            EventManager.publish(EventType.FULL_NAME_CHANGED, newValue);
        });
        descriptionTextArea.textProperty().addListener((observable, oldValue, newValue) -> {
            EventManager.publish(EventType.DESCRIPTION_CHANGED, newValue);
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
