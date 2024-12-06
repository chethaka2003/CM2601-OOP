package com.example.coursework.controllers;

import animatefx.animation.FadeIn;
import com.example.coursework.Alert;
import com.example.coursework.Api_connection;
import com.example.coursework.DbConnector;
import com.example.coursework.User;
import javafx.application.Platform;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.event.Event;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.Pane;
import javafx.scene.text.Text;
import javafx.stage.Stage;
import weka.classifiers.Classifier;
import weka.classifiers.bayes.NaiveBayes;
import weka.core.*;
import weka.filters.Filter;
import weka.filters.unsupervised.attribute.StringToWordVector;

import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URI;
import java.net.URISyntaxException;
import java.net.URL;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import java.util.ResourceBundle;

public class AdministraorViewController implements Initializable {

    //List to store the main panes
    List<Pane> panes = new ArrayList<>();

    //New Image PAth
    String correct_image;

    //Boolean value to check content
    Boolean isContentUpdated = false;

    //Boolean value to check generate new Cat
    Boolean isNewCatGenerated = false;

    //Boolean value to check image is OK
    boolean isImageUpdated = false;

    //API connection URL
    public static URL url;


    @FXML
    private Pane add_news_pn;

    @FXML
    private Pane del_nws_pn;

    @FXML
    private Pane del_usr_pn;

    @FXML
    private Pane updt_news_pn;

    @FXML
    private Pane updt_usr_pn;

    @FXML
    private TextField new_Email;

    @FXML
    private TextField new_Fname;

    @FXML
    private TextField new_Lname;

    @FXML
    private TextField new_Uname;

    @FXML
    private TextField new_pwd;

    @FXML
    private TextField updt_email;

    @FXML
    private Button updt_usr_btn;

    @FXML
    private Pane user_updt_pn;


    @FXML
    private Text view_Lname;

    @FXML
    private Text view_Uname;

    @FXML
    private Text view_fname;

    @FXML
    private Text view_mail;

    @FXML
    private Text view_pwd;

    @FXML
    private Text view_uid;

    @FXML
    private TextField rem_user_email;

    @FXML
    private Pane rem_user_pane;

    @FXML
    private Text view_rem_fname;

    @FXML
    private Text view_rem_id;

    @FXML
    private Text view_rem_lname;

    @FXML
    private Text view_rem_mail;

    @FXML
    private Text view_rem_pwd;

    @FXML
    private Text view_rem_uname;

    //Add news Pane

    @FXML
    private TextArea new__content;

    @FXML
    private TextField new_api_url;

    @FXML
    private TextField new_author;

    @FXML
    private TextField new_image;

    @FXML
    private Label pred_cat;

    @FXML
    private TextArea new_title;

    @FXML
    private TextField new_category;

    //Update News Pane

    @FXML
    private Text view_author;

    @FXML
    private Text view_cat;

    @FXML
    private Text view_content;

    @FXML
    private ImageView view_image;

    @FXML
    private Text view_title;

    @FXML
    private TextField news_id;

    @FXML
    private TextField updtd_URL;

    @FXML
    private TextField updtd_author;

    @FXML
    private TextArea updtd_cntn;

    @FXML
    private TextField updtd_title;

    @FXML
    private Pane updt_news_mini_pane;

    //Delete news

    @FXML
    private Text view_del_news_auth;

    @FXML
    private Label view_del_news_cat;

    @FXML
    private Text view_del_news_con;

    @FXML
    private Text view_del_news_title;

    @FXML
    private TextField del_news_id;

    @FXML
    private Pane del_news_mini_pane;

    @FXML
    private ImageView del_view_img;

    //userTable View

    // Declare TableView and TableColumns
    @FXML
    private TableView<User> usersTable;

    @FXML
    private TableColumn<User, String> idColumn;

    @FXML
    private TableColumn<User, String> firstNameColumn;

    @FXML
    private TableColumn<User, String> lastNameColumn;

    @FXML
    private TableColumn<User, String> emailColumn;

    @FXML
    private TableColumn<User, String> userNameColumn;

    @FXML
    private TableColumn<User, String> passwordColumn;

    @FXML
    private Pane all_user_pn;



    //Lists to save all user details
    List<String> ids = new ArrayList<>();
    List<String> f_names = new ArrayList<>();
    List<String> l_names = new ArrayList<>();
    List<String> u_names = new ArrayList<>();
    List<String> pwds = new ArrayList<>();
    List<String> emails = new ArrayList<>();



