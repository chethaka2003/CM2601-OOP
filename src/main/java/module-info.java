module com.example.coursework {
    requires javafx.controls;
    requires javafx.fxml;
    requires java.xml;
    requires AnimateFX;
    requires java.sql;
    requires json.simple;


    opens com.example.coursework to javafx.fxml;
    exports com.example.coursework;
    exports com.example.coursework.controllers;
    opens com.example.coursework.controllers to javafx.fxml;
}