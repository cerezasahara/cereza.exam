
package model;

public class Admin {
private int id;
    private String username;
    private String password;
    
    public Admin(int id, String username, String password) {
        this.id = id;
        this.username = username;
        this.password = password;
    
    }
    
    public Admin(String username, String password) {
        this.username = username;
        this.password = password;
    }
    
    public String getUsername() {
        return this.username;
    }
    
    public boolean verifyPassword(String password) {
        return this.password.equals(password);
    }
    
    public int getId() {
        return this.id;
    }
    
  
    public void setPassword(String password) {
        this.password = password;
    }
}