    String new_fname;
    String new_laname;
    String new_password;
    String new_userName;
    String new_mail;



    @Override
    public void initialize(URL location, ResourceBundle resources){
        panes.add(add_news_pn);
        panes.add(del_nws_pn);
        panes.add(del_usr_pn);
        panes.add(updt_news_pn);
        panes.add(updt_usr_pn);
        panes.add(all_user_pn);

        new_Fname.textProperty().addListener(new ChangeListener<String>() {
            @Override
            public void changed(ObservableValue<? extends String> observable, String oldValue, String newValue) {
                if (newValue.matches(".*\\d.*")){
                    //giving alert
                    Alert.giveWarningAlert("Incorrect input","You can't enter numbers as firstname");
                    new_Fname.setText(oldValue);

                }
                else if(newValue.length()>30){
                    Alert.giveWarningAlert("Invalid input","You can't enter more than 30 characters as firstname");
                    new_Fname.setText(oldValue);
                }
                else {
                    new_Fname.setText(newValue);
                    view_fname.setText(newValue);
                    new_fname = new_Fname.getText();
                }
            }
        });


        new_Uname.textProperty().addListener(new ChangeListener<String>() {
            @Override
            public void changed(ObservableValue<? extends String> observable, String oldValue, String newValue) {
                if(newValue.length()>40){
                    Alert.giveWarningAlert("Invalid input","You can't enter more than 30 characters as User name");
                    new_Uname.setText(oldValue);
                }
                else {
                    new_Uname.setText(newValue);
                    new_userName = new_Uname.getText();
                    view_Uname.setText(newValue);
                }
            }
        });

        new_Email.textProperty().addListener(new ChangeListener<String>() {
            @Override
            public void changed(ObservableValue<? extends String> observable, String oldValue, String newValue) {
                if(newValue.length()>30){
                    Alert.giveWarningAlert("Invalid input","You can't enter more than 30 characters as email");
                }
                else {
                    new_Email.setText(newValue);
                    view_mail.setText(newValue);
                }
            }
        });

        new_Lname.textProperty().addListener(new ChangeListener<String>() {
            @Override
            public void changed(ObservableValue<? extends String> observable, String oldValue, String newValue) {
                if (newValue.matches(".*\\d.*")){
                    //giving alert
                    Alert.giveWarningAlert("Incorrect input","You can't enter numbers as lastname");
                    new_Lname.setText(oldValue);

                }
                else if(newValue.length()>30){
                    Alert.giveWarningAlert("Invalid input","You can't enter more than 30 characters as lastname");
                    new_Lname.setText(oldValue);
                }
                else {
                    new_Lname.setText(newValue);
                    view_Lname.setText(newValue);
                }
            }
        });

        new_pwd.textProperty().addListener(new ChangeListener<String>() {
            @Override
            public void changed(ObservableValue<? extends String> observable, String oldValue, String newValue) {
                if(newValue.length()>30){
                    Alert.giveWarningAlert("Invalid input","You can't enter more than 30 characters as password");
                    new_pwd.setText(oldValue);
                }
                else {
                    new_pwd.setText(newValue);
                    view_pwd.setText(newValue);
                }
            }
        });

        updtd_cntn.textProperty().addListener(new ChangeListener<String>() {
            @Override
            public void changed(ObservableValue<? extends String> observable, String oldValue, String newValue) {
                if(newValue.length()>300){
                    Alert.giveWarningAlert("Invalid input","You can't enter more than 300 characters as content");
                    updtd_cntn.setText(oldValue);
                    isContentUpdated = true;
                }
                else {
                    updtd_cntn.setText(newValue);
                    view_content.setText(newValue);
                }
            }
        });

        updtd_title.textProperty().addListener(new ChangeListener<String>() {
            @Override
            public void changed(ObservableValue<? extends String> observable, String oldValue, String newValue) {
                if(newValue.length()>200){
                    Alert.giveWarningAlert("Invalid input","You can't enter more than 200 characters as title");
                    updtd_title.setText(oldValue);
                }
                else {
                    updtd_title.setText(newValue);
                    view_title.setText(newValue);
                }
            }
        });

        updtd_author.textProperty().addListener(new ChangeListener<String>() {
            @Override
            public void changed(ObservableValue<? extends String> observable, String oldValue, String newValue) {
                if(newValue.length()>200){
                    Alert.giveWarningAlert("Invalid input","You can't enter more than 200 characters as author");
                    updtd_author.setText(oldValue);
                }
                else {
                    updtd_author.setText(newValue);
                    view_author.setText(newValue);
                }
            }
        });




    }

