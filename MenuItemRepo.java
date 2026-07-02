package com.group4.rims.repository;

import com.group4.rims.model.MenuItem;
import java.util.List;

public interface MenuItemRepo {
    MenuItem findById(int id);
    List<MenuItem> findAll();
    void save(MenuItem menuItem);
    void update(MenuItem menuItem);
    void delete(int id);

    List<MenuItem> findByCategory(String category);
    List<MenuItem> findByStatus(String status);
}
