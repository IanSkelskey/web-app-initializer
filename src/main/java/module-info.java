module com.example.webappinitializer {
    requires javafx.controls;
    requires javafx.fxml;

    requires org.controlsfx.controls;
    requires org.kordamp.ikonli.javafx;
    requires org.kordamp.bootstrapfx.core;
    requires com.google.gson;
    requires org.jsoup;

    exports com.example.webappinitializer;
    exports com.example.webappinitializer.controller;
    exports com.example.webappinitializer.component to javafx.fxml;
    exports com.example.webappinitializer.form to javafx.fxml;

    opens com.example.webappinitializer to javafx.fxml;
    opens com.example.webappinitializer.controller to javafx.fxml;
    opens com.example.webappinitializer.component to javafx.fxml;
    opens com.example.webappinitializer.form to javafx.fxml;
}
