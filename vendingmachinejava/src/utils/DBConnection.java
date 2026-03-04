package utils;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.sql.Statement;

public class DBConnection {

    private final static String URL = "jdbc:mysql://localhost:3306/vending_machine";
    private static final String USERNAME = "root";
    private static final String PASSWORD = "";

    public static Connection getConnection() throws SQLException {
        return DriverManager.getConnection(URL, USERNAME, PASSWORD);

    }

    public static void initializeDatabase() {
        String sql = """
                     CREATE TABLE IF NOT EXISTS admins(
                     admin_id INT AUTO_INCREMENT NOT NULL PRIMARY KEY,
                     username VARCHAR(50) NOT NULL UNIQUE,
                     password VARCHAR(100) NOT NULL
                     )
                     """;

        String sql1 = """
                     CREATE TABLE IF NOT EXISTS machine_money(
                     id INT AUTO_INCREMENT PRIMARY KEY,
                     total_amount DOUBLE NOT NULL
                     )
                     """;

        String sql2 = """
                     CREATE TABLE IF NOT EXISTS products(
                     product_id INT AUTO_INCREMENT PRIMARY KEY,
                     name VARCHAR(50) NOT NULL,
                     price INT NOT NULL,
                     stock INT NOT NULL
                     )
                     """;

        try (Connection con = getConnection(); PreparedStatement ps = con.prepareStatement(sql); PreparedStatement ps1 = con.prepareStatement(sql1); PreparedStatement ps2 = con.prepareStatement(sql2);) {
            ps.execute();
            ps1.execute();
            ps2.execute();

            System.out.println("Database created successfully.");
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
}
