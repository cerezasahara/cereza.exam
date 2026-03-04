package model;

public class Customer {
    private int id;
    private String username;
    private String password;

    // Constructor with id
    public Customer(int id, String username, String password) {
        this.id = id;
        this.username = username;
        this.password = password;
    }

    // Constructor without id (for registration)
    public Customer(String username, String password) {
        this.username = username;
        this.password = password;
    }

    // Getters
    public int getId() {
        return this.id;
    }

    public String getUsername() {
        return this.username;
    }

    // Verify password
    public boolean verifyPassword(String password) {
        return this.password.equals(password);
    }

    // Set password (for change password)
    public void setPassword(String password) {
        this.password = password;
    }
}
