package com.example.coursework.controllers;

import javafx.fxml.FXML;
import javafx.scene.Node;
import javafx.scene.input.MouseEvent;
import javafx.scene.input.MouseEvent;
import javafx.stage.Stage;

public class LoginController {

    public Stage stage;


        @FXML
        void close(MouseEvent event) {
            stage = (Stage)((Node)event.getSource()).getScene().getWindow();
            stage.close();
        }

}

