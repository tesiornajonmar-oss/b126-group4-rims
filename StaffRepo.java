package com.group4.rims.repository;

import com.group4.rims.model.StaffUser;
import java.util.List;

public interface StaffRepo {
    StaffUser findById(int id);
    List<StaffUser> findAll();
    public abstract void save(StaffUser staff);
    public abstract void update(StaffUser staff);
    public abstract void delete(int id);

    StaffUser findByUsername(String username);
}
