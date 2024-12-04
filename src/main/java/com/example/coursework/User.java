package com.example.coursework;

public class User {
    private String id;
    private String first_name;
    private String last_name;
    private String email;
    private String password;
    private String user_name;

    public User(String id, String first_name, String last_name, String email, String password, String user_name) {
        this.id = id;
        this.first_name = first_name;
        this.last_name = last_name;
        this.email = email;
        this.password = password;
        this.user_name = user_name;
    }

    public String getId() {
        return id;
    }


    public String getFirst_name() {
        return first_name;
    }

    public String getLast_name() {
        return last_name;
    }

    public String getEmail() {
        return email;
    }

}
