package model;

import java.sql.*;
import utils.DBConnection;
public class Product {

   
    public static void getAllProducts() {
        
        String sql = "SELECT * FROM products";
        try (Connection conn = DBConnection.getConnection(); PreparedStatement pst = conn.prepareStatement(sql);) {
            ResultSet rs = pst.executeQuery();
            while (rs.next())
            {
                String name = rs.getString("name");
                int price = rs.getInt("price");
                int stock = rs.getInt("stock");
                
                System.out.println("Name: " + name + " Price: " + price + "Stock: " + stock);
            }
        }
        
       catch (SQLException error) {
           error.printStackTrace();
       }
    }

   
    public static void addProduct(String name, double price, int stock) throws SQLException {
        try (Connection conn = DBConnection.getConnection()) {
            String sql = "INSERT INTO products(name, price, stock) VALUES (?, ?, ?)";
            PreparedStatement pst = conn.prepareStatement(sql);
            pst.setString(1, name);
            pst.setDouble(2, price);
            pst.setInt(3, stock);
            pst.executeUpdate();
        }
    }

    
    public static void updateProduct(int id, String name, double price, int stock) throws SQLException {
        try (Connection conn = DBConnection.getConnection()) {
            String sql = "UPDATE products SET name=?, price=?, stock=? WHERE product_id=?";
            PreparedStatement pst = conn.prepareStatement(sql);
            pst.setString(1, name);
            pst.setDouble(2, price);
            pst.setInt(3, stock);
            pst.setInt(4, id);
            pst.executeUpdate();
        }
    }

    
    public static void deleteProduct(int id) throws SQLException {
        try (Connection conn = DBConnection.getConnection()) {
            String sql = "DELETE FROM products WHERE product_id=?";
            PreparedStatement pst = conn.prepareStatement(sql);
            pst.setInt(1, id);
            pst.executeUpdate();
        }
    }

    // =========================
    // Restock Product
    // =========================
    public static void restockProduct(int id, int quantity) throws SQLException {
        try (Connection conn = DBConnection.getConnection ()) {
            String sql = "UPDATE products SET stock = stock + ? WHERE product_id=?";
            PreparedStatement pst = conn.prepareStatement(sql);
            pst.setInt(1, quantity);
            pst.setInt(2, id);
            pst.executeUpdate();
        }
    }
}
