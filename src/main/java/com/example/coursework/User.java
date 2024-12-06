package com.example.coursework;

public class User {
    private String id;
    private String first_name;
    private String last_name;
    private String email;
    private String password;
    private String userName;

    public User(String id, String first_name, String last_name, String email, String password, String userName) {
        this.id = id;
        this.first_name = first_name;
        this.last_name = last_name;
        this.email = email;
        this.password = password;
        this.userName = userName;
    }
    //Returns ID
    public String getId() {
        return id;
    }

    //Returns FirstName
    public String getFirst_name() {
        return first_name;
    }

    //Returns lastName
    public String getLast_name() {
        return last_name;
    }

    //Returns Email
    public String getEmail() {
        return email;
    }

    //Returns Password
    public String getPassword(){
        return password;
    }

    //Returns UserName
    public String getUserName() {
        return userName;
    }


}

class SysAdministrator extends User {
    private String adminRole; // Role of the administrator (e.g., SuperAdmin)

    // Constructor to initialize admin-specific fields
    public SysAdministrator(String id, String first_name, String last_name, String email, String password, String user_name, String adminRole) {
        super(id, first_name, last_name, email, password, user_name); // Calls the parent constructor
        this.adminRole = adminRole;
    }

    // Getter for adminRole
    public String getAdminRole() {
        return adminRole;
    }

    // You can add other methods that are specific to SysAdministrator here
}
