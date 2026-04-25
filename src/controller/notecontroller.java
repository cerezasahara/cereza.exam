package controller;

import utils.DBConnection;
import java.sql.*;
import java.util.ArrayList;

public class notecontroller {

    public static void createNote(int ownerId, String note) {
        try (Connection conn = DBConnection.getConnection()) {

            PreparedStatement ps = conn.prepareStatement(
                "INSERT INTO notepad(owner_id, note, updated_at) VALUES (?, ?, NOW())"
            );

            ps.setInt(1, ownerId);
            ps.setString(2, note);
            ps.executeUpdate();

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public static ArrayList<String[]> getNotes(int userId, String search) {

        ArrayList<String[]> list = new ArrayList<>();

        try (Connection conn = DBConnection.getConnection()) {

            String sql = """
                SELECT DISTINCT 
                n.id,
                n.note,
                s.category,
                n.updated_at,
                u.username,
                CASE 
                    WHEN n.owner_id = ? THEN 'OWNER'
                    ELSE s.permission
                END AS access
                FROM notepad n
                LEFT JOIN shared_with s ON n.id = s.note_id
                LEFT JOIN users u ON n.owner_id = u.id
                WHERE (n.owner_id = ? OR s.shared_user_id = ?)
                AND n.note LIKE ?
                ORDER BY n.updated_at DESC
            """;

            PreparedStatement ps = conn.prepareStatement(sql);
            ps.setInt(1, userId);
            ps.setInt(2, userId);
            ps.setInt(3, userId);
            ps.setString(4, "%" + search + "%");

            ResultSet rs = ps.executeQuery();

            while (rs.next()) {

                String category = rs.getString("category");
                if (category == null) category = "None";

                list.add(new String[]{
                        rs.getString("id"),         
                        rs.getString("note"),       
                        category,                   
                        rs.getString("updated_at"), 
                        rs.getString("username"),   
                        rs.getString("access")      
                });
            }

        } catch (Exception e) {
            e.printStackTrace();
        }

        return list;
    }

    public static void updateNote(int id, int userId, String note) {
        try (Connection conn = DBConnection.getConnection()) {

            if (!canEdit(id, userId)) return;

            PreparedStatement ps = conn.prepareStatement(
                "UPDATE notepad SET note=?, updated_at=NOW() WHERE id=?"
            );

            ps.setString(1, note);
            ps.setInt(2, id);
            ps.executeUpdate();

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public static void deleteNote(int id, int userId) {
        try (Connection conn = DBConnection.getConnection()) {

            PreparedStatement check = conn.prepareStatement(
                "SELECT owner_id FROM notepad WHERE id=?"
            );
            check.setInt(1, id);
            ResultSet rs = check.executeQuery();

            if (rs.next() && rs.getInt("owner_id") != userId) {
                return;
            }

            PreparedStatement ps = conn.prepareStatement(
                "DELETE FROM notepad WHERE id=?"
            );
            ps.setInt(1, id);
            ps.executeUpdate();

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public static void shareNote(int noteId, int ownerId, int sharedUserId, String category, String permission) {
        try (Connection conn = DBConnection.getConnection()) {

            PreparedStatement ps = conn.prepareStatement(
                "INSERT INTO shared_with(note_id, owner_id, shared_user_id, category, permission) VALUES (?,?,?,?,?)"
            );

            ps.setInt(1, noteId);
            ps.setInt(2, ownerId);
            ps.setInt(3, sharedUserId);
            ps.setString(4, category);
            ps.setString(5, permission);

            ps.executeUpdate();

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public static String getPermission(int noteId, int userId) {
        try (Connection conn = DBConnection.getConnection()) {

            PreparedStatement owner = conn.prepareStatement(
                "SELECT owner_id FROM notepad WHERE id=?"
            );
            owner.setInt(1, noteId);
            ResultSet r = owner.executeQuery();

            if (r.next() && r.getInt("owner_id") == userId)
                return "OWNER";

            PreparedStatement ps = conn.prepareStatement(
                "SELECT permission FROM shared_with WHERE note_id=? AND shared_user_id=?"
            );
            ps.setInt(1, noteId);
            ps.setInt(2, userId);

            ResultSet rs = ps.executeQuery();
            if (rs.next()) return rs.getString("permission");

        } catch (Exception e) {
            e.printStackTrace();
        }
        return "NONE";
    }

    public static boolean canEdit(int noteId, int userId) {
        String p = getPermission(noteId, userId);
        return p.equals("OWNER") || p.equals("EDIT");
    }
}