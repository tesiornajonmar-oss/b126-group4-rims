package com.group4.rims.repository;

import com.group4.rims.model.RecipeIngredient;
import java.util.List;

public interface RecipeIngredientRepo {
    RecipeIngredient findById(int id);
    List<RecipeIngredient> findAll();
    void save(RecipeIngredient recipeIngredient);
    void update(RecipeIngredient recipeIngredient);
    void delete(int id);

    List<RecipeIngredient> findByMenuId(int menuId);
    List<RecipeIngredient> findByIngredientId(int ingredientId);
}
