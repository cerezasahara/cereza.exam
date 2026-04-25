package controller;

import utils.DBConnection;
import java.sql.*;
import java.util.ArrayList;

public class authcontroller {

    public static int login(String username, String password) {

        try (Connection conn = DBConnection.getConnection()) {

            PreparedStatement ps = conn.prepareStatement(
                "SELECT id FROM users WHERE username=? AND password=?"
            );

            ps.setString(1, username);
            ps.setString(2, password);

            ResultSet rs = ps.executeQuery();

            if (rs.next()) {
                return rs.getInt("id"); 
            }

        } catch (Exception e) {
            e.printStackTrace();
        }

        return -1; 
    }

    public static boolean register(String username, String password) {

        try (Connection conn = DBConnection.getConnection()) {

            PreparedStatement check = conn.prepareStatement(
                "SELECT id FROM users WHERE username=?"
            );
            check.setString(1, username);
            ResultSet rs = check.executeQuery();

            if (rs.next()) {
                return false; 
            }

            PreparedStatement ps = conn.prepareStatement(
                "INSERT INTO users(username,password) VALUES (?,?)"
            );

            ps.setString(1, username);
            ps.setString(2, password);

            ps.executeUpdate();

            return true;

        } catch (Exception e) {
            e.printStackTrace();
        }

        return false;
    }

    public static ArrayList<String[]> getAllUsers() {

        ArrayList<String[]> list = new ArrayList<>();

        try (Connection conn = DBConnection.getConnection()) {

            PreparedStatement ps = conn.prepareStatement(
                "SELECT id, username FROM users"
            );

            ResultSet rs = ps.executeQuery();

            while (rs.next()) {
                list.add(new String[]{
                    rs.getString("id"),
                    rs.getString("username")
                });
            }

        } catch (Exception e) {
            e.printStackTrace();
        }

        return list;
    }
}