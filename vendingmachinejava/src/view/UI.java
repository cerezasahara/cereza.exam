package view;

import java.sql.ResultSet;

public class UI {

    // =========================
    // MAIN MENU (Customer)
    // =========================
    public static void showMainMenu() {
        System.out.println("""
               
               \n==== WELCOME TO VENDING MACHINE ====
               [1] View Products
               [2] Buy Product
               [3] Admin Login
               [4] Exit
               """);
        System.out.println("Choose: ");
    }

    // =========================
    // ADMIN MENU
    // =========================
    public static void showAdminMenu() {
        System.out.println("""
               
               \n==== ADMIN PANEL ====
               [1] Add Product
               [2] View Products
               [3] Update Product
               [4] Delete Product
               [5] Restock Product
               [6] Collect Money
               [7] Logout
               """);
        System.out.println("Choose: ");
    }

    // =========================
    // DISPLAY PRODUCTS
    // =========================
    public static void displayProducts(ResultSet rs) {
        try {
            System.out.println("\n--- PRODUCT LIST ---");

            while (rs.next()) {
                System.out.println(
                        rs.getInt("product_id") + " | " +
                        rs.getString("name") + " | ₱" +
                        rs.getDouble("price") + " | Stock: " +
                        rs.getInt("stock")
                );
            }

        } catch (Exception e) {
            System.out.println("Error displaying products: " + e.getMessage());
        }
    }

    public static int getIntInput(java.util.Scanner scan) {
        return scan.nextInt();
    }

    public static double getDoubleInput(java.util.Scanner scan) {
        return scan.nextDouble();
    }

    public static String getStringInput(java.util.Scanner scan) {
        scan.nextLine(); // consume leftover newline
        return scan.nextLine();
    }
}
