package com.example.coursework;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.SQLException;

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

    public static void userNameValidityCheck(String userName) {
        String sql = "SELECT user_name FROM user_accounts WHERE user_name = ?";

    }
}
