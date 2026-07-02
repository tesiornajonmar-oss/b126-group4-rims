package com.group4.rims.repository;

import com.group4.rims.model.Supplier;
import java.util.List;

public interface SupplierRepo {
    Supplier findById(int id);
    List<Supplier> findAll();
    public abstract void save(Supplier supplier);
    public abstract void update(Supplier supplier);
    public abstract void delete(int id);
}
