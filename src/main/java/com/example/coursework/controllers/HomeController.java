package com.example.coursework.controllers;

import com.example.coursework.DbConnector;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.control.Label;
import javafx.scene.control.ScrollPane;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.Pane;
import javafx.scene.text.Text;
import javafx.application.Platform;
import javafx.stage.Stage;

import java.net.URL;
import java.util.*;
import java.util.stream.Collectors;

public class HomeController implements Initializable {

    //String variable to hold user favourable category
    String mostRepeated;

    private int layoutY = 23; // Starting Y position for the first article
    private final int paneHeight = 380; // Height of each article Pane
    private final int spacing = 10; // Space between articles

    // Variables to hold news data
    private List<String> titles = new ArrayList<>();
    private List<String> content = new ArrayList<>();
    private List<String> images = new ArrayList<>();
    private List<String> like_count = new ArrayList<>();
    private List<String> ids = new ArrayList<>();


    private List<Integer> likedNews;

    private HashMap<String,Boolean> liked_status = new HashMap<>();


    @FXML
    private ScrollPane newsHome;

    @FXML
    private Pane nholderBg;

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        // Start a background thread to load the data
        new Thread(() -> {
            // Fetch data in the background
            titles = DbConnector.getNewsData("news", "title");
            content = DbConnector.getNewsData("news", "content");
            images = DbConnector.getNewsData("news", "image");
            like_count = DbConnector.getNewsData("news", "likes");
            ids = DbConnector.getNewsData("news", "id");


            // Update UI with the loaded data (this needs to be done on the UI thread)
            Platform.runLater(this::loadArticles);
        }).start();

        userPref(4);
    }



    // Method to load articles into the UI
    private void loadArticles() {
        int newsCount = Math.min(titles.size(), Math.min(content.size(), images.size()));

        for (int i = 0; i < newsCount; i++) {
            Pane pane = createArticlePane(i);

            // Add the article to the main Pane
            nholderBg.getChildren().add(pane);

            // Update layoutY for the next article
            layoutY += paneHeight + spacing;
        }


        // Dynamically adjust the height of nholderBg to fit all children
        nholderBg.setPrefHeight(layoutY);
    }

    // Method to create a single article pane
    private void loadImageThread(String imagePath, ImageView imageView) {
        // Placeholder image while the actual image is loading
        Image placeholder = new Image(getClass().getResource("/com/example/coursework/images/loading.png").toExternalForm());
        imageView.setImage(placeholder);

        // Load image in a background thread
        new Thread(() -> {
            try {
                Image image = new Image(imagePath, 320, 180, true, true); // Load image with specified size
                Platform.runLater(() -> imageView.setImage(image)); // Update the ImageView on the UI thread
            } catch (Exception e) {
                System.out.println("Cant load the image");
            }
        }).start();
    }

    private Pane createArticlePane(int index) {
        Pane pane = new Pane();
        pane.setId("article");
        pane.setPrefHeight(280);
        pane.setPrefWidth(840);
        pane.setLayoutX(28);
        pane.setLayoutY(layoutY);

        // Setting images
        String imagePath = images.get(index);
        ImageView imageView = new ImageView();
        imageView.setFitWidth(320);
        imageView.setFitHeight(180);
        imageView.setLayoutX(21);
        imageView.setLayoutY(80);
        imageView.setId("image-view");
        if (imagePath != null && !imagePath.isEmpty()) {
            loadImageThread(imagePath, imageView);
        }
        pane.getChildren().add(imageView);

        // Add title, content, and other UI components as before
        Text titleText = new Text(titles.get(index));
        titleText.setId("title");
        titleText.setWrappingWidth(791.13671875);
        titleText.setLayoutX(14);
        titleText.setLayoutY(30);
        pane.getChildren().add(titleText);

        Text contentText = new Text(content.get(index));
        contentText.setId("content");
        contentText.setWrappingWidth(469.13671875);
        contentText.setLayoutX(361);
        contentText.setLayoutY(83);
        pane.getChildren().add(contentText);

        Text likeCount = new Text(like_count.get(index));
        likeCount.setId("content");
        likeCount.setLayoutX(759);
        likeCount.setLayoutY(255);
        likeCount.setWrappingWidth(43);
        pane.getChildren().add(likeCount);

        Label id = new Label(ids.get(index));
        id.setVisible(false);
        pane.getChildren().add(id);

        liked_status.put(ids.get(index), false);

        ImageView heart = new ImageView(getClass().getResource("/com/example/coursework/images/heart.png").toExternalForm());
        heart.setId("heart");
        heart.setFitHeight(32);
        heart.setFitWidth(32);
        heart.setLayoutX(789);
        heart.setLayoutY(235);
        heart.setOnMouseClicked(event -> {
            if (!liked_status.get(id.getText())) {
                int newLikeCount = Integer.parseInt(likeCount.getText()) + 1;
                likeCount.setText(String.valueOf(newLikeCount));
                DbConnector.UpdateSingleValue("news", "likes", "id", String.valueOf(newLikeCount), id.getText());
                liked_status.put(id.getText(), true);
                DbConnector.AddingUserLikes(Integer.parseInt(LoginController.getLoggedUserId()),Integer.parseInt(id.getText()));
            }
        });
        pane.getChildren().add(heart);

        return pane;
    }

    public String userPref(int userId) {
        // list to store categories
        List<String> contents = new ArrayList<>();
        likedNews = DbConnector.getUserLikedNews("news_id", "news_user_like", "user_id", userId);

        for (int id : likedNews) {
            contents.add(DbConnector.getNewsCat("category", "news", "id", id));
        }

        // Count occurrences of each category
        Map<String, Long> frequencyMap = contents.stream()
                .collect(Collectors.groupingBy(s -> s, Collectors.counting()));

        // Find the maximum frequency
        long maxCount = frequencyMap.values().stream().max(Long::compare).orElse(0L);

        // Find the first category with the maximum frequency
        String mostRepeated = frequencyMap.entrySet().stream()
                .filter(entry -> entry.getValue() == maxCount)
                .map(Map.Entry::getKey)
                .findFirst() // Get the first one
                .orElse(null); // Handle the case where no categories are found

        System.out.println("Most repeated category: " + mostRepeated);
        return mostRepeated;
    }





    @FXML
    void close(MouseEvent event) {
        Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
        stage.close();
        System.out.println("close");
    }

}