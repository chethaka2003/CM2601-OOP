package com.example.coursework.controllers;

import animatefx.animation.FadeIn;
import com.example.coursework.DbConnector;
import javafx.application.Platform;
import javafx.event.ActionEvent;
import javafx.event.Event;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.Pane;
import weka.classifiers.Classifier;
import weka.classifiers.bayes.NaiveBayes;
import weka.core.*;
import weka.filters.Filter;
import weka.filters.unsupervised.attribute.StringToWordVector;

import java.net.URL;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import java.util.ResourceBundle;

public class AdministraorViewController implements Initializable {

    List<Pane> panes = new ArrayList<>();

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


    @Override
    public void initialize(URL location, ResourceBundle resources){
        panes.add(add_news_pn);
        panes.add(del_nws_pn);
        panes.add(del_usr_pn);
        panes.add(updt_news_pn);
        panes.add(updt_usr_pn);
    }

    @FXML
    void add_news_clicked(MouseEvent event) {
        hideOtherPanes(add_news_pn);
        add_news_pn.setVisible(true);
        new FadeIn(add_news_pn).play();
        add_news_pn.toFront();
    }

    @FXML
    void del_news_clked(MouseEvent event) {
        hideOtherPanes(del_nws_pn);
        del_nws_pn.setVisible(true);
        new FadeIn(del_nws_pn).play();
        del_nws_pn.toFront();
    }

    @FXML
    void dshbrd_clked(MouseEvent event) {
        hideOtherPanes(add_news_pn);
        add_news_pn.setVisible(true);
        new FadeIn(add_news_pn).play();
        add_news_pn.toFront();
    }

    @FXML
    void hlp_spt_clked(MouseEvent event) {

    }

    @FXML
    void rem_user_clicked(MouseEvent event) {
        hideOtherPanes(del_usr_pn);
        del_usr_pn.setVisible(true);
        new FadeIn(del_usr_pn).play();
        del_usr_pn.toFront();
    }

    @FXML
    void up_news_clked(MouseEvent event) {
        hideOtherPanes(updt_news_pn);
        updt_news_pn.setVisible(true);
        new FadeIn(updt_news_pn).play();
        updt_news_pn.toFront();
    }

    @FXML
    void update_user(MouseEvent event) {
        hideOtherPanes(updt_usr_pn);
        updt_usr_pn.setVisible(true);
        new FadeIn(updt_usr_pn).play();
        updt_usr_pn.toFront();
    }

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


    }


    //    public static void main(String[] args) throws Exception {
//        // Step 1: Load data from MySQL
//        List<String> contents = new ArrayList<>();
//        List<String> categories = new ArrayList<>();
//
//        contents = DbConnector.getNewsData("news","content");
//        categories = DbConnector.getNewsData("news","category");
//
//
//        // Step 2: Create Instances for Weka
//        FastVector attributes = new FastVector();
//        attributes.addElement(new Attribute("content", (FastVector) null)); // Text attribute
//        FastVector classValues = new FastVector();
//        for (String category : categories) {
//            if (!classValues.contains(category)) {
//                classValues.addElement(category);
//            }
//        }
//        attributes.addElement(new Attribute("category", classValues)); // Rename "class" to "category"
//
//
//        Instances dataset = new Instances("NewsDataset", attributes, contents.size());
//        dataset.setClassIndex(1); // Class attribute index
//        for (int i = 0; i < contents.size(); i++) {
//            Instance instance = new DenseInstance(2);
//            instance.setValue((Attribute) attributes.elementAt(0), contents.get(i));
//            instance.setValue((Attribute) attributes.elementAt(1), categories.get(i));
//            dataset.add(instance);
//        }
//
//        // Step 3: Preprocess text with StringToWordVector
//        StringToWordVector filter = new StringToWordVector();
//        filter.setInputFormat(dataset);
//        Instances filteredData = Filter.useFilter(dataset, filter);
//
//        // Step 4: Train a Classifier
//        Classifier classifier = new NaiveBayes();
//        classifier.buildClassifier(filteredData);
//
//        // Step 5: Predict new articles
//        String newArticle = "Srilanka won the match by two wickets";
//        Instance newInstance = new DenseInstance(2);
//        newInstance.setDataset(dataset);
//        newInstance.setValue((Attribute) attributes.elementAt(0), newArticle);
//        Instances testSet = new Instances(dataset, 0);
//        testSet.add(newInstance);
//
//        Instances filteredTestSet = Filter.useFilter(testSet, filter);
//        double predictedIndex = classifier.classifyInstance(filteredTestSet.instance(0));
//        String predictedCategory = dataset.classAttribute().value((int) predictedIndex);
//
//        System.out.println("Predicted Category: " + predictedCategory);
//        System.out.println(5/2);
//
//
//    }

