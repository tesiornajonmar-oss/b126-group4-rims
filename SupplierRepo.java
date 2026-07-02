package com.group4.rims.repository;

import com.group4.rims.model.Supplier;
import java.util.List;

public interface SupplierRepo {
    Supplier findById(int id);
    List<Supplier> findAll();
    void save(Supplier supplier);
    void update(Supplier supplier);
    void delete(int id);
}
