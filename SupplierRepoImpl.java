package com.group4.rims.repository;

import com.group4.rims.model.Supplier;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

public class SupplierRepoImpl implements SupplierRepo {
    private final Connection connection;

    public SupplierRepoImpl(Connection connection) {
        this.connection = connection;
    }

    @Override
    public Supplier findById(int id) {
        String query = "SELECT * FROM suppliers WHERE supplier_id=?";
        try (PreparedStatement stmnt = connection.prepareStatement(query)) {
        	stmnt.setInt(1, id);
            ResultSet rs = stmnt.executeQuery();
            if (rs.next()) return mapRow(rs);
        } catch (SQLException e) { e.printStackTrace(); }
        return null;
    }

    @Override
    public List<Supplier> findAll() {
        List<Supplier> suppliers = new ArrayList<>();
        String query = "SELECT * FROM suppliers";
        try (Statement stmnt = connection.createStatement();
             ResultSet rs = stmnt.executeQuery(query)) {
            while (rs.next()) suppliers.add(mapRow(rs));
        } catch (SQLException e) { e.printStackTrace(); }
        return suppliers;
    }

    @Override
    public void save(Supplier supplier) {
        String query = "INSERT INTO suppliers (supplier_name, contact_number, address) VALUES (?, ?, ?)";
        try (PreparedStatement stmnt = connection.prepareStatement(query)) {
        	stmnt.setString(1, supplier.getSupplierName());
            stmnt.setString(2, supplier.getContactNumber());
            stmnt.setString(3, supplier.getAddress());
            stmnt.executeUpdate();
        } catch (SQLException e) { e.printStackTrace(); }
    }

    @Override
    public void update(Supplier supplier) {
        String query = "UPDATE suppliers SET supplier_name=?, contact_number=?, address=? WHERE supplier_id=?";
        try (PreparedStatement stmnt = connection.prepareStatement(query)) {
        	stmnt.setString(1, supplier.getSupplierName());
            stmnt.setString(2, supplier.getContactNumber());
            stmnt.setString(3, supplier.getAddress());
            stmnt.setInt(4, supplier.getSupplierId());
            stmnt.executeUpdate();
        } catch (SQLException e) { e.printStackTrace(); }
    }

    @Override
    public void delete(int id) {
        String query = "DELETE FROM suppliers WHERE supplier_id=?";
        try (PreparedStatement stmnt = connection.prepareStatement(query)) {
        	stmnt.setInt(1, id);
            stmnt.executeUpdate();
        } catch (SQLException e) { e.printStackTrace(); }
    }

    private Supplier mapRow(ResultSet rs) throws SQLException {
        return new Supplier(
            rs.getInt("supplier_id"),
            rs.getString("supplier_name"),
            rs.getString("contact_number"),
            rs.getString("address")
        );
    }
}
