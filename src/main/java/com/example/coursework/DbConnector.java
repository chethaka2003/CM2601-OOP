package com.example.coursework;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class DbConnector {

    private static final String URL = "jdbc:mysql://localhost:3306/news_asia";
    private static final String USERNAME = "root";
    private static final String PASSWORD = "";

    private static Connection connection;

    // Method to connect to the database
    public static void connectToDb() {

            try {
                Class.forName("com.mysql.cj.jdbc.Driver");
                // Connect with only URL and username since there's no password
                connection = DriverManager.getConnection(URL, USERNAME, "");
                System.out.println("Connected to the database.");
            } catch (ClassNotFoundException e) {
                System.out.println("JDBC Driver not found.");
                e.printStackTrace();
            } catch (SQLException e) {
                System.out.println("Error: Unable to connect to the database.");
                e.printStackTrace();
            }


    }

    // Method to add details to the database using PreparedStatement
    public static void addDetails(String firstName, String lastName, String userName, String pwd, String email) {
        String sql = "INSERT INTO user_accounts (first_name, last_name, user_name, user_password, user_email) VALUES (?, ?, ?, ?, ?)";

        connectToDb(); // Ensure connection is established

        try (PreparedStatement preparedStatement = connection.prepareStatement(sql)) {
            preparedStatement.setString(1, firstName);
            preparedStatement.setString(2, lastName);
            preparedStatement.setString(3, userName);
            preparedStatement.setString(4, pwd);
            preparedStatement.setString(5, email);

            int rowsInserted = preparedStatement.executeUpdate();
            if (rowsInserted > 0) {
                System.out.println("A new user was inserted successfully!");
            }
        } catch (SQLException e) {
            System.out.println("Error while inserting user details.");
            e.printStackTrace();
        }
    }

    //Checking the availability of username
    public static boolean userNameValidityCheck(String userName) {
        String sql = "SELECT user_name FROM user_accounts WHERE user_name = ?";
        boolean isAvailable = false;

        //Connect to the database
        connectToDb();

        try (PreparedStatement preparedStatement = connection.prepareStatement(sql)){
            preparedStatement.setString(1, userName);
            ResultSet resultSet = preparedStatement.executeQuery();

            isAvailable = resultSet.next();
        }
        catch (SQLException e)
        {
            System.out.println("Error while checking user_name availability.");
            e.printStackTrace();
        }

        return isAvailable;


    }

    //Checking the availability of email
    public static boolean userEmailValidityCheck(String email) {
        String sql = "SELECT user_name FROM user_accounts WHERE user_email = ?";
        boolean isAvailable = false;

        //Connect to the database
        connectToDb();

        try (PreparedStatement preparedStatement = connection.prepareStatement(sql)){
            preparedStatement.setString(1, email);
            ResultSet resultSet = preparedStatement.executeQuery();

            isAvailable = resultSet.next();
        }
        catch (SQLException e)
        {
            System.out.println("Error while checking user_name availability.");
            e.printStackTrace();
        }

        return isAvailable;


    }

    // Checking the validity of password and username
    public static boolean loginPwdValidityCheck(String password, String name) {
        String sql = "SELECT user_name FROM user_accounts WHERE user_password = ?";
        boolean isAvailable = false;

        // Connect to the database (ensure this initializes 'connection')
        connectToDb();

        try (PreparedStatement preparedStatement = connection.prepareStatement(sql)) {
            preparedStatement.setString(1, password); // Set the password parameter
            ResultSet resultSet = preparedStatement.executeQuery();

            // Check if the resultSet has any records and move to the first row
            if (resultSet.next()) {
                String realUserName = resultSet.getString("user_name"); // Get the username
                if (realUserName.equals(name)) { // Compare the username correctly
                    isAvailable = true; // User is valid
                }
            }

        } catch (SQLException e) {
            System.out.println("Error while checking user credentials: " + e.getMessage());
            e.printStackTrace();
        }

        return isAvailable; // Return whether the user is valid or not
    }





    public static void addNews(String title, String author, String content, String image,String category) {
        String sql = "INSERT INTO news (title, author, content, image, category) VALUES (?, ?, ?, ?, ?)";

        connectToDb();

        try (PreparedStatement preparedStatement = connection.prepareStatement(sql)){
            preparedStatement.setString(1, title);
            preparedStatement.setString(2, author);
            preparedStatement.setString(3, content);
            preparedStatement.setString(4, image);
            preparedStatement.setString(5, category);

            preparedStatement.executeUpdate();

            connection.close();

        }catch (SQLException e){
            System.out.println("Error while adding new details.");
        }


    }

    //Delete all the data in table
    public static void deleteAllNewsData() {
        String sql = "DELETE FROM news ";

        connectToDb();

        try {
            Statement statement = connection.createStatement();

            statement.executeUpdate(sql);

            connection.close();


        } catch (SQLException e) {
            System.out.println("There was a problem deleting the data.");;
        }
    }

    //Getting the data in  a column
    public static List<String> getNewsData(String tableName, String columnName) {
        List<String> data = new ArrayList<>();

        connectToDb();

        String sql = "SELECT " + columnName + " FROM " + tableName;

        try (Statement statement = connection.createStatement();
             ResultSet resultSet = statement.executeQuery(sql)) {

            while (resultSet.next()) {
                data.add(resultSet.getString(columnName));
            }
        } catch (SQLException e) {
            System.err.println("Error executing query: " + e.getMessage());
            e.printStackTrace();
        }

        return data;
    }


    // To get the user ID by using userName
    public static String getUserId(String name) {
        String sql = "SELECT user_id FROM user_accounts WHERE user_name = ?";

        // Connect to the database (ensure this initializes 'connection')
        connectToDb();

        try (PreparedStatement preparedStatement = connection.prepareStatement(sql)) {
            preparedStatement.setString(1, name); // Set the password parameter
            ResultSet resultSet = preparedStatement.executeQuery();

            // Check if the resultSet has any records and move to the first row
            if (resultSet.next()) {
                return resultSet.getString("user_id");
            }

        } catch (SQLException e) {
            System.out.println("Error while checking user credentials: " + e.getMessage());
            e.printStackTrace();
        }
        System.out.println("no");
        return "no" ;
    }


}
