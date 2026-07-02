package com.group4.rims.repository;

import com.group4.rims.model.MenuItem;
import java.util.List;

public interface MenuItemRepo {
    MenuItem findById(int id);
    List<MenuItem> findAll();
    public abstract void save(MenuItem menuItem);
    public abstract void update(MenuItem menuItem);
    public abstract void delete(int id);

    List<MenuItem> findByCategory(String category);
    List<MenuItem> findByStatus(String status);
}
