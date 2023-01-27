module com.example.finaldotsandboxes {
    requires javafx.controls;
    requires javafx.fxml;
    requires javafx.media;
    requires java.desktop;

    opens com.example.DB to javafx.fxml;
    exports com.example.DB;
}