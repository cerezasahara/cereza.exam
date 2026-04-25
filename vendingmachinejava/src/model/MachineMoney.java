package model;

import java.sql.*;
import utils.DBConnection;

public class MachineMoney {

    // Add money to machine
    public static void addMoney(double amount) {
        try (Connection conn = DBConnection.getConnection()) {
            String sql = "UPDATE machine_money SET total_amount = total_amount + ?";
            PreparedStatement pst = conn.prepareStatement(sql);
            pst.setDouble(1, amount);
            pst.executeUpdate();
        } catch (SQLException e) {
            System.out.println("Error adding money: " + e.getMessage());
        }
    }

    // Collect money (reset total_amount to 0)
    public static double collectMoney() {
        double total = 0;
        try (Connection conn = DBConnection.getConnection()) {

            String getSql = "SELECT total_amount FROM machine_money";
            PreparedStatement pst = conn.prepareStatement(getSql);
            ResultSet rs = pst.executeQuery();
            if (rs.next()) total = rs.getDouble("total_amount");

            String resetSql = "UPDATE machine_money SET total_amount = 0";
            PreparedStatement pst2 = conn.prepareStatement(resetSql);
            pst2.executeUpdate();

        } catch (SQLException e) {
            System.out.println("Error collecting money: " + e.getMessage());
        }
        return total;
    }
}
