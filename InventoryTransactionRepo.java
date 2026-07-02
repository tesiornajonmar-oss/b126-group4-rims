package com.group4.rims.repository;

import com.group4.rims.model.InventoryTransaction;
import java.util.List;

public interface InventoryTransactionRepo {
    InventoryTransaction findById(int id);
    List<InventoryTransaction> findAll();
    public abstract void save(InventoryTransaction transaction);
    public abstract void update(InventoryTransaction transaction);
    public abstract void delete(int id);

    List<InventoryTransaction> findByIngredientId(int ingredientId);
    List<InventoryTransaction> findByStaffId(int staffId);
}
