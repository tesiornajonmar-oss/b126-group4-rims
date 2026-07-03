package com.group4.rims.repository;

import com.group4.rims.model.StaffUser;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import org.mindrot.jbcrypt.BCrypt;


public class StaffRepoImpl implements StaffRepo {
    private final Connection connection;

    public StaffRepoImpl(Connection connection) {
        this.connection = connection;
    }

    @Override
    public StaffUser findById(int id) {
        String query = "SELECT * FROM staff WHERE staff_id=?";
        try (PreparedStatement stmnt = connection.prepareStatement(query)) {
            stmnt.setInt(1, id);
            ResultSet rs = stmnt.executeQuery();
            if (rs.next()) return mapRow(rs);
        } catch (SQLException e) { e.printStackTrace(); }
        return null;
    }

    @Override
    public List<StaffUser> findAll() {
        List<StaffUser> staffList = new ArrayList<>();
        String query = "SELECT * FROM staff";
        try (Statement stmnt = connection.createStatement();
             ResultSet rs = stmnt.executeQuery(query)) {
            while (rs.next()) staffList.add(mapRow(rs));
        } catch (SQLException e) { e.printStackTrace(); }
        return staffList;
    }

    @Override
    public void save(StaffUser staff) {
        String query = "INSERT INTO staff (username, password, full_name, role) VALUES (?, ?, ?, ?)";
        String hashedPassword = BCrypt.hashpw(staff.getPassword(), BCrypt.gensalt());
        try (PreparedStatement stmnt = connection.prepareStatement(query)) {
            stmnt.setString(1, staff.getUserName());
            stmnt.setString(2, hashedPassword);
            stmnt.setString(3, staff.getFullName());
            stmnt.setString(4, staff.getRole());
            stmnt.executeUpdate();
        } catch (SQLException e) { e.printStackTrace(); }
    }

    @Override
    public void update(StaffUser staff) {
        String query = "UPDATE staff SET username=?, password=?, full_name=?, role=? WHERE staff_id=?";
        try (PreparedStatement stmnt = connection.prepareStatement(query)) {
            stmnt.setString(1, staff.getUserName());
            stmnt.setString(2, staff.getPassword());
            stmnt.setString(3, staff.getFullName());
            stmnt.setString(4, staff.getRole());
            stmnt.setInt(5, staff.getStaffId());
            stmnt.executeUpdate();
        } catch (SQLException e) { e.printStackTrace(); }
    }

    @Override
    public void delete(int id) {
        String query = "DELETE FROM staff WHERE staff_id=?";
        try (PreparedStatement stmnt = connection.prepareStatement(query)) {
            stmnt.setInt(1, id);
            stmnt.executeUpdate();
        } catch (SQLException e) { e.printStackTrace(); }
    }

    @Override
    public StaffUser findByUsername(String username) {
        String query = "SELECT * FROM staff WHERE username=?";
        try (PreparedStatement stmnt = connection.prepareStatement(query)) {
            stmnt.setString(1, username);
            ResultSet rs = stmnt.executeQuery();
            if (rs.next()) return mapRow(rs);
        } catch (SQLException e) { e.printStackTrace(); }
        return null;
    }

    public List<Map<String, Object>> findStaffWithTransactions() {
        List<Map<String, Object>> results = new ArrayList<>();
        String query = "SELECT s.staff_id, s.full_name, s.role, " +
                       "t.transaction_id, t.transaction_type, t.quantity " +
                       "FROM staff s " +
                       "JOIN inventory_transactions t ON s.staff_id = t.staff_id";
        try (Statement stmnt = connection.createStatement();
             ResultSet rs = stmnt.executeQuery(query)) {
            while (rs.next()) {
                Map<String, Object> row = new HashMap<>();
                row.put("staffId", rs.getInt("staff_id"));
                row.put("fullName", rs.getString("full_name"));
                row.put("role", rs.getString("role"));
                row.put("transactionId", rs.getInt("transaction_id"));
                row.put("transactionType", rs.getString("transaction_type"));
                row.put("quantity", rs.getInt("quantity"));
                results.add(row);
            }
        } catch (SQLException e) { e.printStackTrace(); }
        return results;
    }

    private StaffUser mapRow(ResultSet rs) throws SQLException {
        return new StaffUser(
            rs.getInt("staff_id"),
            rs.getString("username"),
            rs.getString("password"),
            rs.getString("full_name"),
            rs.getString("role")
        );
    }
}
