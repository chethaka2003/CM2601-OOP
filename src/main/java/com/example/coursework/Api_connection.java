package com.example.coursework;

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
    public static void connectApi() {
        String ex_url = "https://newsdata.io/api/1/sources?country=lk&apikey=pub_59509efe543bc197064e7ef9040c563a6ad82";



        {
            try {
                url = new URL(ex_url);
                HttpURLConnection connection = (HttpURLConnection) url.openConnection();
                connection.setRequestMethod("GET");
                connection.connect();


                //Check if the connection is successful or not;
                int responseCode = connection.getResponseCode();

                if (responseCode == 200){
                    System.out.println("Successfully connected to the API");

                    StringBuilder informationString = new StringBuilder();
                    Scanner scanner = new Scanner(url.openStream());

                    while (scanner.hasNextLine()){
                        informationString.append(scanner.nextLine());
                    }

                    //close the scanner
                    scanner.close();

                    System.out.println(informationString);

                    //get json data
                    JSONParser parser = new JSONParser();
                    JSONObject jsonObject = (JSONObject) parser.parse(informationString.toString());

                    System.out.println(jsonObject.get("results"));
                }
                else {
                    System.out.println("Failed to connect to the API");
                }



            } catch (MalformedURLException e) {
                throw new RuntimeException(e);
            } catch (IOException | ParseException e) {
                throw new RuntimeException(e);
            }
        }
    }

    public static void main(String[] args) {
        Api_connection.connectApi();
    }
}