    @FXML
    void add_news_clicked(MouseEvent event) {
        hideSidePanes();
        hideOtherPanes(add_news_pn);
        add_news_pn.setVisible(true);
        new FadeIn(add_news_pn).play();
        add_news_pn.toFront();
    }

    @FXML
    void del_news_clked(MouseEvent event) {
        hideSidePanes();
        hideOtherPanes(del_nws_pn);
        del_nws_pn.setVisible(true);
        new FadeIn(del_nws_pn).play();
        del_nws_pn.toFront();
    }

    @FXML
    void dshbrd_clked(MouseEvent event) {
        hideSidePanes();
        hideOtherPanes(add_news_pn);
        add_news_pn.setVisible(true);
        new FadeIn(add_news_pn).play();
        add_news_pn.toFront();
    }

    @FXML
    void hlp_spt_clked(MouseEvent event) {
        hideSidePanes();
    }

    @FXML
    void rem_user_clicked(MouseEvent event) {
        hideSidePanes();
        hideOtherPanes(del_usr_pn);
        del_usr_pn.setVisible(true);
        new FadeIn(del_usr_pn).play();
        del_usr_pn.toFront();
    }

    @FXML
    void up_news_clked(MouseEvent event) {
        hideSidePanes();
        hideOtherPanes(updt_news_pn);
        updt_news_pn.setVisible(true);
        new FadeIn(updt_news_pn).play();
        updt_news_pn.toFront();
    }

    @FXML
    void update_user(MouseEvent event) {
        hideSidePanes();
        hideOtherPanes(updt_usr_pn);
        updt_usr_pn.setVisible(true);
        new FadeIn(updt_usr_pn).play();
        updt_usr_pn.toFront();
    }

//    All users clicked
    @FXML
    void all_users_clicked(MouseEvent event) {
        hideSidePanes();
        hideOtherPanes(all_user_pn);
        all_user_pn.setVisible(true);
        usersTable.setVisible(true);

        // Fetch data from database
        ids = DbConnector.getNewsData("user_accounts", "user_id");
        f_names = DbConnector.getNewsData("user_accounts", "first_name");
        l_names = DbConnector.getNewsData("user_accounts", "last_name");
        u_names = DbConnector.getNewsData("user_accounts", "user_name");
        pwds = DbConnector.getNewsData("user_accounts", "user_password");
        emails = DbConnector.getNewsData("user_accounts", "user_email");

        // Create an ObservableList to hold User objects
        ObservableList<User> usersList = FXCollections.observableArrayList();

        // Populate the list with User objects
        for (int i = 0; i < f_names.size(); ++i) {
            usersList.add(new User(ids.get(i), f_names.get(i), l_names.get(i), emails.get(i), pwds.get(i), u_names.get(i)));
            System.out.println(ids.get(i));
        }

        // Set the data to the TableView
        usersTable.setItems(usersList);


        // Set up columns using PropertyValueFactory
        idColumn.setCellValueFactory(new PropertyValueFactory<>("id"));
        idColumn.setSortType(TableColumn.SortType.ASCENDING);
        firstNameColumn.setCellValueFactory(new PropertyValueFactory<>("first_name"));
        lastNameColumn.setCellValueFactory(new PropertyValueFactory<>("last_name"));
        emailColumn.setCellValueFactory(new PropertyValueFactory<>("email"));
        userNameColumn.setCellValueFactory(new PropertyValueFactory<>("userName"));
        passwordColumn.setCellValueFactory(new PropertyValueFactory<>("password"));
    }

    @FXML
    public void initialize() {

    }

    //Creates a method to hide all the other panes
    public void hideOtherPanes(Pane showPane){
        Platform.runLater(() -> {
            for (Pane pane : panes){
                if (pane == showPane){
                    continue;
                }
                else {
                    pane.setVisible(false);
                }
            }
        });
    }

    //Updating the suer details
    @FXML
    void update_user_details_action(ActionEvent event) {
        if (!new_Fname.getText().isEmpty()){
            DbConnector.UpdateSingleValue("user_accounts","first_name","user_email",new_Fname.getText(),updt_email.getText());
        }
        if (!new_pwd.getText().isEmpty()){
            DbConnector.UpdateSingleValue("user_accounts","user_password","user_email",new_pwd.getText(),updt_email.getText());
        }
        if (!new_Lname.getText().isEmpty()){
            DbConnector.UpdateSingleValue("user_accounts","last_name","user_email",new_Lname.getText(),updt_email.getText());
        }
        if (!new_Email.getText().isEmpty()){
            DbConnector.UpdateSingleValue("user_accounts","user_email","user_email",new_Email.getText(),updt_email.getText());
        }
        if(!new_Uname.getText().isEmpty()){
            DbConnector.UpdateSingleValue("user_accounts","user_name","user_email",new_Uname.getText(),updt_email.getText());
        }
        Alert.giveConfirmAlert("Update done","You have successfully updated the details");
    }


