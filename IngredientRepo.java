package com.group4.rims.repository;

import com.group4.rims.model.Ingredient;
import java.util.List;

public interface IngredientRepo {
    Ingredient findById(int id);
    List<Ingredient> findAll();
    public abstract void save(Ingredient ingredient);
    public abstract void update(Ingredient ingredient);
    public abstract void delete(int id);

    List<Ingredient> findBySupplierId(int supplierId);
    List<Ingredient> findLowStockIngredients();
}
