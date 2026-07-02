package com.group4.rims.repository;

import com.group4.rims.model.RecipeIngredient;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

public class RecipeIngredientRepoImpl implements RecipeIngredientRepo {
    private final Connection connection;

    public RecipeIngredientRepoImpl(Connection connection) {
        this.connection = connection;
    }

    @Override
    public RecipeIngredient findById(int id) {
        String query = "SELECT * FROM recipe_ingredients WHERE recipe_id=?";
        try (PreparedStatement stmnt = connection.prepareStatement(query)) {
            stmnt.setInt(1, id);
            ResultSet rs = stmnt.executeQuery();
            if (rs.next()) return mapRow(rs);
        } catch (SQLException e) { e.printStackTrace(); }
        return null;
    }

    @Override
    public List<RecipeIngredient> findAll() {
        List<RecipeIngredient> recipes = new ArrayList<>();
        String query = "SELECT * FROM recipe_ingredients";
        try (Statement stmnt = connection.createStatement();
             ResultSet rs = stmnt.executeQuery(query)) {
            while (rs.next()) recipes.add(mapRow(rs));
        } catch (SQLException e) { e.printStackTrace(); }
        return recipes;
    }

    @Override
    public void save(RecipeIngredient recipeIngredient) {
        String query = "INSERT INTO recipe_ingredients (menu_id, ingredient_id, quantity_needed) VALUES (?, ?, ?)";
        try (PreparedStatement stmnt = connection.prepareStatement(query)) {
            stmnt.setInt(1, recipeIngredient.getMenuId());
            stmnt.setInt(2, recipeIngredient.getIngredientId());
            stmnt.setDouble(3, recipeIngredient.getQuantityNeeded());
            stmnt.executeUpdate();
        } catch (SQLException e) { e.printStackTrace(); }
    }

    @Override
    public void update(RecipeIngredient recipeIngredient) {
        String query = "UPDATE recipe_ingredients SET menu_id=?, ingredient_id=?, quantity_needed=? WHERE recipe_id=?";
        try (PreparedStatement stmnt = connection.prepareStatement(query)) {
            stmnt.setInt(1, recipeIngredient.getMenuId());
            stmnt.setInt(2, recipeIngredient.getIngredientId());
            stmnt.setDouble(3, recipeIngredient.getQuantityNeeded());
            stmnt.setInt(4, recipeIngredient.getRecipeId());
            stmnt.executeUpdate();
        } catch (SQLException e) { e.printStackTrace(); }
    }

    @Override
    public void delete(int id) {
        String query = "DELETE FROM recipe_ingredients WHERE recipe_id=?";
        try (PreparedStatement stmnt = connection.prepareStatement(query)) {
            stmnt.setInt(1, id);
            stmnt.executeUpdate();
        } catch (SQLException e) { e.printStackTrace(); }
    }

    @Override
    public List<RecipeIngredient> findByMenuId(int menuId) {
        List<RecipeIngredient> recipes = new ArrayList<>();
        String query = "SELECT * FROM recipe_ingredients WHERE menu_id=?";
        try (PreparedStatement stmnt = connection.prepareStatement(query)) {
            stmnt.setInt(1, menuId);
            ResultSet rs = stmnt.executeQuery();
            while (rs.next()) recipes.add(mapRow(rs));
        } catch (SQLException e) { e.printStackTrace(); }
        return recipes;
    }

    @Override
    public List<RecipeIngredient> findByIngredientId(int ingredientId) {
        List<RecipeIngredient> recipes = new ArrayList<>();
        String query = "SELECT * FROM recipe_ingredients WHERE ingredient_id=?";
        try (PreparedStatement stmnt = connection.prepareStatement(query)) {
            stmnt.setInt(1, ingredientId);
            ResultSet rs = stmnt.executeQuery();
            while (rs.next()) recipes.add(mapRow(rs));
        } catch (SQLException e) { e.printStackTrace(); }
        return recipes;
    }

    private RecipeIngredient mapRow(ResultSet rs) throws SQLException {
        return new RecipeIngredient(
            rs.getInt("recipe_id"),
            rs.getInt("menu_id"),
            rs.getInt("ingredient_id"),
            rs.getDouble("quantity_needed")
        );
    }
}
