package com.group4.rims.repository;

import com.group4.rims.model.RecipeIngredient;
import java.util.List;

public interface RecipeIngredientRepo {
    RecipeIngredient findById(int id);
    List<RecipeIngredient> findAll();
    public abstract void save(RecipeIngredient recipeIngredient);
    public abstract void update(RecipeIngredient recipeIngredient);
    public abstract void delete(int id);

    List<RecipeIngredient> findByMenuId(int menuId);
    List<RecipeIngredient> findByIngredientId(int ingredientId);
}
