package com.example.coursework.controllers;

import animatefx.animation.FadeIn;
import com.example.coursework.Alert;
import com.example.coursework.DbConnector;
import javafx.beans.Observable;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.control.CheckBox;
import javafx.scene.control.TextField;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.Pane;

import javafx.stage.Stage;

import java.io.IOException;
import java.net.URL;
import java.sql.SQLException;
import java.util.ResourceBundle;

public class LoginController implements Initializable {

    Stage stage;

    @FXML
    private CheckBox terms;

    @FXML
    private Pane CreateAccountPg;

    @FXML
    private Pane LoginPage;

    @FXML
    private TextField email;

    @FXML
    private TextField firstName;

    @FXML
    private TextField lastName;

    @FXML
    private TextField pwd;

    @FXML
    private TextField pwd2;

    @FXML
    private TextField userName;


    @FXML
    void back(MouseEvent event) {
        new FadeIn(LoginPage).play();
        LoginPage.toFront();
    }

    //when pressed login button in signup page
    @FXML
    void changePage(MouseEvent event) {
        new FadeIn(LoginPage).play();
        LoginPage.toFront();
    }

    //Avoiding the mistakes which can happen when entering the data
    @Override
    public void initialize(URL url, ResourceBundle resourceBundle){

        //checking the firstname

        firstName.textProperty().addListener(new ChangeListener<String>(){
            @Override
            public void changed(ObservableValue<? extends String> observable, String oldValue, String newValue) {
                if (newValue.matches(".*\\d.*")){
                    //giving alert
                    Alert.giveWarningAlert("Incorrect input","You can't enter numbers as firstname");
                    firstName.setText(oldValue);

                }
                else if(newValue.length()>30){
                    Alert.giveWarningAlert("Invalid input","You can't enter more than 30 characters as firstname");
                    firstName.setText(oldValue);
                }
                else {
                    firstName.setText(newValue);
                }
            }
        });

        //checking the lastname

        lastName.textProperty().addListener(new ChangeListener<String>(){
            @Override
            public void changed(ObservableValue<? extends String> observable, String oldValue, String newValue) {
                if (newValue.matches(".*\\d.*")){
                    //giving alert
                    Alert.giveWarningAlert("Incorrect input","You can't enter numbers as lastname");
                    lastName.setText(oldValue);

                }
                else if(newValue.length()>30){
                    Alert.giveWarningAlert("Invalid input","You can't enter more than 30 characters as lastname");
                    lastName.setText(oldValue);
                }
                else {
                    lastName.setText(newValue);
                }
            }
        });

        //Checking the username

        userName.textProperty().addListener(new ChangeListener<String>(){
            @Override
            public void changed(ObservableValue<? extends String> observable, String oldValue, String newValue) {

                if(newValue.length()>40){
                    Alert.giveWarningAlert("Invalid input","You can't enter more than 30 characters as User name");
                    userName.setText(oldValue);
                }
                else {
                    userName.setText(newValue);
                }
            }
        });

        //checking the passwords

        pwd.textProperty().addListener(new ChangeListener<String>(){
            @Override
            public void changed(ObservableValue<? extends String> observable, String oldValue, String newValue) {
                if(newValue.length()>30){
                    Alert.giveWarningAlert("Invalid input","You can't enter more than 30 characters as password");
                    pwd.setText(oldValue);
                }
                else {
                    pwd.setText(newValue);
                }
            }
        });

        //Checking the emails
        email.textProperty().addListener(new ChangeListener<String>(){
            @Override
            public void changed(ObservableValue<? extends String> observable, String oldValue, String newValue) {

                if(newValue.length()>30){
                    Alert.giveWarningAlert("Invalid input","You can't enter more than 30 characters as email");
                }
                else {
                    email.setText(newValue);
                }
            }
        });




    }

    //Closing button
    @FXML
    void close(MouseEvent event) {
        stage = (Stage) ((Node)event.getSource()).getScene().getWindow();
        stage.close();
    }

    @FXML
    void close2(MouseEvent event) {
        stage = (Stage) ((Node)event.getSource()).getScene().getWindow();
        stage.close();

    }

    @FXML
    void signupBtn(MouseEvent event) {
        new FadeIn(CreateAccountPg).play();
        CreateAccountPg.toFront();
    }

    //Creates account
    @FXML
    void createAccount(MouseEvent event) {
        String password = pwd.getText();
        String verPassword = pwd2.getText();
        if (!password.equals(verPassword)) {
            Alert.giveWarningAlert("Password mismatch", "Password you entered are not match with each other");
        } else if (firstName.getText().isEmpty() || lastName.getText().isEmpty() || pwd.getText().isEmpty() || userName.getText().isEmpty() || email.getText().isEmpty()) {
            Alert.giveWarningAlert("Empty field", "Please fill all the details in here");
        } else if (!terms.isSelected()) {
            Alert.giveWarningAlert("Terms and conditions", "You have not agreed to out terms and conditions");
        }
        else if(DbConnector.userNameValidityCheck(userName.getText())){
            Alert.giveWarningAlert("User name error", "Username is already in use");
        }

        else if (DbConnector.userEmailValidityCheck(email.getText())){
            Alert.giveWarningAlert("Email error", "Email is already in use");
        }
        else {
            Alert.giveConfirmAlert("Successfully updated","You have successfully created a account please remember your login details");
            DbConnector.addDetails(firstName.getText(), lastName.getText(), userName.getText(), pwd.getText(), email.getText());
            firstName.clear();
            lastName.clear();
            email.clear();
            pwd.clear();
            pwd2.clear();
            userName.clear();


        }
    }

    @FXML
    void login(MouseEvent event) throws IOException {
        FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("/com/example/coursework/fxmls/Home.fxml"));
        Scene scene = new Scene(fxmlLoader.load());
        scene.getStylesheets().add(getClass().getResource("/com/example/coursework/stylesheets/Home.css").toExternalForm());
        stage = (Stage)((Node)event.getSource()).getScene().getWindow();
        stage.setScene(scene);
        stage.show();

    }

}