    //Searching the user to remove
    @FXML
    void rem_user_search(MouseEvent event) {
        String input_mail= rem_user_email.getText();
        if (DbConnector.userEmailValidityCheck(input_mail)){
            rem_user_pane.setVisible(true);
            new FadeIn(rem_user_pane).play();
            view_rem_fname.setText(DbConnector.getUserData("first_name","user_accounts","user_email",input_mail));
            view_rem_id.setText(DbConnector.getUserData("user_id","user_accounts","user_email",input_mail));
            view_rem_lname.setText(DbConnector.getUserData("last_name","user_accounts","user_email",input_mail));
            view_rem_mail.setText(input_mail);
            view_rem_pwd.setText(DbConnector.getUserData("user_password","user_accounts","user_email",input_mail));
            view_rem_uname.setText(DbConnector.getUserData("user_name","user_accounts","user_email",input_mail));
        }
        else {
            Alert.giveWarningAlert("Incorrect mail","Please enter a correct email");
        }
    }

    //Searching a user to update
    @FXML
    void srch_user(MouseEvent event) {
        String input_mail = updt_email.getText();
        if (DbConnector.userEmailValidityCheck(input_mail)){
            user_updt_pn.setVisible(true);
            new FadeIn(user_updt_pn).play();
            view_fname.setText(DbConnector.getUserData("first_name","user_accounts","user_email",input_mail));
            view_Lname.setText(DbConnector.getUserData("last_name","user_accounts","user_email",input_mail));
            view_mail.setText(input_mail);
            view_pwd.setText(DbConnector.getUserData("user_password","user_accounts","user_email",input_mail));
            view_Uname.setText(DbConnector.getUserData("user_name","user_accounts","user_email",input_mail));
            view_uid.setText(DbConnector.getUserData("user_id","user_accounts","user_email",input_mail));

        }
        else {
            Alert.giveWarningAlert("Incorrect mail","Please enter a correct email");
        }
    }

    //Hide the all other side panes
    public void hideSidePanes(){
        rem_user_pane.setVisible(false);
        user_updt_pn.setVisible(false);
        updt_news_mini_pane.setVisible(false);
        del_news_mini_pane.setVisible(false);
        usersTable.setVisible(false);

    }

    //delete user button
    @FXML
    void delete_user(MouseEvent event) {
        DbConnector.deleteRaw("user_accounts","user_email",rem_user_email.getText());
        Alert.giveConfirmAlert("Successfully deleted","Ypu have successfully removed a user from the system");
        rem_user_email.clear();
        view_rem_uname.setText("");
        view_rem_pwd.setText("");
        view_rem_mail.setText("");
        view_rem_fname.setText("");
        view_rem_id.setText("");
        view_rem_lname.setText("");
        rem_user_pane.setVisible(false);
    }

