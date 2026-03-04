package controller;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Scanner;
import model.Admin;
import model.Product;
import model.MachineMoney;
import utils.DBConnection;
import view.UI;

public class AdminController {

    static Scanner scan = new Scanner(System.in);

  
    public void addProduct() throws SQLException {
        System.out.println("Enter product name: ");
        String name = UI.getStringInput(scan);

        System.out.println("Enter product price: ₱");
        double price = UI.getDoubleInput(scan);

        System.out.println("Enter stock quantity: ");
        int stock = UI.getIntInput(scan);

        Product.addProduct(name, price, stock);
        System.out.println("Product added successfully!");
    }

   
    public void updateProduct() throws SQLException {
        
        System.out.print("Enter Product ID to update: ");
        int id = UI.getIntInput(scan);
        System.out.println("Enter new product name: ");
        String name = UI.getStringInput(scan);
        System.out.println("Enter new price: ₱");
        double price = UI.getDoubleInput(scan);
        System.out.println("Enter new stock quantity: ");
        int stock = UI.getIntInput(scan);
        Product.updateProduct(id, name, price, stock);
        System.out.println("Product updated successfully!");
    }

    
    public void deleteProduct() throws SQLException {
                System.out.println("Enter Product ID to delete: ");
        int id = UI.getIntInput(scan);
        Product.deleteProduct(id);
        System.out.println("Product deleted successfully!");
    }

    
    public void restockProduct() throws SQLException {
                System.out.println("Enter Product ID to restock: ");
        int id = UI.getIntInput(scan);
        System.out.println("Enter quantity to add: ");
        int quantity = UI.getIntInput(scan);
        Product.restockProduct(id, quantity);
        System.out.println("Product restocked successfully!");
    }

    
    public void collectMoney() {
        double total = MachineMoney.collectMoney();
        System.out.println("You collected ₱" + total + " from the vending machine.");
    }

   public boolean login(String username, String password) {
        if (password.isBlank() || username.isBlank()) {
            System.out.println("\nUsername or Password is blank!");
            return false;
        }

        String sql = """
                     SELECT * FROM admins WHERE username = ? AND password = ?
                     """;

        try (Connection con = DBConnection.getConnection(); PreparedStatement ps = con.prepareStatement(sql)) {
            ps.setString(1, username);
            ps.setString(2, password);

            ResultSet rs = ps.executeQuery();

            if (rs.next()) {
                System.out.println("\nLogged in as " + rs.getString("username"));
                return true;
                        
            }

            System.out.println("\nIncorrect Username or Password.");
            return false;

        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }
}
