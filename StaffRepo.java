package com.group4.rims.repository;

import com.group4.rims.model.StaffUser;
import java.util.List;

public interface StaffRepo {
    StaffUser findById(int id);
    List<StaffUser> findAll();
    void save(StaffUser staff);
    void update(StaffUser staff);
    void delete(int id);

    StaffUser findByUsername(String username);
}