    //generate a category
    @FXML
    void generate_cat(ActionEvent event) {
        List<String> contents = new ArrayList<>();
        List<String> categories = new ArrayList<>();

        contents = DbConnector.getNewsData("news","content");
        categories = DbConnector.getNewsData("news","category");


        // Step 2: Create Instances for Weka
        FastVector attributes = new FastVector();
        attributes.addElement(new Attribute("content", (FastVector) null)); // Text attribute
        FastVector classValues = new FastVector();
        for (String category : categories) {
            if (!classValues.contains(category)) {
                classValues.addElement(category);
            }
        }
        attributes.addElement(new Attribute("category", classValues)); // Rename "class" to "category"


        Instances dataset = new Instances("NewsDataset", attributes, contents.size());
        dataset.setClassIndex(1); // Class attribute index
        for (int i = 0; i < contents.size(); i++) {
            Instance instance = new DenseInstance(2);
            instance.setValue((Attribute) attributes.elementAt(0), contents.get(i));
            instance.setValue((Attribute) attributes.elementAt(1), categories.get(i));
            dataset.add(instance);
        }

        // Step 3: Preprocess text with StringToWordVector
        StringToWordVector filter = new StringToWordVector();
        try {
            filter.setInputFormat(dataset);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
        Instances filteredData = null;
        try {
            filteredData = Filter.useFilter(dataset, filter);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }

        // Step 4: Train a Classifier
        Classifier classifier = new NaiveBayes();
        try {
            classifier.buildClassifier(filteredData);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }

        // Step 5: Predict new articles
        String newArticle = new__content.getText();
        Instance newInstance = new DenseInstance(2);
        newInstance.setDataset(dataset);
        newInstance.setValue((Attribute) attributes.elementAt(0), newArticle);
        Instances testSet = new Instances(dataset, 0);
        testSet.add(newInstance);

        Instances filteredTestSet = null;
        try {
            filteredTestSet = Filter.useFilter(testSet, filter);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
        double predictedIndex = 0;
        try {
            predictedIndex = classifier.classifyInstance(filteredTestSet.instance(0));
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
        String predictedCategory = dataset.classAttribute().value((int) predictedIndex);

        System.out.println("Predicted Category: " + predictedCategory);
        pred_cat.setText(predictedCategory);
    }

    //Adding news automatically by using API
    @FXML
    void add_aut_news(MouseEvent event) {
        if (new_api_url.getText().isEmpty()||new_category.getText().isEmpty()){

        }else {
            Api_connection.connectApi(new_api_url.getText(),new_category.getText());
            Alert.giveConfirmAlert("Successfully added","New Articles has been successfully added");
        }
    }

    @FXML
    void add_man_news(MouseEvent event) {
        if (new__content.getText().isEmpty()||new_title.getText().isEmpty()||new_author.getText().isEmpty()||new_image.getText().isEmpty()||pred_cat.getText().isEmpty()){

        }else {
            DbConnector.addNews(new_title.getText(),new_author.getText(),new__content.getText(),new_image.getText(),pred_cat.getText());
            new_title.clear();
            new_author.clear();
            new__content.clear();
            new_image.clear();
            pred_cat.setText("");
            Alert.giveConfirmAlert("Successfully added","You have successfully added the news");
        }
    }

    //Upload new Image into the system
    @FXML
    void upld_image(ActionEvent event) throws IOException {
        if (updtd_URL.getText().isEmpty()){
            
        }
        else {
            URL image = new URL(updtd_URL.getText());
            Image pic = new Image(image.openStream());
            view_image.setImage(pic);
            correct_image = updtd_URL.getText();
            isImageUpdated = true;

        }
    }

    //generate new category according to new content
    @FXML
    void gen_new_cat(ActionEvent event) {
        if (!updtd_cntn.getText().isEmpty()) {
            isNewCatGenerated = true;
            List<String> contents = new ArrayList<>();
            List<String> categories = new ArrayList<>();

            contents = DbConnector.getNewsData("news", "content");
            categories = DbConnector.getNewsData("news", "category");


            // Step 2: Create Instances for Weka
            FastVector attributes = new FastVector();
            attributes.addElement(new Attribute("content", (FastVector) null)); // Text attribute
            FastVector classValues = new FastVector();
            for (String category : categories) {
                if (!classValues.contains(category)) {
                    classValues.addElement(category);
                }
            }
            attributes.addElement(new Attribute("category", classValues)); // Rename "class" to "category"


            Instances dataset = new Instances("NewsDataset", attributes, contents.size());
            dataset.setClassIndex(1); // Class attribute index
            for (int i = 0; i < contents.size(); i++) {
                Instance instance = new DenseInstance(2);
                instance.setValue((Attribute) attributes.elementAt(0), contents.get(i));
                instance.setValue((Attribute) attributes.elementAt(1), categories.get(i));
                dataset.add(instance);
            }

            // Step 3: Preprocess text with StringToWordVector
            StringToWordVector filter = new StringToWordVector();
            try {
                filter.setInputFormat(dataset);
            } catch (Exception e) {
                throw new RuntimeException(e);
            }
            Instances filteredData = null;
            try {
                filteredData = Filter.useFilter(dataset, filter);
            } catch (Exception e) {
                throw new RuntimeException(e);
            }

            // Step 4: Train a Classifier
            Classifier classifier = new NaiveBayes();
            try {
                classifier.buildClassifier(filteredData);
            } catch (Exception e) {
                throw new RuntimeException(e);
            }

            // Step 5: Predict new articles
            String newArticle = updtd_cntn.getText();
            Instance newInstance = new DenseInstance(2);
            newInstance.setDataset(dataset);
            newInstance.setValue((Attribute) attributes.elementAt(0), newArticle);
            Instances testSet = new Instances(dataset, 0);
            testSet.add(newInstance);

            Instances filteredTestSet = null;
            try {
                filteredTestSet = Filter.useFilter(testSet, filter);
            } catch (Exception e) {
                throw new RuntimeException(e);
            }
            double predictedIndex = 0;
            try {
                predictedIndex = classifier.classifyInstance(filteredTestSet.instance(0));
            } catch (Exception e) {
                throw new RuntimeException(e);
            }
            String predictedCategory = dataset.classAttribute().value((int) predictedIndex);

            System.out.println("Predicted Category: " + predictedCategory);
            view_cat.setText(predictedCategory);
        }
        else {
            Alert.giveWarningAlert("Content is empty","You cant predict a category using Empty content");
        }
    }

    //Update news button
    @FXML
    void updt_news(MouseEvent event) {
            if (!updtd_cntn.getText().isEmpty()){
                if (isNewCatGenerated){
                    DbConnector.UpdateSingleValue("news","content","id",updtd_cntn.getText(),news_id.getText());
                }
            }
            if (!updtd_author.getText().isEmpty()){
                DbConnector.UpdateSingleValue("news","author","id",updtd_author.getText(),news_id.getText());
            }
            if (!updtd_title.getText().isEmpty()){
                DbConnector.UpdateSingleValue("news","title","id",updtd_title.getText(),news_id.getText());
            }
            if (!updtd_URL.getText().isEmpty()){
                if (isImageUpdated){
                    DbConnector.UpdateSingleValue("news","image","id",updtd_URL.getText(),news_id.getText());
                }
            }
            if (isNewCatGenerated){
                DbConnector.UpdateSingleValue("news","category","id",view_cat.getText(),news_id.getText());
            }
    }

    //Search the news to update
    @FXML
    void srch_updt_news(MouseEvent event) {
        if (news_id.getText().isEmpty()){
            Alert.giveWarningAlert("Empty ID","User Id field is empty");
        }
        else {
            if (DbConnector.newsIdValidityCheck(Integer.parseInt(news_id.getText()))) {
                updt_news_mini_pane.setVisible(true);
                new FadeIn(updt_news_mini_pane).play();
                view_content.setText(DbConnector.getUserData("content", "news", "id", news_id.getText()));
                view_title.setText(DbConnector.getUserData("title", "news", "id", news_id.getText()));
                view_author.setText(DbConnector.getUserData("author", "news", "id", news_id.getText()));
                view_cat.setText(DbConnector.getUserData("category", "news", "id", news_id.getText()));
                Image image = new Image(DbConnector.getUserData("image", "news", "id", news_id.getText()));
                Platform.runLater(() -> view_image.setImage(image));
            }else{
                Alert.giveWarningAlert("Incorrect ID","News ID is incorrect please enter a valid ID");
            }
        }
    }

    @FXML
    void srch_del_news(MouseEvent event) {
        if (!del_news_id.getText().isBlank()){
            if (DbConnector.newsIdValidityCheck(Integer.parseInt(del_news_id.getText()))){
                del_news_mini_pane.setVisible(true);
                new FadeIn(del_news_mini_pane).play();
                view_del_news_con.setText(DbConnector.getUserData("content", "news", "id",del_news_id.getText()));
                view_del_news_auth.setText(DbConnector.getUserData("author", "news", "id", del_news_id.getText()));
                view_del_news_cat.setText(DbConnector.getUserData("category", "news", "id", del_news_id.getText()));
                view_del_news_title.setText(DbConnector.getUserData("title", "news", "id", del_news_id.getText()));
                Image image = new Image(DbConnector.getUserData("image", "news", "id",del_news_id.getText()));
                Platform.runLater(() -> del_view_img.setImage(image));

            }
            else{
                Alert.giveWarningAlert("Unavailable ID","News ID is Unavailable");
            }
        }else{
            Alert.giveWarningAlert("Blank data","Please enter a news Id to search");
        }
    }

    @FXML
    void del_news_butn(MouseEvent event) {
        DbConnector.deleteRaw("news","id",del_news_id.getText());
        view_del_news_title.setText("");
        view_del_news_cat.setText("");
        view_del_news_auth.setText("");
        view_del_news_con.setText("");
        del_news_mini_pane.setVisible(false);
    }

    @FXML
    void close_wndw(MouseEvent event) {
        Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
        stage.close();
        System.out.println("close");

    }



}



