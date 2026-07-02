package com.group4.rims.repository;

import com.group4.rims.model.MenuItem;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

public class MenuItemRepoImpl implements MenuItemRepo {
    private final Connection connection;

    public MenuItemRepoImpl(Connection connection) {
        this.connection = connection;
    }

    @Override
    public MenuItem findById(int id) {
        String query = "SELECT * FROM menu_items WHERE menu_id=?";
        try (PreparedStatement stmnt = connection.prepareStatement(query)) {
        	stmnt.setInt(1, id);
            ResultSet rs = stmnt.executeQuery();
            if (rs.next()) return mapRow(rs);
        } catch (SQLException e) { e.printStackTrace(); }
        return null;
    }

    @Override
    public List<MenuItem> findAll() {
        List<MenuItem> items = new ArrayList<>();
        String query = "SELECT * FROM menu_items";
        try (Statement stmnt = connection.createStatement();
             ResultSet rs = stmnt.executeQuery(query)) {
            while (rs.next()) items.add(mapRow(rs));
        } catch (SQLException e) { e.printStackTrace(); }
        return items;
    }

    @Override
    public void save(MenuItem menuItem) {
        String query = "INSERT INTO menu_items (menu_name, price, category, status) VALUES (?, ?, ?, ?)";
        try (PreparedStatement stmnt = connection.prepareStatement(query)) {
            stmnt.setString(1, menuItem.getMenuName());
            stmnt.setDouble(2, menuItem.getPrice());
            stmnt.setString(3, menuItem.getCategory());
            stmnt.setString(4, menuItem.getStatus());
            stmnt.executeUpdate();
        } catch (SQLException e) { e.printStackTrace(); }
    }

    @Override
    public void update(MenuItem menuItem) {
        String query = "UPDATE menu_items SET menu_name=?, price=?, category=?, status=? WHERE menu_id=?";
        try (PreparedStatement stmnt = connection.prepareStatement(query)) {
            stmnt.setString(1, menuItem.getMenuName());
            stmnt.setDouble(2, menuItem.getPrice());
            stmnt.setString(3, menuItem.getCategory());
            stmnt.setString(4, menuItem.getStatus());
            stmnt.setInt(5, menuItem.getMenuId());
            stmnt.executeUpdate();
        } catch (SQLException e) { e.printStackTrace(); }
    }

    @Override
    public void delete(int id) {
        String query = "DELETE FROM menu_items WHERE menu_id=?";
        try (PreparedStatement stmnt = connection.prepareStatement(query)) {
            stmnt.setInt(1, id);
            stmnt.executeUpdate();
        } catch (SQLException e) { e.printStackTrace(); }
    }

    @Override
    public List<MenuItem> findByCategory(String category) {
        List<MenuItem> items = new ArrayList<>();
        String query = "SELECT * FROM menu_items WHERE category=?";
        try (PreparedStatement stmnt = connection.prepareStatement(query)) {
        	stmnt.setString(1, category);
            ResultSet rs = stmnt.executeQuery();
            while (rs.next()) items.add(mapRow(rs));
        } catch (SQLException e) { e.printStackTrace(); }
        return items;
    }

    @Override
    public List<MenuItem> findByStatus(String status) {
        List<MenuItem> items = new ArrayList<>();
        String query = "SELECT * FROM menu_items WHERE status=?";
        try (PreparedStatement stmnt = connection.prepareStatement(query)) {
        	stmnt.setString(1, status);
            ResultSet rs = stmnt.executeQuery();
            while (rs.next()) items.add(mapRow(rs));
        } catch (SQLException e) { e.printStackTrace(); }
        return items;
    }

    private MenuItem mapRow(ResultSet rs) throws SQLException {
        return new MenuItem(
            rs.getInt("menu_id"),
            rs.getString("menu_name"),
            rs.getDouble("price"),
            rs.getString("category"),
            rs.getString("status")
        );
    }
}
