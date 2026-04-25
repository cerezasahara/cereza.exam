package controller;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.Scanner;

import model.Product;
import model.MachineMoney;
import view.UI;
import utils.DBConnection;

public class VendingController {

    static Scanner scan = new Scanner(System.in);

   
    public void viewProducts() throws SQLException {
       Product.getAllProducts();
  
    }

    
    public void buyProduct() throws SQLException {
        viewProducts(); // Show product list first

        System.out.println("\nEnter Product ID to buy: ");
        int productId = UI.getIntInput(scan);

        System.out.println("Enter quantity: ");
        int quantity = UI.getIntInput(scan);

        System.out.println("Enter your money: ₱");
        double money = UI.getDoubleInput(scan);

        try (Connection conn = DBConnection.getConnection()) {

            // Check product info
            String sql = "SELECT * FROM products WHERE product_id = ?";
            PreparedStatement pst = conn.prepareStatement(sql);
            pst.setInt(1, productId);
            ResultSet rs = pst.executeQuery();

            if (rs.next()) {
                String name = rs.getString("name");
                double price = rs.getDouble("price");
                int stock = rs.getInt("stock");

                if (stock < quantity) {
                    System.out.println("Insufficient stock! Available: " + stock);
                    return;
                }

                double totalCost = price * quantity;

                if (money < totalCost) {
                    System.out.println("Not enough money! Total cost: ₱" + totalCost);
                    return;
                }

                double change = money - totalCost;

                // Update product stock
                String updateStock = "UPDATE products SET stock = stock - ? WHERE product_id = ?";
                PreparedStatement pst2 = conn.prepareStatement(updateStock);
                pst2.setInt(1, quantity);
                pst2.setInt(2, productId);
                pst2.executeUpdate();

                // Add money to machine
                MachineMoney.addMoney(totalCost);

                System.out.println("\nYou bought " + quantity + " x " + name + " for ₱" + totalCost);
                System.out.println("Your change: ₱" + change);

            } else {
                System.out.println("Product ID not found.");
            }

        } catch (SQLException e) {
            System.out.println("Error buying product: " + e.getMessage());
        }
    }
}
