module org.example._122025 {
    requires javafx.controls;
    requires javafx.fxml;
    requires java.sql;


    opens org.example._122025 to javafx.fxml;
    exports org.example._122025;
    exports controller;
    opens controller to javafx.fxml;
    opens model to javafx.base;
}