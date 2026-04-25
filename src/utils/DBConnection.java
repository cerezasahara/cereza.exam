package utils;

import java.sql.*;
import java.sql.Connection;
import java.sql.DriverManager;


public class DBConnection {

    private static final String URL = "jdbc:mysql://localhost:3306/javanotepad";
    private static final String USER = "root";
    private static final String PASS = "";

    public static Connection getConnection() throws SQLException {
        return DriverManager.getConnection(URL, USER, PASS);
    }

    public static void initializeDatabase() {

        try (Connection conn = getConnection();
             Statement stmt = conn.createStatement()) {

            stmt.execute("""
                CREATE TABLE IF NOT EXISTS users (
                    id INT AUTO_INCREMENT PRIMARY KEY,
                    username VARCHAR(50) UNIQUE,
                    password VARCHAR(100)
                )
            """);

            stmt.execute("""
                   CREATE TABLE IF NOT EXISTS notepad (
                        id INT AUTO_INCREMENT PRIMARY KEY,
                        owner_id INT,
                        note TEXT,
                        created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
                        updated_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP 
                            ON UPDATE CURRENT_TIMESTAMP
                )
            """);

            stmt.execute("""
                    CREATE TABLE IF NOT EXISTS shared_with (
                        id INT AUTO_INCREMENT PRIMARY KEY,
                        note_id INT,
                        owner_id INT,
                        shared_user_id INT,
                        category VARCHAR(50),
                        permission ENUM('VIEW','EDIT')
                    )
                """);

        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
