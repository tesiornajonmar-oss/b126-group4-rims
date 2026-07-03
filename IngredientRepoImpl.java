package com.group4.rims.repository;

import com.group4.rims.model.Ingredient;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class IngredientRepoImpl implements IngredientRepo {
    private final Connection connection;

    public IngredientRepoImpl(Connection connection) {
        this.connection = connection;
    }

    @Override
    public Ingredient findById(int id) {
        String query = "SELECT * FROM ingredients WHERE ingredient_id=?";
        try (PreparedStatement stmnt = connection.prepareStatement(query)) {
            stmnt.setInt(1, id);
            ResultSet rs = stmnt.executeQuery();
            if (rs.next()) return mapRow(rs);
        } catch (SQLException e) { e.printStackTrace(); }
        return null;
    }

    @Override
    public List<Ingredient> findAll() {
        List<Ingredient> ingredients = new ArrayList<>();
        String query = "SELECT * FROM ingredients";
        try (Statement stmnt = connection.createStatement();
             ResultSet rs = stmnt.executeQuery(query)) {
            while (rs.next()) ingredients.add(mapRow(rs));
        } catch (SQLException e) { e.printStackTrace(); }
        return ingredients;
    }

    @Override
    public void save(Ingredient ingredient) {
        String query = "INSERT INTO ingredients (ingredient_name, stock_quantity, unit, reorder_level, supplier_id) VALUES (?, ?, ?, ?, ?)";
        try (PreparedStatement stmnt = connection.prepareStatement(query)) {
            stmnt.setString(1, ingredient.getIngredientName());
            stmnt.setInt(2, ingredient.getStockQuantity());
            stmnt.setString(3, ingredient.getUnit());
            stmnt.setInt(4, ingredient.getReorderLevel());
            stmnt.setInt(5, ingredient.getSupplierId());
            stmnt.executeUpdate();
        } catch (SQLException e) { e.printStackTrace(); }
    }

    @Override
    public void update(Ingredient ingredient) {
        String query = "UPDATE ingredients SET ingredient_name=?, stock_quantity=?, unit=?, reorder_level=?, supplier_id=? WHERE ingredient_id=?";
        try (PreparedStatement stmnt = connection.prepareStatement(query)) {
            stmnt.setString(1, ingredient.getIngredientName());
            stmnt.setInt(2, ingredient.getStockQuantity());
            stmnt.setString(3, ingredient.getUnit());
            stmnt.setInt(4, ingredient.getReorderLevel());
            stmnt.setInt(5, ingredient.getSupplierId());
            stmnt.setInt(6, ingredient.getIngredientId());
            stmnt.executeUpdate();
        } catch (SQLException e) { e.printStackTrace(); }
    }

    @Override
    public void delete(int id) {
        String query = "DELETE FROM ingredients WHERE ingredient_id=?";
        try (PreparedStatement stmnt = connection.prepareStatement(query)) {
            stmnt.setInt(1, id);
            stmnt.executeUpdate();
        } catch (SQLException e) { e.printStackTrace(); }
    }

    @Override
    public List<Ingredient> findBySupplierId(int supplierId) {
        List<Ingredient> ingredients = new ArrayList<>();
        String query = "SELECT * FROM ingredients WHERE supplier_id=?";
        try (PreparedStatement stmnt = connection.prepareStatement(query)) {
            stmnt.setInt(1, supplierId);
            ResultSet rs = stmnt.executeQuery();
            while (rs.next()) ingredients.add(mapRow(rs));
        } catch (SQLException e) { e.printStackTrace(); }
        return ingredients;
    }

    @Override
    public List<Ingredient> findLowStockIngredients() {
        List<Ingredient> ingredients = new ArrayList<>();
        String query = "SELECT * FROM ingredients WHERE stock_quantity < reorder_level";
        try (Statement stmnt = connection.createStatement();
             ResultSet rs = stmnt.executeQuery(query)) {
            while (rs.next()) ingredients.add(mapRow(rs));
        } catch (SQLException e) { e.printStackTrace(); }
        return ingredients;
    }

    public List<Map<String, Object>> findIngredientsWithSupplier() {
        List<Map<String, Object>> results = new ArrayList<>();
        String query = "SELECT i.ingredient_id, i.ingredient_name, i.stock_quantity, i.unit, i.reorder_level, " +
                       "s.supplier_id, s.supplier_name " +
                       "FROM ingredients i " +
                       "JOIN suppliers s ON i.supplier_id = s.supplier_id";
        try (Statement stmnt = connection.createStatement();
             ResultSet rs = stmnt.executeQuery(query)) {
            while (rs.next()) {
                Map<String, Object> row = new HashMap<>();
                row.put("ingredientId", rs.getInt("ingredient_id"));
                row.put("ingredientName", rs.getString("ingredient_name"));
                row.put("stockQuantity", rs.getInt("stock_quantity"));
                row.put("unit", rs.getString("unit"));
                row.put("reorderLevel", rs.getInt("reorder_level"));
                row.put("supplierId", rs.getInt("supplier_id"));
                row.put("supplierName", rs.getString("supplier_name"));
                results.add(row);
            }
        } catch (SQLException e) { e.printStackTrace(); }
        return results;
    }

    public List<Map<String, Object>> findIngredientsUsedInRecipes() {
        List<Map<String, Object>> results = new ArrayList<>();
        String query = "SELECT i.ingredient_id, i.ingredient_name, r.recipe_id, r.menu_id, r.quantity_needed " +
                       "FROM ingredients i " +
                       "JOIN recipe_ingredients r ON i.ingredient_id = r.ingredient_id";
        try (Statement stmnt = connection.createStatement();
             ResultSet rs = stmnt.executeQuery(query)) {
            while (rs.next()) {
                Map<String, Object> row = new HashMap<>();
                row.put("ingredientId", rs.getInt("ingredient_id"));
                row.put("ingredientName", rs.getString("ingredient_name"));
                row.put("recipeId", rs.getInt("recipe_id"));
                row.put("menuId", rs.getInt("menu_id"));
                row.put("quantityNeeded", rs.getDouble("quantity_needed"));
                results.add(row);
            }
        } catch (SQLException e) { e.printStackTrace(); }
        return results;
    }

    private Ingredient mapRow(ResultSet rs) throws SQLException {
        return new Ingredient(
            rs.getInt("ingredient_id"),
            rs.getString("ingredient_name"),
            rs.getInt("stock_quantity"),
            rs.getString("unit"),
            rs.getInt("reorder_level"),
            rs.getInt("supplier_id")
        );
    }
}
