package com.group4.rims.repository;

import com.group4.rims.model.Ingredient;
import java.util.List;

public interface IngredientRepo {
    Ingredient findById(int id);
    List<Ingredient> findAll();
    void save(Ingredient ingredient);
    void update(Ingredient ingredient);
    void delete(int id);

    List<Ingredient> findBySupplierId(int supplierId);
    List<Ingredient> findLowStockIngredients();
}
