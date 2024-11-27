package com.example.coursework.controllers;

import com.example.coursework.DbConnector;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.ScrollPane;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.Pane;
import javafx.scene.text.Text;

import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import java.util.ResourceBundle;

public class HomeController implements Initializable {

    private int layoutY = 23; // Starting Y position for the first article
    private final int paneHeight = 380; // Height of each article Pane
    private final int spacing = 10; // Space between articles

    // Variables to hold news data
    public List<String> titles = new ArrayList<>();
    public List<String> content = new ArrayList<>();
    public List<String> images = new ArrayList<>();
    public List<String> like_count = new ArrayList<>();

    @FXML
    private ScrollPane newsHome;

    @FXML
    private Pane nholderBg;

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        titles = DbConnector.getNewsData("news", "title");
        content = DbConnector.getNewsData("news", "content");
        images = DbConnector.getNewsData("news", "image");
        like_count = DbConnector.getNewsData("news","likes");

        int newsCount = Math.min(titles.size(), Math.min(content.size(), images.size()));

        for (int i = 0; i < 5; i++) {
            Pane pane = new Pane();
            pane.setId("article");
            pane.setPrefHeight(280);
            pane.setPrefWidth(840);
            pane.setLayoutX(28); // Fixed horizontal position
            pane.setId("article");
            pane.setLayoutY(layoutY); // Adjust vertical position dynamically

            // Setting images
            String imagePath = images.get(i);
            if (imagePath != null && !imagePath.isEmpty()) {
                Image image = new Image(imagePath);
                ImageView imageView = new ImageView(image);
                imageView.setFitWidth(320);
                imageView.setFitHeight(180);
                imageView.setLayoutX(21);
                imageView.setLayoutY(80);
                imageView.setId("image-view");
                pane.getChildren().add(imageView);
            }

            // Showing title
            Text titleText = new Text(titles.get(i));
            titleText.setId("title");
            titleText.setWrappingWidth(791.13671875);
            titleText.setLayoutX(14);
            titleText.setLayoutY(30);
            pane.getChildren().add(titleText);

            // Showing content
            String con = content.get(i);
            Text contentText = new Text(con);
            contentText.setId("content");
            contentText.setWrappingWidth(469.13671875);
            contentText.setLayoutX(361);
            contentText.setLayoutY(83);
            contentText.setId("content");
            pane.getChildren().add(contentText);

            ImageView heart = new ImageView(getClass().getResource("/com/example/coursework/images/heart.png").toExternalForm());
            heart.setId("heart");
            heart.setFitHeight(32);
            heart.setFitWidth(32);
            heart.setLayoutX(789);
            heart.setLayoutY(235);
            pane.getChildren().add(heart);

            Text likeCount = new Text(like_count.get(i));
            likeCount.setId("content");
            likeCount.setLayoutX(759);
            likeCount.setLayoutY(255);
            likeCount.setWrappingWidth(43);
            pane.getChildren().add(likeCount);

            // Add the article Pane to the holder Pane
            nholderBg.getChildren().add(pane);

            // Update layoutY for the next article
            layoutY += paneHeight + spacing;

//
        }

        // Dynamically adjust the height of nholderBg to fit all children
        nholderBg.setPrefHeight(layoutY);
    }
}
