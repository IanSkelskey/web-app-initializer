module com.example.webappinitializer {
    requires javafx.controls;
    requires javafx.fxml;

    requires org.controlsfx.controls;
    requires org.kordamp.ikonli.javafx;
    requires org.kordamp.bootstrapfx.core;
    requires com.google.gson;

    opens com.example.webappinitializer to javafx.fxml;
    exports com.example.webappinitializer;
}