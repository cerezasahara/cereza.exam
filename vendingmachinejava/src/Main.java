import controller.AdminController;
import controller.VendingController;
import java.sql.SQLException;
import java.util.Scanner;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.sql.Statement;

import utils.DBConnection;
import static utils.DBConnection.initializeDatabase;
import view.UI;

public class Main {

    static Scanner scan = new Scanner(System.in);

    static VendingController vc = new VendingController();
    static AdminController ac = new AdminController();

    public static void main(String[] args) throws SQLException {

        System.out.println("Vending Machine Initialized.");
        
        initializeDatabase();

        while (true) {

            UI.showMainMenu();

            int choice = scan.nextInt();
            scan.nextLine();

            mainMenuOption(choice);
        }
    }

    public static void mainMenuOption(int choice) throws SQLException {

        switch (choice) {

            case 1 -> {
                // View Products
                vc.viewProducts();
            }

            case 2 -> {
                // Buy Product
                vc.buyProduct();
            }

            case 3 -> {
                // Admin Login
                System.out.println("\nADMIN LOGIN");

                System.out.println("Username: ");
                String username = scan.nextLine();

                System.out.println("Password: ");
                String password = scan.nextLine();

                boolean loggedIn = ac.login(username, password);

                if (loggedIn) {
                    adminOption();
                }
            }

            case 4 -> {
                System.out.println("\nThank you for using the Vending Machine.");
                System.exit(0);
            }
        }
    }

    public static void adminOption() throws SQLException {

        boolean run = true;

        while (run) {

            UI.showAdminMenu();

            int choice = scan.nextInt();
            scan.nextLine();

            switch (choice) {

                case 1 -> ac.addProduct();

                case 2 -> vc.viewProducts();

                case 3 -> ac.updateProduct();

                case 4 -> ac.deleteProduct();

                case 5 -> ac.restockProduct();

                case 6 -> ac.collectMoney();

                case 7 -> {
                    System.out.println("\nLogging out...");
                    run = false;
                }
            }
        }
    }
}