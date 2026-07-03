package com.group4.rims.repository;

import com.group4.rims.model.MenuItem;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

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

    // JOIN: menu items with ingredients
    public List<Map<String, Object>> findMenuItemsWithIngredients() {
        List<Map<String, Object>> results = new ArrayList<>();
        String query = "SELECT m.menu_id, m.menu_name, m.price, m.category, m.status, " +
                       "r.recipe_id, r.quantity_needed, " +
                       "i.ingredient_id, i.ingredient_name " +
                       "FROM menu_items m " +
                       "JOIN recipe_ingredients r ON m.menu_id = r.menu_id " +
                       "JOIN ingredients i ON r.ingredient_id = i.ingredient_id";
        try (Statement stmnt = connection.createStatement();
             ResultSet rs = stmnt.executeQuery(query)) {
            while (rs.next()) {
                Map<String, Object> row = new HashMap<>();
                row.put("menuId", rs.getInt("menu_id"));
                row.put("menuName", rs.getString("menu_name"));
                row.put("price", rs.getDouble("price"));
                row.put("category", rs.getString("category"));
                row.put("status", rs.getString("status"));
                row.put("recipeId", rs.getInt("recipe_id"));
                row.put("quantityNeeded", rs.getDouble("quantity_needed"));
                row.put("ingredientId", rs.getInt("ingredient_id"));
                row.put("ingredientName", rs.getString("ingredient_name"));
                results.add(row);
            }
        } catch (SQLException e) { e.printStackTrace(); }
        return results;
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
