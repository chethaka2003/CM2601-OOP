package com.example.coursework;

import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;

import java.io.IOException;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.Scanner;

public class Api_connection {

    public static URL url;

    /* Categories which are available

           1. All articles mentioning Apple from yesterday, sorted by popular publishers first
               https://newsapi.org/v2/everything?q=apple&from=2024-11-16&to=2024-11-16&sortBy=popularity&apiKey=97d01f12afa3480e876a1c397e3afa0c

           2. All articles about Tesla from the last month, sorted by recent first
               https://newsapi.org/v2/everything?q=tesla&from=2024-10-17&sortBy=publishedAt&apiKey=97d01f12afa3480e876a1c397e3afa0c
           3.

       */
    public static void connectApi(String new_url,String new_cat) {
//        String ex_url = "https://newsapi.org/v2/top-headlines?country=us&category=health&apiKey=97d01f12afa3480e876a1c397e3afa0c";


        {
            try {
                url = new URL(new_url);
                HttpURLConnection connection = (HttpURLConnection) url.openConnection();
                connection.setRequestMethod("GET");
                connection.connect();


                //Check if the connection is successful or not;
                int responseCode = connection.getResponseCode();

                if (responseCode == 200) {
                    System.out.println("Successfully connected to the API");

                    StringBuilder informationString = new StringBuilder();
                    Scanner scanner = new Scanner(url.openStream());

                    while (scanner.hasNextLine()) {
                        informationString.append(scanner.nextLine());
                    }

                    //close the scanner
                    scanner.close();

                    System.out.println(informationString);

                    //get json data
                    JSONParser parser = new JSONParser();
                    JSONObject jsonObject = (JSONObject) parser.parse(informationString.toString());


                    JSONArray results = (JSONArray) jsonObject.get("articles");

                    //Catch the title of the news
                    for (Object object : results) {
                        JSONObject item = (JSONObject) object;
                        String author = (String) item.get("author");
                        String newsContent = (String) item.get("content");
                        String image = (String) item.get("urlToImage");
                        String title = (String) item.get("title");


                        if ((title == null)) {
                            continue;
                        } else if (author == null) {
                            continue;
                        } else if (newsContent == null) {
                            continue;
                        } else if (image == null) {
                            continue;
                        } else {
                            DbConnector.addNews(title, author, newsContent, image, new_cat);

                        }
                    }


//                    System.out.println(jsonObject.get("results"));
                } else {
                    System.out.println("Failed to connect to the API");
                }


            } catch (MalformedURLException e) {
                throw new RuntimeException(e);
            } catch (IOException | ParseException e) {
                throw new RuntimeException(e);
            }
        }
    }


}
