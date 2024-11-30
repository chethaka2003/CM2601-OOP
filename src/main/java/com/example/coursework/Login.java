package com.example.coursework;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.stage.Stage;
import javafx.stage.StageStyle;

import java.io.IOException;

//Starting application
public class Login extends Application {

    public void start(Stage primaryStage) throws IOException {
        FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("fxmls/login.fxml"));
        Scene scene = new Scene(fxmlLoader.load());
        scene.getStylesheets().add(getClass().getResource("stylesheets/login.css").toExternalForm());
        primaryStage.setScene(scene);
        primaryStage.setTitle("Login");
        //Removing the default control buttons
        primaryStage.initStyle(StageStyle.UNDECORATED);
        primaryStage.show();

    }

    public static void main(String[] args) {
        launch(args);

    }

}
